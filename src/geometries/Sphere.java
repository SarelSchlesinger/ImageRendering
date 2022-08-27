package geometries;

import primitives.Point;
import primitives.Vector;

public class Sphere implements Geometry {

    final Point center;
    final double radius;

    public Sphere(Point center, double radius) {

        if (center == null) {
            throw new IllegalArgumentException("center cannot be null");
        }

        if (radius <= 0) {
            throw new IllegalArgumentException("The radius must be greater than 0");
        }

        this.center = center;
        this.radius = radius;
    }

    public Vector getNormal(Point point) {

        return point.subtract(this.center).normalize();
    }

    public Point getCenter() {
        return this.center;
    }

    public double getRadius() {
        return this.radius;
    }
}
