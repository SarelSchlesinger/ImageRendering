package geometries;

import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import java.util.List;

import static primitives.Util.alignZero;
import static primitives.Util.isZero;

public class Triangle extends Polygon {


    public Triangle(Point p1, Point p2, Point p3) {
        super(p1, p2, p3);
    }

    /**
     * Boolean method to check if a given point is inside the triangle
     * This compute was taken from <a href="https://math.stackexchange.com/questions/4322/check-whether-a-point-is-within-a-3d-triangle">...</a>
     */
    public Boolean isInside(Point point) {
        if (point.isOnEdge(this)) return false;
        double triangleArea = this.vertices.get(1).subtract(this.vertices.get(0)).crossProduct(this.vertices.get(2).subtract(this.vertices.get(0))).length() * 0.5;
        double firstThirdOfTheTriangle = this.vertices.get(1).subtract(point).crossProduct(this.vertices.get(2).subtract(point)).length() / (2 * triangleArea);
        double secondThirdOfTheTriangle = this.vertices.get(2).subtract(point).crossProduct(this.vertices.get(0).subtract(point)).length() / (2 * triangleArea);
        double thirdThirdOfTheTriangle = this.vertices.get(0).subtract(point).crossProduct(this.vertices.get(1).subtract(point)).length() / (2 * triangleArea);

        return firstThirdOfTheTriangle >= 0 && secondThirdOfTheTriangle >= 0 && thirdThirdOfTheTriangle >= 0 &&
                firstThirdOfTheTriangle <= 1 && secondThirdOfTheTriangle <= 1 && thirdThirdOfTheTriangle <= 1 &&
                firstThirdOfTheTriangle + secondThirdOfTheTriangle + thirdThirdOfTheTriangle == 1;
    }

    @Override
    protected List<GeoPoint> findGeoIntersectionsHelper(Ray ray, double maxDistance) {
        // Check if the point inside the area of the triangle
        Vector v1 = this.vertices.get(0).subtract(ray.getP0());
        Vector v2 = this.vertices.get(1).subtract(ray.getP0());
        Vector v3 = this.vertices.get(2).subtract(ray.getP0());
        Vector n1 = v1.crossProduct(v2).normalize();
        Vector n2 = v2.crossProduct(v3).normalize();
        Vector n3 = v3.crossProduct(v1).normalize();

        if ((ray.getDirection().dotProduct(n1) < 0 &&
                ray.getDirection().dotProduct(n2) < 0 &&
                ray.getDirection().dotProduct(n3) < 0) ||
                (ray.getDirection().dotProduct(n1) > 0 &&
                        ray.getDirection().dotProduct(n2) > 0 &&
                        ray.getDirection().dotProduct(n3) > 0)) {

            double numerator = this.plane.getNormal().dotProduct(this.plane.getP0().subtract(ray.getP0()));
            double denominator = this.plane.getNormal().dotProduct(ray.getDirection());
            if (isZero(denominator)) {
                throw new IllegalArgumentException("denominator cannot be zero");
            }
            double t = alignZero(numerator / denominator);

            // The ray starts from the triangle
            if (t == 0) {
                return null;
            }

            if (t > 0 && alignZero(t - maxDistance) <= 0) {
                return List.of(new GeoPoint(this, ray.getPoint(t)));
            }
        }
        return null;
    }

    @Override
    public String toString() {
        return "Triangle{" +
                "emission=" + emission +
                ", vertices=" + vertices +
                ", plane=" + plane +
                "} " + super.toString();
    }
}
