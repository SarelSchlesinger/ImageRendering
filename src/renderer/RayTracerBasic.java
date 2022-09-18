package renderer;

import primitives.*;
import scene.Scene;

import java.util.List;

public class RayTracerBasic extends RayTracer {

    public RayTracerBasic(Scene scene) {
        super(scene);
    }

    @Override
    public Color traceRay(Ray ray) {
        List<Point> intersections = this.scene.getGeometries().findIntersections(ray);
        if (intersections == null) {
            return this.scene.getBackground();
        }
        return this.calcColor(ray.findClosestPoint(intersections));
    }

    private Color calcColor(Point point) {
        return this.scene.getAmbientLight().getIntensity();
    }
}
