package primitives;

import geometries.Polygon;

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

    public Double3 getXYZ() {
        return this.xyz;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || this.getClass() != o.getClass()) return false;
        Point point = (Point) o;
        return this.getXYZ().equals(point.getXYZ());
    }

    @Override
    public String toString() {
        return "Point{" + "xyz=" + this.getXYZ() + '}';
    }

    public Point add(Point point) {
        return new Point(this.getXYZ().add(point.getXYZ()));
    }

    public Vector subtract(Point point) {
        return new Vector(this.getXYZ().subtract(point.getXYZ()));
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
        return this.getXYZ().getD1();
    }

    public double getY() {
        return this.getXYZ().getD2();
    }

    public double getZ() {
        return this.getXYZ().getD3();
    }


    /**
     * Returns the midpoint on the line between the two given points
     */
    public Point midpoint(Point point) {
        return new Point((this.getX() + point.getX()) / 2,
                         (this.getY() + point.getY()) / 2,
                         (this.getZ() + point.getZ()) / 2);
    }

    /**
     * Return true if the point located between two given points
     */
    public Boolean isBetween(Point p1, Point p2) {
        if (this.equals(p1) || this.equals(p2)) return false;
        return p1.distance(p2) == p1.distance(this) + this.distance(p2);
    }

    /**
     * Returns true if the point is located on one of the edges of the polygon
     */
    public Boolean isOnEdge(Polygon polygon) {
        for (int i = 0; i < polygon.getSize() - 1; i++) {
            if (this.equals(polygon.getVertices().get(i))) return true;
            if (this.isBetween(polygon.getVertices().get(i), polygon.getVertices().get(i + 1))) return true;
        }
        if (this.equals(polygon.getVertices().get(polygon.getSize() - 1))) return true;
        return this.isBetween(polygon.getVertices().get(0), polygon.getVertices().get(polygon.getSize() - 1));
    }
}

