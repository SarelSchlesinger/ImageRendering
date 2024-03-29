package lighting;

import org.junit.jupiter.api.Test;

import static java.awt.Color.*;

import renderer.ImageWriter;
import geometries.*;
import primitives.*;
import renderer.*;
import scene.Scene;

/**
 * Testing basic shadows
 *
 * @author Dan
 */
public class ShadowTests {
    private Intersectable sphere = new Sphere(new Point(0, 0, -200), 60d)
            .setEmission(new Color(BLUE))
            .setMaterial(
                    new Material().setKd(0.5).setKs(0.5).setShininess(30));
    private Material trMaterial = new Material().setKd(0.5).setKs(0.5).setShininess(30);

    private Scene scene = new Scene.SceneBuilder("Test scene")
            .setAmbientLight(new AmbientLight(new Color(java.awt.Color.WHITE), new Double3(0.15)))
            .build();
    private Camera camera = new Camera(new Point(0, 0, 1000), new Vector(0, 0, -1), new Vector(0, 1, 0))
            .setViewPlaneSize(200, 200)
            .setViewPlaneDistance(1000)
            .setRayTracer(new RayTracerBasic(scene));

    /**
     * Helper function for the tests in this module
     */
    void sphereTriangleHelper(String pictName, Triangle triangle, Point spotLocation) {
        scene.getGeometries().add(sphere, triangle.setEmission(new Color(BLUE)).setMaterial(trMaterial));
        scene.getLights().add(
                new SpotLight(
                        new Color(400, 240, 0), spotLocation, new Vector(1, 1, -3))
                        .setkLinear(1E-5).setkQuadratic(1.5E-7));
        camera.setImageWriter(new ImageWriter(pictName, 400, 400)).renderImage().writeToImage();
    }

    /**
     * Produce a picture of a sphere and triangle with point light and shade
     */
    @Test
    public void sphereTriangleInitial() {
        sphereTriangleHelper("0014shadowSphereTriangleInitial",
                             new Triangle(new Point(-70, -40, 0), new Point(-40, -70, 0), new Point(-68, -68, -4)),
                             new Point(-100, -100, 200));
    }

    /**
     * Sphere-Triangle shading - move triangle up-right
     */
    @Test
    public void sphereTriangleMove1() {
        sphereTriangleHelper("0015shadowSphereTriangleMove2",
                             new Triangle(new Point(-62, -32, 0), new Point(-32, -62, 0), new Point(-60, -60, -4)),
                             new Point(-100, -100, 200));
    }

    /**
     * Sphere-Triangle shading - move triangle upper-righter
     */
    @Test
    public void sphereTriangleMove2() {
        sphereTriangleHelper("0016shadowSphereTriangleMove1",
                             new Triangle(new Point(-49, -19, 0), new Point(-19, -49, 0), new Point(-47, -47, -4)),
                             new Point(-100, -100, 200));
    }

    /**
     * Sphere-Triangle shading - move spot closer
     */
    @Test
    public void sphereTriangleSpot1() {
        sphereTriangleHelper("0017shadowSphereTriangleSpot1",
                             new Triangle(
                                     new Point(-70, -40, 0),
                                     new Point(-40, -70, 0),
                                     new Point(-68, -68, -4)),
                             new Point(-88, -88, 120));
    }

    /**
     * Sphere-Triangle shading - move spot even more close
     */
    @Test
    public void sphereTriangleSpot2() {
        sphereTriangleHelper("0018shadowSphereTriangleSpot2",
                             new Triangle(new Point(-70, -40, 0), new Point(-40, -70, 0), new Point(-68, -68, -4)),
                             new Point(-76, -76, 70));
    }

    /**
     * Produce a picture of two triangles lighted by a spot light with a Sphere
     * producing a shading
     */
    @Test
    public void trianglesSphere() {

        scene.getGeometries().add(
                new Triangle(new Point(-150, -150, -115), new Point(150, -150, -135), new Point(75, 75, -150))
                        .setMaterial(new Material().setKs(0.8).setShininess(60)),
                new Triangle(new Point(-150, -150, -115), new Point(-70, 70, -140), new Point(75, 75, -150))
                        .setMaterial(new Material().setKs(0.8).setShininess(60)),
                new Sphere(new Point(0, 0, -11), 30d)
                        .setEmission(new Color(BLUE))
                        .setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(30)));

        scene.getLights().add(
                new SpotLight(
                        new Color(700, 400, 400),
                        new Point(40, 40, 115),
                        new Vector(-1, -1, -4))
                        .setkLinear(4E-4).setkQuadratic(2E-5));

        camera.setImageWriter(new ImageWriter("0019shadowTrianglesSphere", 600, 600)).renderImage().writeToImage();
    }

}
