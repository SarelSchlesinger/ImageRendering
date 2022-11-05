package lighting;

import primitives.Color;

/**
 * An abstract class that represents all types of light sources in the scene
 */
abstract class Light {

    /**
     * Light's color. The intensity of RGB colors
     */
    protected final Color intensity;

    protected Light(Color intensity) {
        this.intensity = intensity;
    }

    public Color getIntensity() {
        return this.intensity;
    }
}
