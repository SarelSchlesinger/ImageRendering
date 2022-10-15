package renderer;

import primitives.*;
import scene.Scene;
import geometries.Intersectable.GeoPoint;

import java.util.List;

public class RayTracerBasic extends RayTracer {

    public RayTracerBasic(Scene scene) {
        super(scene);
    }

    @Override
    public Color traceRay(Ray ray) {
        List<GeoPoint> intersections = this.scene.getGeometries().findGeoIntersections(ray);
        if (intersections == null) {
            return this.scene.getBackground();
        }
        return this.calcColor(ray.findClosestGeoPoint(intersections), ray);
    }

    private Color calcColor(GeoPoint geoPoint, Ray ray) {
        return this.scene.getAmbientLight().getIntensity().add(geoPoint.geometry.getEmission());
    }
}