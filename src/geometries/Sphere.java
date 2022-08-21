package geometries;

import primitives.Point;
import primitives.Vector;

public class Sphere implements Geometry {

    final Point center;
    final double radius;

    public Sphere(Point center, double radius) {

        if (!(center instanceof Point)) {
            throw new IllegalArgumentException("center must be of type Point");
        }

        if (radius <= 0) {
            throw new IllegalArgumentException("The radius must be greater than 0");
        }

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
