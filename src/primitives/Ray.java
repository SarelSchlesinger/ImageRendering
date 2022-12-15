package primitives;

import java.util.List;

import geometries.Intersectable.GeoPoint;


public class Ray {

    final private Point p0;
    final private Vector direction;
    /**
     * DELTA is a constant value that defines how much the ray's origin should be moved
     */
    private static final double DELTA = 0.1;

    public Ray(Point p0, Vector direction) {

        if (p0 == null) {
            throw new IllegalArgumentException("p0 cannot be null");
        }

        if (direction == null) {
            throw new IllegalArgumentException("direction cannot be null");
        }

        this.p0 = p0;
        this.direction = direction.normalize();
    }

    public Ray(Point p0, Vector direction, Vector normal) {

        if (p0 == null) {
            throw new IllegalArgumentException("p0 cannot be null");
        }

        if (direction == null) {
            throw new IllegalArgumentException("direction cannot be null");
        }

        if (normal == null) {
            throw new IllegalArgumentException("normal cannot be null");
        }

        this.p0 = p0.add(normal.scale(normal.dotProduct(direction) > 0 ? DELTA : -DELTA));
        this.direction = direction.normalize();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Ray ray = (Ray) o;
        return p0.equals(ray.p0) && direction.equals(ray.direction);
    }

    @Override
    public String toString() {
        return "Ray{" + "p0=" + p0 + ", direction=" + direction + '}';
    }

    public Point getP0() {
        return p0;
    }

    public Vector getDirection() {
        return direction;
    }

    public Point getPoint(double t) {
        return this.getP0().add(this.getDirection().scale(t));
    }


    public Point findClosestPoint(List<Point> points) {
        if (points.isEmpty()) {
            return null;
        }
        Point closestPoint = points.get(0);
        double closestPointDistance = this.p0.distance(points.get(0));
        for (int i = 1; i < points.size(); i++) {
            if (this.p0.distance(points.get(i)) < closestPointDistance) {
                closestPoint = points.get(i);
                closestPointDistance = this.p0.distance(points.get(i));
            }
        }
        return closestPoint;
    }

    public GeoPoint findClosestGeoPoint(List<GeoPoint> geoPoints) {
        if (geoPoints == null) {
            return null;
        }
        GeoPoint closestPoint = geoPoints.get(0);
        double closestPointDistance = this.p0.distance(geoPoints.get(0).point);
        for (int i = 1; i < geoPoints.size(); i++) {
            if (this.p0.distance(geoPoints.get(i).point) < closestPointDistance) {
                closestPoint = geoPoints.get(i);
                closestPointDistance = this.p0.distance(geoPoints.get(i).point);
            }
        }
        return closestPoint;
    }

    /**
     * method to find a point on the orthogonal vector to the ray
     * @param distanceFromP0  distance for a point on the ray from the ray's origin point
     * @param distanceFromRay distance for the point on the orthogonal vector to the ray.
     *                        the vector starts from pointOnRay
     */
    public Point findPointOnTheOrthogonalVector(double distanceFromP0, double distanceFromRay) {
        Point pointOnRay = this.getPoint(distanceFromP0);
        return pointOnRay.add(this.getDirection().findOrthogonalVector().scale(distanceFromRay));
    }
}
