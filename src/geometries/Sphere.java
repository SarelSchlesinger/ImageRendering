package geometries;

import primitives.*;

import java.util.List;

import static java.lang.Math.sqrt;
import static primitives.Util.*;

public class Sphere extends Geometry {

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

    public Point getCenter() {
        return this.center;
    }

    public double getRadius() {
        return this.radius;
    }

    public Vector getNormal(Point point) {
        return point.subtract(this.getCenter()).normalize();
    }

    @Override
    protected List<GeoPoint> findGeoIntersectionsHelper(Ray ray, double maxDistance) {

        double tm;
        double d;

        if (this.getCenter().equals(ray.getP0())) {
            tm = 0;
            d = 0;
        } else {
            Vector u = this.getCenter().subtract(ray.getP0());
            tm = ray.getDirection().dotProduct(u);
            d = sqrt(u.lengthSquared() - tm * tm);
        }

        if (d > this.getRadius()) {
            return null;
        }

        double th = sqrt(this.getRadius() * this.getRadius() - d * d);
        double t1 = alignZero(tm + th);
        double t2 = alignZero(tm - th);

        if (    t1 > 0 &&
                t2 > 0 &&
                alignZero(t1 - maxDistance) <= 0 &&
                alignZero(t2 - maxDistance) <= 0 &&
                t1 != t2 ) {

            return List.of(new GeoPoint(this, ray.getPoint(t1)),
                           new GeoPoint(this, ray.getPoint(t2)));
        }

        if (    t1 > 0 &&
                alignZero(t1 - maxDistance) <= 0) {

            return List.of(new GeoPoint(this, ray.getPoint(t1)));
        }

        if (    t2 > 0 &&
                alignZero(t2 - maxDistance) <= 0) {

            return List.of(new GeoPoint(this, ray.getPoint(t2)));
        }

        return null;
    }
}
