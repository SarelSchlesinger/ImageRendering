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

    public Ray getAxisRay() {
        return this.axisRay;
    }

    public double getRadius() {
        return this.radius;
    }

    @Override
    public Vector getNormal(Point point) {
        double t = this.getAxisRay().getDirection().dotProduct(point.subtract(this.getAxisRay().getP0()));
        Point pointOnRay = this.getAxisRay().getP0().add(this.getAxisRay().getDirection().scale(t));
        return point.subtract(pointOnRay).normalize();
    }

    @Override
    protected List<GeoPoint> findGeoIntersectionsHelper(Ray ray, double maxDistance) {
        return null;
    }

}