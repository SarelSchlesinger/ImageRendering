package scenes;

import geometries.Diamond;
import geometries.Intersectable;
import lighting.*;
import primitives.*;
import renderer.*;
import scene.Scene;

import java.util.List;

import static java.awt.Color.*;

public class diamondsScene {

    public static void diamondsScene() {

        Scene scene = new Scene.SceneBuilder("diamonds scene")
                .setAmbientLight(new AmbientLight(new Color(LIGHT_GRAY), new Double3(0.1)))
                .setLights(List.of(new SpotLight(
                                           new Color(500, 300, 0),
                                           new Point(50, -50, -50),
                                           new Vector(1, 1, -2))
                                           .setKl(0.00001)
                                           .setKq(0.00000001),
                                   new PointLight(
                                           new Color(500, 300, 0),
                                           new Point(50, 50, 50))
                                           .setKl(0.00001)
                                           .setKq(0.000001),
                                   new DirectionalLight(
                                           new Color(500, 300, 0),
                                           new Vector(5, -1, 1))))
                .build();

        Camera camera = new Camera(new Point(-2000, 0, 0),
                                   new Vector(1, 0, 0),
                                   new Vector(0, 0, 1))
                .setViewPlaneSize(150, 150)
                .setViewPlaneDistance(1000);

        Diamond diamond1 = new Diamond(16,
                                       new Point(-120, 0, 50),
                                       60,
                                       0.83,
                                       25,
                                       new Vector(0, 0, -1),
                                       new Color(GREEN),
                                       new Color(PINK));

        Diamond diamond2 = new Diamond(40,
                                       new Point(-60, 0, -63),
                                       80,
                                       0.83,
                                       25,
                                       new Vector(0, 1, 1),
                                       new Color(YELLOW),
                                       new Color(RED));

        Diamond diamond3 = new Diamond(8,
                                       new Point(0, 0, -63),
                                       120,
                                       0.83,
                                       50,
                                       new Vector(0, 0, 1),
                                       new Color(RED),
                                       new Color(BLUE));

        Diamond diamond4 = new Diamond(40,
                                       new Point(60, 0, -63),
                                       80,
                                       0.83,
                                       25,
                                       new Vector(0, -1, 1),
                                       new Color(YELLOW),
                                       new Color(RED));

        Diamond diamond5 = new Diamond(16,
                                       new Point(120, 0, 50),
                                       60,
                                       0.83,
                                       25,
                                       new Vector(0, 0, -1),
                                       new Color(GREEN),
                                       new Color(PINK));

        List<Diamond> diamondsList = List.of(diamond1,diamond2,diamond3,diamond4,diamond5);

        for (Diamond diamond: diamondsList) {
            for (Intersectable triangle : diamond.getTriangles()) {
                scene.getGeometries().add(triangle);
            }
        }



//        for (int i = 0; i < diamond1.getTriangles().size(); i++) {
//            scene.getGeometries().add(diamond1.getTriangles().get(i));
//            scene.getGeometries().add(diamond2.getTriangles().get(i));
//            scene.getGeometries().add(diamond3.getTriangles().get(i));
//            scene.getGeometries().add(diamond4.getTriangles().get(i));
//            scene.getGeometries().add(diamond5.getTriangles().get(i));
//        }

//        for (Intersectable triangle : diamond1.getTriangles()) {
//            scene.getGeometries().add(triangle);
//        }
//
//        for (Intersectable triangle : diamond2.getTriangles()) {
//            scene.getGeometries().add(triangle);
//        }
//
//        for (Intersectable triangle : diamond3.getTriangles()) {
//            scene.getGeometries().add(triangle);
//        }
//
//        for (Intersectable triangle : diamond4.getTriangles()) {
//            scene.getGeometries().add(triangle);
//        }
//
//        for (Intersectable triangle : diamond5.getTriangles()) {
//            scene.getGeometries().add(triangle);
//        }

        ImageWriter imageWriter1 = new ImageWriter("24 diamondsScene from X-axis", 500, 500);

        camera.setImageWriter(imageWriter1)
              .setRayTracer(new RayTracerBasic(scene))
              .renderImage()
              .writeToImage();

        camera.rotateCamera(new Ray(Point.ZERO, new Vector(0, 0, 1)), 90);

        ImageWriter imageWriter2 = new ImageWriter("25 diamondsScene from Y-axis", 500, 500);

        camera.setImageWriter(imageWriter2)
              .setRayTracer(new RayTracerBasic(scene))
              .renderImage()
              .writeToImage();

    }

    public static void main(String[] args) {

        long startTime = System.currentTimeMillis();

        diamondsScene();

        long endTime = System.currentTimeMillis();

        System.out.println("That took " + ((endTime - startTime) / 1000d) + " seconds");

    }
}
