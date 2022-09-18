package renderer;

import org.junit.jupiter.api.Test;

import lighting.*;
import geometries.*;
import primitives.*;
import renderer.*;
import scene.*;

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
                .setImageWriter(new ImageWriter("baseRenderTest", 1000, 1000))
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

}
