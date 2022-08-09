package geometries;

import primitives.Point;
import primitives.Vector;

public class Plane implements Geometry {

    final Point p0;
    final Vector normal;

    public Plane(Point p1, Point p2, Point p3) {
        this.p0 = p1;
        this.normal = null;
    }

    public Plane(Point p0, Vector normal) {
        this.p0 = p0;
        this.normal = normal.normalize();
    }

    @Override
    // getNormal method
    public Vector getNormal(Point point) {
        return null;
    }

    // normal getter
    public Vector getNormal() {
        return normal;
    }

    public Point getP0() {
        return p0;
    }


}
