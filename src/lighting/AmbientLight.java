package lighting;

import primitives.Color;
import primitives.Double3;

/**
 * An ambient light source represents a fixed-intensity and fixed-color light source
 * that affects all objects in the scene equally
 */
public class AmbientLight extends Light {

    /**
     * @param Ia illumination light
     * @param Ka light factor
     */
    public AmbientLight(Color Ia, Double3 Ka) {
        super(Ia.scale(Ka));
    }

    public AmbientLight() {
        super(Color.BLACK);
    }

}
