package geometries;

import primitives.Point;
import primitives.Vector;

public class Sphere implements Geometry {

    final Point center;
    final double radius;

    public Sphere(Point center, double radius) {
        this.center = center;
        this.radius = radius;
    }

    public Vector getNormal(Point point) {
        return null;
    }

    public Point getCenter() {
        return center;
    }

    public double getRadius() {
        return radius;
    }
}
