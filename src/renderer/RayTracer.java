package renderer;

import primitives.*;
import scene.Scene;

public abstract class RayTracer {

    protected Scene scene;

    public RayTracer(Scene scene) {
        this.scene = scene;
    }

    public abstract Color traceRay(Ray ray);
}
