package lighting;

import primitives.*;

/**
 * Ambient light for all objects in 3D space
 */
public class AmbientLight {

    // The intensity of ambient light
    private final Color intensity;

    /**
     *
     * @param Ia Illumination light
     * @param Ka light factor
     */
    public AmbientLight(Color Ia, Double3 Ka) {
        this.intensity = Ia.scale(Ka);
    }

    public AmbientLight() {
        this.intensity = Color.BLACK;
    }

    public Color getIntensity() {
        return intensity;
    }
}
