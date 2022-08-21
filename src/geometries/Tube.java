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

        double t = this.axisRay.getDirection().dotProduct(point.subtract(this.axisRay.getP0()));
        Point pointOnRay = this.axisRay.getP0().add(this.axisRay.getDirection().scale(t));

        return point.subtract(pointOnRay).normalize();
    }

    public Ray getAxisRay() {
        return axisRay;
    }

    public double getRadius() {
        return radius;
    }
}
