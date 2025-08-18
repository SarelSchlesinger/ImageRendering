package scenes;

import geometries.Diamond;
import geometries.Geometry;
import primitives.Color;
import primitives.Point;
import primitives.Vector;
import renderer.Camera;
import renderer.ImageWriter;
import renderer.RayTracerBasic;
import scene.Scene;

import java.text.DecimalFormat;

import static java.awt.Color.BLUE;
import static java.awt.Color.RED;

public final class basicDiamond {

    public static void basicDiamondScene() {


        Scene scene = new Scene.SceneBuilder("basic diamond").build();

        Camera camera = new Camera(new Point(0, 0, 0),
                                   new Vector(1, 1, 0),
                                   new Vector(0, 0, 1))
                .setViewPlaneSize(50, 50)
                .setViewPlaneDistance(50)
                .setRaysPerPixel(400);

        Diamond diamond = new Diamond(8,
                                      new Point(10, 10, -5.5),
                                      6.7,
                                      0.8333333333333333333333,
                                      3,
                                      new Vector(0, 0, 1),
                                      new Color(RED),
                                      new Color(BLUE));

        for (Geometry triangle : diamond.getTriangles()) {
            scene.getGeometries().add(triangle);
        }

        DecimalFormat df = new DecimalFormat("000");
        camera.setImageWriter(new ImageWriter("0023basicDiamond_" + df.format(camera.getRaysPerPixel()) + "_rays_per_pixel", 500, 500))
              .setRayTracer(new RayTracerBasic(scene))
              .renderImage()
              .writeToImage();
    }

    public static void main(String[] args) {

        basicDiamondScene();
    }
}