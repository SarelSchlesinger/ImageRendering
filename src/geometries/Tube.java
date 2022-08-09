package geometries;

import primitives.Point;
import primitives.Ray;
import primitives.Vector;

public class Tube implements Geometry {

    final Ray axisRay;
    final double radius;


    public Tube(Ray axisRay, double radius) {
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
