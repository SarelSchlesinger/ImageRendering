package scenes;

import geometries.Diamond;
import geometries.Intersectable;
import lighting.*;
import primitives.*;
import renderer.*;
import scene.Scene;

import java.text.DecimalFormat;
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
                                           .setkLinear(0.00001)
                                           .setkQuadratic(0.00000001),
                                   new PointLight(
                                           new Color(500, 300, 0),
                                           new Point(50, 50, 50))
                                           .setkLinear(0.00001)
                                           .setkQuadratic(0.000001),
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

        List<Diamond> diamondsList = List.of(diamond1, diamond2, diamond3, diamond4, diamond5);

        for (Diamond diamond : diamondsList) {
            for (Intersectable triangle : diamond.getTriangles()) {
                scene.getGeometries().add(triangle);
            }
        }

        DecimalFormat df = new DecimalFormat("0000");
        int NUM_OF_PICS = 1000;

        for (int i = 0; i < NUM_OF_PICS; i++) {
            long startTime = System.currentTimeMillis();
            ImageWriter imageWriter = new ImageWriter(df.format(i + 24) + "diamondsScene", 500, 500);
            camera.setImageWriter(imageWriter)
                  .setRayTracer(new RayTracerBasic(scene))
                  .renderImage()
                  .writeToImage();
            long endTime = System.currentTimeMillis();
            System.out.println("pic num " + df.format(i + 24) + " took " + ((endTime - startTime) / 1000d) + " seconds");
            camera.rotateCamera(new Ray(Point.ZERO, new Vector(0, 0, 1)), 360d / NUM_OF_PICS);
        }
    }

    public static void main(String[] args) {

        long startTime = System.currentTimeMillis();

        diamondsScene();

        long endTime = System.currentTimeMillis();

        System.out.println("That took " + ((endTime - startTime) / 1000d) + " seconds");

    }
}
