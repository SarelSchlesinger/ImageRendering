package lighting;

import org.junit.jupiter.api.Test;

import static java.awt.Color.*;

import geometries.*;
import primitives.*;
import renderer.*;
import scene.Scene;

/**
 * Tests for reflection and transparency functionality, test for partial shadows
 * (with transparency)
 *
 * @author dzilb
 */
public class ReflectionRefractionTests {

    private Scene scene1 = new Scene.SceneBuilder("Test scene").build();

    private Scene scene2 = new Scene.SceneBuilder("Test scene")
            .setAmbientLight(new AmbientLight(new Color(255, 255, 255), new Double3(0.1)))
            .build();

    private Scene scene3 = new Scene.SceneBuilder("Test scene")
            .setAmbientLight(new AmbientLight(new Color(WHITE), new Double3(0.15)))
            .build();

    /**
     * Produce a picture of a sphere lighted by a spot light
     */
    @Test
    public void twoSpheres() {
        Camera camera = new Camera(new Point(0, 0, 1000), new Vector(0, 0, -1), new Vector(0, 1, 0))
                .setViewPlaneSize(150, 150)
                .setViewPlaneDistance(1000);

        scene1.getGeometries().add(
                new Sphere(new Point(0, 0, -50), 50d).setEmission(new Color(BLUE))
                                                     .setMaterial(new Material().setKd(0.4).setKs(0.3).setShininess(100).setKt(0.3)),
                new Sphere(new Point(0, 0, -50), 25d).setEmission(new Color(RED))
                                                     .setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(100)));
        scene1.getLights().add(
                new SpotLight(new Color(1000, 600, 0), new Point(-100, -100, 500), new Vector(-1, -1, -2))
                        .setkLinear(0.0004)
                        .setkQuadratic(0.0000006));

        camera.setImageWriter(new ImageWriter("0020refractionTwoSpheres", 500, 500))
              .setRayTracer(new RayTracerBasic(scene1))
              .renderImage()
              .writeToImage();
    }

    /**
     * Produce a picture of a sphere lighted by a spot light
     */
    @Test
    public void twoSpheresOnMirrors() {
        Camera camera = new Camera(new Point(0, 0, 10000), new Vector(0, 0, -1), new Vector(0, 1, 0))
                .setViewPlaneSize(2500, 2500)
                .setViewPlaneDistance(10000);

        scene2.getGeometries().add(
                new Sphere(new Point(-950, -900, -1000), 400d)
                        .setEmission(new Color(0, 0, 100))
                        .setMaterial(new Material().setKd(0.25).setKs(0.25).setShininess(20).setKt(0.5)),
                new Sphere(new Point(-950, -900, -1000), 200d)
                        .setEmission(new Color(100, 20, 20))
                        .setMaterial(new Material().setKd(0.25).setKs(0.25).setShininess(20)),
                new Triangle(new Point(1500, -1500, -1500), new Point(-1500, 1500, -1500), new Point(670, 670, 3000))
                        .setEmission(new Color(20, 20, 20))
                        .setMaterial(new Material().setKr(1)),
                new Triangle(new Point(1500, -1500, -1500), new Point(-1500, 1500, -1500), new Point(-1500, -1500, -2000))
                        .setEmission(new Color(20, 20, 20))
                        .setMaterial(new Material().setKr(0.5)));

        scene2.getLights().add(
                new SpotLight(new Color(1020, 400, 400), new Point(-750, -750, -150), new Vector(-1, -1, -4))
                        .setkLinear(0.00001)
                        .setkQuadratic(0.000005));

        ImageWriter imageWriter = new ImageWriter("0021reflectionTwoSpheresMirrored", 500, 500);
        camera.setImageWriter(imageWriter)
              .setRayTracer(new RayTracerBasic(scene2))
              .renderImage()
              .writeToImage();
    }

    /**
     * Produce a picture of two triangles lighted by a spot light with a partially
     * transparent Sphere producing partial shadow
     */
    @Test
    public void trianglesTransparentSphere() {
        Camera camera = new Camera(new Point(0, 0, 1000), new Vector(0, 0, -1), new Vector(0, 1, 0))
                .setViewPlaneSize(200, 200)
                .setViewPlaneDistance(1000);

        scene3.getGeometries().add(
                new Triangle(new Point(-150, -150, -115), new Point(150, -150, -135), new Point(75, 75, -150))
                        .setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(60)),
                new Triangle(new Point(-150, -150, -115), new Point(-70, 70, -140), new Point(75, 75, -150))
                        .setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(60)),
                new Sphere(new Point(60, 50, -50), 30d)
                        .setEmission(new Color(BLUE))
                        .setMaterial(new Material().setKd(0.2).setKs(0.2).setShininess(30).setKt(0.6)));

        scene3.getLights().add(
                new SpotLight(new Color(700, 400, 400), new Point(60, 50, 0), new Vector(0, 0, -1))
                        .setkLinear(4E-5)
                        .setkQuadratic(2E-7));

        ImageWriter imageWriter = new ImageWriter("0022refractionShadow", 600, 600);
        camera.setImageWriter(imageWriter)
              .setRayTracer(new RayTracerBasic(scene3))
              .renderImage()
              .writeToImage();
    }
}
