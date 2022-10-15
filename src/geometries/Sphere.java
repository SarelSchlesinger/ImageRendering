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

    public Vector getNormal(Point point) {

        return point.subtract(this.center).normalize();
    }

    public Point getCenter() {
        return this.center;
    }

    public double getRadius() {
        return this.radius;
    }

    @Override
    protected List<GeoPoint> findGeoIntersectionsHelper(Ray ray) {
        Vector u = this.center.subtract(ray.getP0());
        double tm = ray.getDirection().dotProduct(u);
        double d = sqrt(u.lengthSquared() - tm * tm);

        if (d > this.radius) {
            return null;
        }

        double th = sqrt(this.radius * this.radius - d * d);
        double t1 = tm + th;
        double t2 = tm - th;

        if (alignZero(t1) > 0 && alignZero(t2) > 0 && t1 != t2) {

            return List.of(new GeoPoint(this,ray.getPoint(t1)),
                           new GeoPoint(this,ray.getPoint(t2)));
        }

        if (t1 > 0) {

            return List.of(new GeoPoint(this,ray.getPoint(t1)));
        }

        if (t2 > 0) {

            return List.of(new GeoPoint(this,ray.getPoint(t2)));
        }

        return null;
    }
}
