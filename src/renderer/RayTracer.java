package renderer;

import primitives.Color;
import primitives.Ray;
import scene.Scene;

import java.util.List;

public abstract class RayTracer {

    protected Scene scene;

    public RayTracer(Scene scene) {
        this.scene = scene;
    }

    public abstract Color traceRay(Ray ray);
    public abstract Color traceRays(List<Ray> rays);
}