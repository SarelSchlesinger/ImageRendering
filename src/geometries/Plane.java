package geometries;

import primitives.Double3;
import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import java.util.List;

import static primitives.Util.*;

public class Plane extends Geometry {

    private final Point p0;
    private final Vector normal;

    public Plane(Point p1, Point p2, Point p3) {

        if ((p1 == null) || (p2 == null) || (p3 == null)) {
            throw new IllegalArgumentException("Arguments cannot be null");
        }

        if ((p1.equals(p2)) || (p1.equals(p3)) || (p2.equals(p3))) {
            throw new IllegalArgumentException("All points should be different from each other");
        }

        if ((p1.subtract(p2).crossProduct(p1.subtract(p3))).getXYZ().equals(Double3.ZERO)) {
            throw new IllegalArgumentException("The points should not be on the same line");
        }

        this.p0 = p1;
        this.normal = findNormal(p1,p2,p3);

    }

    public Plane(Point p0, Vector normal) {

        if (p0 == null) {
            throw new IllegalArgumentException("p0 cannot be null");
        }

        if (normal == null) {
            throw new IllegalArgumentException("The normal cannot be null");
        }
        this.p0 = p0;
        this.normal = normal.normalize();
    }

    protected Vector findNormal(Point p1, Point p2, Point p3) {
        return p2.subtract(p1).crossProduct(p3.subtract(p1)).normalize();
    }

    @Override
    // getNormal method
    public Vector getNormal(Point point) {
        return this.normal;
    }

    // normal getter
    public Vector getNormal() {
        return normal;
    }

    public Point getP0() {
        return p0;
    }


    @Override
    protected List<GeoPoint> findGeoIntersectionsHelper(Ray ray, double maxDistance) {

        // The ray is contained in the plane
        if (isZero(ray.getDirection().dotProduct(this.normal))) {
            return null;
        }

        // Ray origin is the head of the normal
        if (ray.getP0().equals(this.p0)) {
            return null;
        }

        double numerator = this.normal.dotProduct(this.p0.subtract(ray.getP0()));
        double denominator = this.normal.dotProduct(ray.getDirection());
        if (isZero(denominator)) {
            throw new IllegalArgumentException("denominator cannot be zero");
        }
        double t = alignZero(numerator / denominator);

        // The ray starts from the plane
        if (t == 0) {
            return null;
        }

        if (t > 0 && alignZero(t - maxDistance) <= 0) {
            return List.of(new GeoPoint(this, ray.getPoint(t)));
        }

        return null;
    }
}