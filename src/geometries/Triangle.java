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

    @Override
    public List<Point> findIntersections(Ray ray) {

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

            if (t > 0) {
                return List.of(ray.getPoint(t));
            }

            //return null;
        }
        return null;

//        if (!(ray.getDirection().dotProduct(n1) > 0 &&
//                ray.getDirection().dotProduct(n2) > 0 &&
//                ray.getDirection().dotProduct(n3) > 0)) {
//            return null;
//        }


    }
}
