package geometries;

import primitives.Point;
import primitives.Ray;
import primitives.Vector;

public class Tube implements Geometry {

    final Ray axisRay;
    final double radius;


    public Tube(Ray axisRay, double radius) {

        if (!(axisRay instanceof Ray)) {
            throw new IllegalArgumentException("axisRay must be of type Ray");
        }

        if (radius <= 0) {
            throw new IllegalArgumentException("The radius must be greater than 0");
        }
        this.axisRay = axisRay;
        this.radius = radius;
    }

    @Override
    public Vector getNormal(Point point) {
        return null;
    }

    public Ray getAxisRay() {
        return axisRay;
    }

    public double getRadius() {
        return radius;
    }
}
