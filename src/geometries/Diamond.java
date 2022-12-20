package geometries;

import primitives.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static primitives.Util.alignZero;
import static primitives.Util.isZero;

public class Diamond extends Geometries {

    final int sides;
    final Point bottomPoint;
    final double height;
    final double distanceFromBottomPoint;
    final double radius;
    final Vector rotationAxis;
    final Ray diamondRotationAxis;
    final Point topPoint;
    final Color firstColor;
    final Color secondColor;

    public Diamond(int sides, Point bottomPoint, double height, double distanceFromBottomPoint, double radius, Vector rotationAxis, Color firstColor, Color secondColor) {
        if (sides <= 0) {
            throw new IllegalArgumentException("number of sides must be greater than 0");
        }
        if (height <= 0) {
            throw new IllegalArgumentException("diamond's height must be greater than 0");
        }
        if (distanceFromBottomPoint >= 1 || distanceFromBottomPoint <= 0) {
            throw new IllegalArgumentException("distance from the bottom point must be greater than 0 and less than 1");
        }
        if (radius <= 0) {
            throw new IllegalArgumentException("diamond's radius must be greater than 0");
        }
        this.sides = sides;
        this.bottomPoint = bottomPoint;
        this.height = height;
        this.distanceFromBottomPoint = distanceFromBottomPoint * height;
        this.radius = radius;
        this.rotationAxis = rotationAxis;
        this.diamondRotationAxis = new Ray(bottomPoint, rotationAxis);
        this.topPoint = this.diamondRotationAxis.getPoint(this.height);
        this.firstColor = firstColor;
        this.secondColor = secondColor;

        for (int i = 0; i < this.sides; i++) {
            Point p1 = this.bottomPoint.add(diamondRotationAxis.findPointOnTheOrthogonalVector(this.distanceFromBottomPoint, this.radius)
                                                               .subtract(this.bottomPoint)
                                                               .rotateVector(this.rotationAxis, (360 / this.sides) * i));
            Point p2 = this.bottomPoint.add(diamondRotationAxis.findPointOnTheOrthogonalVector(this.distanceFromBottomPoint, this.radius)
                                                               .subtract(this.bottomPoint)
                                                               .rotateVector(this.rotationAxis, (360 / this.sides) * (i + 1)));
            Point p3 = this.topPoint.add(diamondRotationAxis.findPointOnTheOrthogonalVector(this.height, this.radius * 0.7)
                                                            .subtract(topPoint)
                                                            .rotateVector(this.rotationAxis, (360 / this.sides) * i));
            Point p4 = this.topPoint.add(diamondRotationAxis.findPointOnTheOrthogonalVector(this.height, this.radius * 0.7)
                                                            .subtract(topPoint)
                                                            .rotateVector(this.rotationAxis, (360 / this.sides) * (i + 1)));
            Collections.addAll(this.geometriesList,
                               // creating the triangles at the bottom of the diamond
                               new Triangle(this.bottomPoint, p1, p2)
                                       .setEmission((i % 2 == 0) ? this.firstColor : this.secondColor)
                                       .setMaterial(new Material().setKd(0.4).setKs(0.3).setShininess(100).setKt(0.3)),
                               // creating the surface at the top of the diamond
                               new Triangle(topPoint, p3, p4)
                                       .setEmission((i % 2 == 0) ? this.firstColor : this.secondColor)
                                       .setMaterial(new Material().setKd(0.4).setKs(0.3).setShininess(100).setKt(0.3)),
                               new Triangle(p3, p4, p1.midpoint(p2))
                                       .setEmission((i % 2 == 0) ? this.firstColor : this.secondColor)
                                       .setMaterial(new Material().setKd(0.4).setKs(0.3).setShininess(100).setKt(0.3)),
                               new Triangle(p1.midpoint(p2), p1, p3)
                                       .setEmission((i % 2 == 1) ? this.firstColor : this.secondColor)
                                       .setMaterial(new Material().setKd(0.4).setKs(0.3).setShininess(100).setKt(0.3)),
                               new Triangle(p2, p4, p1.midpoint(p2))
                                       .setEmission((i % 2 == 1) ? this.firstColor : this.secondColor)
                                       .setMaterial(new Material().setKd(0.4).setKs(0.3).setShininess(100).setKt(0.3)));
        }
    }

    @Override
    protected List<GeoPoint> findGeoIntersectionsHelper(Ray ray, double maxDistance) {
        List<GeoPoint> intersections = new ArrayList<>();
        for (Intersectable geometry : this.geometriesList) {
            // Check if the point inside the area of the triangle
            Triangle triangle = (Triangle) geometry;
            Vector v1 = triangle.getVertices().get(0).subtract(ray.getP0());
            Vector v2 = triangle.getVertices().get(1).subtract(ray.getP0());
            Vector v3 = triangle.getVertices().get(2).subtract(ray.getP0());
            Vector n1 = v1.crossProduct(v2).normalize();
            Vector n2 = v2.crossProduct(v3).normalize();
            Vector n3 = v3.crossProduct(v1).normalize();

            if ((ray.getDirection().dotProduct(n1) < 0 &&
                    ray.getDirection().dotProduct(n2) < 0 &&
                    ray.getDirection().dotProduct(n3) < 0) ||
                    (ray.getDirection().dotProduct(n1) > 0 &&
                            ray.getDirection().dotProduct(n2) > 0 &&
                            ray.getDirection().dotProduct(n3) > 0)) {

                double numerator = triangle.getPlane().getNormal().dotProduct(triangle.getPlane().getP0().subtract(ray.getP0()));
                double denominator = triangle.getPlane().getNormal().dotProduct(ray.getDirection());
                if (isZero(denominator)) {
                    throw new IllegalArgumentException("denominator cannot be zero");
                }
                double t = alignZero(numerator / denominator);

                if (t > 0 && alignZero(t - maxDistance) <= 0) {
                    intersections.add(new GeoPoint(triangle, ray.getPoint(t)));
                }
            }
        }
        if (intersections.isEmpty()) return null;
        return intersections;
    }
}
