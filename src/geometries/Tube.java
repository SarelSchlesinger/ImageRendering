package geometries;

import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import java.util.List;

public class Tube extends Geometry {

    final Ray axisRay;
    final double radius;


    public Tube(Ray axisRay, double radius) {

        if (axisRay == null) {
            throw new IllegalArgumentException("axisRay cannot be null");
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


    @Override
    protected List<GeoPoint> findGeoIntersectionsHelper(Ray ray) {
        /*if (this.axisRay.getDirection().isParallel(ray.getDirection())) {
            return null;
        }*/
        return null;
    }
}