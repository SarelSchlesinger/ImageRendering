package geometries;

import primitives.Point;
import primitives.Vector;

public class Plane implements Geometry {

    final Point p0;
    final Vector normal;

    public Plane(Point p1, Point p2, Point p3) {

        if ((p1 == null) || (p2 == null) || (p3 == null)) {
            throw new IllegalArgumentException("Arguments cannot be null");
        }

        if ((p1.equals(p2)) || (p1.equals(p3)) || (p2.equals(p3))) {
            throw new IllegalArgumentException("All points should be different from each other");
        }

        if ((p1.subtract(p2).crossProduct(p1.subtract(p3))).equals(new Vector(0,0,0))) {
            throw new IllegalArgumentException("The points should not be on the same line");
        }

        this.p0 = p1;
        this.normal = p2.subtract(p1).crossProduct(p3.subtract(p1)).normalize();

    }

    public Plane(Point p0, Vector normal) {

        if (p0 == null) {
            throw new IllegalArgumentException("p0 cannot be null");
        }

        if (normal == null) {
            throw new IllegalArgumentException("The normal cannot be null");
        }
        this.p0 = p0;
        this.normal = normal.normalize();
    }

    @Override
    // getNormal method
    public Vector getNormal(Point point) {
        return this.normal;
    }

    // normal getter
    public Vector getNormal() {
        return normal;
    }

    public Point getP0() {
        return p0;
    }

}