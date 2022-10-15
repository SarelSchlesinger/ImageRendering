package renderer;

import org.junit.jupiter.api.Test;

import lighting.*;
import geometries.*;
import primitives.*;
import scene.*;
import renderer.*;

import static java.awt.Color.*;

/**
 * Test rendering a basic image
 *
 * @author Dan
 */
public class RenderTests {

    /**
     * Produce a scene with basic 3D model and render it into a png image with a
     * grid
     */
    @Test
    public void basicRenderTwoColorTest() {

        Scene scene = new Scene.SceneBuilder("TestScene")
                .setAmbientLight(
                        new AmbientLight(new Color(255, 191, 191), new Double3(1, 1, 1)))
                .setBackground(new Color(75, 127, 90))
                .build();


        Camera camera = new Camera(Point.ZERO, new Vector(0, 0, -1), new Vector(0, 1, 0))
                .setViewPlaneDistance(100)
                .setViewPlaneSize(500, 500)
                .setImageWriter(new ImageWriter("twoColorsRenderTest", 1000, 1000))
                .setRayTracer(new RayTracerBasic(scene));


        scene.getGeometries().add(
                new Sphere(new Point(0, 0, -100), 50),
                new Triangle(new Point(-100, 0, -100), new Point(0, -100, -100), new Point(-100, -100, -100)),
                new Triangle(new Point(-100, 0, -100), new Point(0, 100, -100), new Point(-100, 100, -100)),
                new Triangle(new Point(100, 0, -100), new Point(0, -100, -100), new Point(100, -100, -100)));


        camera.renderImage();
        camera.printGrid(100, new Color(255, 255, 0));
        camera.writeToImage();
    }

    /**
     * Produce a scene with basic 3D model - including individual lights of the
     * bodies and render it into a png image with a grid
     */
    @Test
    public void basicRenderMultiColorTest() {
        Scene scene = new Scene.SceneBuilder("TestScene")
                .setAmbientLight(
                        new AmbientLight(new Color(WHITE), new Double3(0.2)))
                .build();

        scene.getGeometries().add(
                new Sphere(new Point(0, 0, -100), 50),
                new Triangle(new Point(-100, 0, -100), new Point(0, 100, -100), new Point(-100, 100, -100))
                        .setEmission(new Color(GREEN)),
                new Triangle(new Point(-100, 0, -100), new Point(0, -100, -100), new Point(-100, -100, -100))
                        .setEmission(new Color(RED)),
                new Triangle(new Point(100, 0, -100), new Point(0, -100, -100), new Point(100, -100, -100))
                        .setEmission(new Color(BLUE)));

        Camera camera = new Camera(Point.ZERO, new Vector(0, 0, -1), new Vector(0, 1, 0))
                .setViewPlaneDistance(100)
                .setViewPlaneSize(500, 500)
                .setImageWriter(new ImageWriter("multiColorsRenderTest", 1000, 1000))
                .setRayTracer(new RayTracerBasic(scene));

        camera.renderImage();
        camera.printGrid(100, new Color(WHITE));
        camera.writeToImage();
    }

}
