package primitives;

import java.util.Collection;
import java.util.Objects;

public class Point {

    final Double3 xyz;

    public static final Point ZERO = new Point(Double3.ZERO);

    public Point(double x, double y, double z) {

        this.xyz = new Double3(x, y, z);
    }

    public Point(Double3 xyz) {

        if (xyz == null) {
            throw new IllegalArgumentException("Argument cannot be null");
        }

        this.xyz = new Double3(xyz.d1, xyz.d2, xyz.d3);
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Point point = (Point) o;
        return xyz.equals(point.xyz);
    }

    @Override
    public String toString() {
        return "Point{" + "xyz=" + xyz + '}';
    }

    public Point add(Point point) {

        return new Point(this.xyz.add(point.xyz));
    }


    public Vector subtract(Point point) {

        return new Vector(this.xyz.subtract(point.xyz));
    }

    public double distanceSquared(Point point) {
        return (this.getX() - point.getX()) * (this.getX() - point.getX()) +
                (this.getY() - point.getY()) * (this.getY() - point.getY()) +
                (this.getZ() - point.getZ()) * (this.getZ() - point.getZ());
    }

    public double distance(Point point) {

        return Math.sqrt(this.distanceSquared(point));
    }

    public double getX() {
        return this.xyz.d1;
    }

    public double getY() {
        return this.xyz.d2;
    }

    public double getZ() {
        return this.xyz.d3;
    }

    /**
     * Returns the average point between two given points
     */
    public Point midpoint(Point point) {
        return new Point((this.getX() + point.getX()) / 2,
                         (this.getY() + point.getY()) / 2,
                         (this.getZ() + point.getZ()) / 2);
    }

}

