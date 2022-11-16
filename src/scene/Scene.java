package scene;

import geometries.Geometries;
import lighting.*;
import primitives.*;

import java.util.LinkedList;
import java.util.List;

public class Scene {

    private final String name;
    private final Color background;
    private final AmbientLight ambientLight;
    private final Geometries geometries;
    private final List<LightSource> lights;

    private Scene(SceneBuilder sceneBuilder) {
        this.name = sceneBuilder.name;
        this.background = sceneBuilder.background;
        this.ambientLight = sceneBuilder.ambientLight;
        this.geometries = sceneBuilder.geometries;
        this.lights = sceneBuilder.lights;
    }

    public static class SceneBuilder {
        private final String name;
        private Color background = Color.BLACK;
        private AmbientLight ambientLight = new AmbientLight(Color.BLACK, Double3.ONE);
        private Geometries geometries = new Geometries();
        private List<LightSource> lights = new LinkedList<>();

        public SceneBuilder(String name) {
            this.name = name;
        }

        public SceneBuilder setBackground(Color background) {
            this.background = background;
            return this;
        }

        public SceneBuilder setAmbientLight(AmbientLight ambientLight) {
            this.ambientLight = ambientLight;
            return this;
        }

        public SceneBuilder setGeometries(Geometries geometries) {
            this.geometries = geometries;
            return this;
        }

        public SceneBuilder setLights(List<LightSource> lights) {
            this.lights = lights;
            return this;
        }

        public Scene build() {
            return new Scene(this);
        }
    }

    public String getName() {
        return this.name;
    }

    public Color getBackground() {
        return this.background;
    }

    public AmbientLight getAmbientLight() {
        return this.ambientLight;
    }

    public Geometries getGeometries() {
        return this.geometries;
    }

    public List<LightSource> getLights() {
        return this.lights;
    }

}
