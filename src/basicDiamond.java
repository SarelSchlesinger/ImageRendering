import geometries.Diamond;
import geometries.Intersectable;
import lighting.*;
import primitives.*;
import renderer.*;
import scene.Scene;

import static java.awt.Color.*;

public final class basicDiamond {

    public static void createDiamondsScene() {

        Scene scene = new Scene.SceneBuilder("basic diamond")
                .setAmbientLight(new AmbientLight(new Color(LIGHT_GRAY), new Double3(0.2)))
                .build();
        Camera camera = new Camera(new Point(0, 0, 0), new Vector(1, 1, 0), new Vector(0, 0, 1))
                .setViewPlaneSize(5, 5)
                .setViewPlaneDistance(5);


        Diamond diamond1 = new Diamond(8,
                                       new Point(10, 10, -5.5),
                                       6.7,
                                       0.8333333333333333333333,
                                       3,
                                       new Vector(0, 0, 1),
                                       new Color(RED),
                                       new Color(BLUE));

        for (Intersectable triangle : diamond1.geometriesList) {
            scene.getGeometries().add(triangle);
        }

        scene.getLights().add(new SpotLight(new Color(1000, 600, 0), new Point(-100, -100, 500), new Vector(-1, -1, -2)).setKl(0.0004).setKq(0.0000006));
        scene.getLights().add(new SpotLight(new Color(50, 200, 35), new Point(0, 0, 20), new Vector(0, 0, -1)));

        camera.setImageWriter(new ImageWriter("23 basic diamond", 500, 500))
              .setRayTracer(new RayTracerBasic(scene))
              .renderImage()
              .writeToImage();
    }

    public static void main(String[] args) {

        createDiamondsScene();


    }
}

