package lighting;

import org.junit.jupiter.api.Test;

import geometries.*;
import primitives.*;
import renderer.*;
import scene.Scene;

import java.util.Collections;

import static java.awt.Color.*;

/**
 * Test rendering a basic image
 *
 * @author Dan
 */
public class LightsTests {
    private Scene scene1 = new Scene.SceneBuilder("Test scene").build();
    private Scene scene2 = new Scene.SceneBuilder("Test scene")
            .setAmbientLight(new AmbientLight(new Color(WHITE), new Double3(0.15))).build();
    private Camera camera1 = new Camera(
            new Point(0, 0, 1000),
            new Vector(0, 0, -1),
            new Vector(0, 1, 0))
            .setViewPlaneSize(150, 150)
            .setViewPlaneDistance(1000);
    private Camera camera2 = new Camera(
            new Point(0, 0, 1000),
            new Vector(0, 0, -1),
            new Vector(0, 1, 0))
            .setViewPlaneSize(200, 200)
            .setViewPlaneDistance(1000);

    private Point[] p = { // The Triangles' vertices:
            new Point(-110, -110, -150), // the shared left-bottom
            new Point(95, 100, -150), // the shared right-top
            new Point(110, -110, -150), // the right-bottom
            new Point(-75, 78, 100)}; // the left-top
    private Point trPL = new Point(30, 10, -100); // Triangles test Position of Light
    private Point spPL = new Point(-50, -50, 25); // Sphere test Position of Light
    private Color trCL = new Color(800, 500, 250); // Triangles test Color of Light
    private Color spCL = new Color(800, 500, 0); // Sphere test Color of Light
    private Vector trDL = new Vector(-2, -2, -2); // Triangles test Direction of Light
    private Material material = new Material().setKd(0.5).setKs(0.5).setShininess(300);
    private Geometry triangle1 = new Triangle(p[0], p[1], p[2]).setMaterial(material);
    private Geometry triangle2 = new Triangle(p[0], p[1], p[3]).setMaterial(material);
    private Geometry sphere = new Sphere(new Point(0, 0, -50), 50d)
            .setEmission(new Color(BLUE).reduce(2))
            .setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(300));

    /**
     * Produce a picture of a sphere lighted by a directional light
     */
    @Test
    public void sphereDirectional() {
        scene1.getGeometries().add(sphere);
        scene1.getLights().add(new DirectionalLight(spCL, new Vector(1, 1, -0.5)));

        ImageWriter imageWriter = new ImageWriter("0004lightSphereDirectional", 500, 500);
        camera1.setImageWriter(imageWriter)
               .setRayTracer(new RayTracerBasic(scene1))
               .renderImage()
               .writeToImage();
    }

    /**
     * Produce a picture of a sphere lighted by a point light
     */
    @Test
    public void spherePoint() {
        scene1.getGeometries().add(sphere);
        scene1.getLights().add(new PointLight(spCL, spPL).setkLinear(0.001).setkQuadratic(0.0002));

        ImageWriter imageWriter = new ImageWriter("0005lightSpherePoint", 500, 500);
        camera1.setImageWriter(imageWriter)
               .setRayTracer(new RayTracerBasic(scene1))
               .renderImage()
               .writeToImage();
    }

    /**
     * Produce a picture of a sphere lighted by a spot light
     */
    @Test
    public void sphereSpot() {
        scene1.getGeometries().add(sphere);
        scene1.getLights().add(new SpotLight(spCL, spPL, new Vector(1, 1, -0.5))
                                       .setkLinear(0.001)
                                       .setkQuadratic(0.0001));

        ImageWriter imageWriter = new ImageWriter("0006lightSphereSpot", 500, 500);
        camera1.setImageWriter(imageWriter)
               .setRayTracer(new RayTracerBasic(scene1))
               .renderImage()
               .writeToImage();
    }

    /**
     * Produce a picture of two triangles lighted by a directional light
     */
    @Test
    public void trianglesDirectional() {
        scene2.getGeometries().add(triangle1, triangle2);
        scene2.getLights().add(new DirectionalLight(trCL, trDL));

        ImageWriter imageWriter = new ImageWriter("0007lightTrianglesDirectional", 500, 500);
        camera2.setImageWriter(imageWriter)
               .setRayTracer(new RayTracerBasic(scene2))
               .renderImage()
               .writeToImage();
    }

    /**
     * Produce a picture of two triangles lighted by a point light
     */
    @Test
    public void trianglesPoint() {
        scene2.getGeometries().add(triangle1, triangle2);
        scene2.getLights().add(new PointLight(trCL, trPL)
                                       .setkLinear(0.001)
                                       .setkQuadratic(0.0002));

        ImageWriter imageWriter = new ImageWriter("0008lightTrianglesPoint", 500, 500);
        camera2.setImageWriter(imageWriter)
               .setRayTracer(new RayTracerBasic(scene2))
               .renderImage()
               .writeToImage();
    }

    /**
     * Produce a picture of two triangles lighted by a spot light
     */
    @Test
    public void trianglesSpot() {
        scene2.getGeometries().add(triangle1, triangle2);
        scene2.getLights().add(new SpotLight(trCL, trPL, trDL)
                                       .setkLinear(0.001)
                                       .setkQuadratic(0.0001));

        ImageWriter imageWriter = new ImageWriter("0009lightTrianglesSpot", 500, 500);
        camera2.setImageWriter(imageWriter)
               .setRayTracer(new RayTracerBasic(scene2))
               .renderImage()
               .writeToImage();
    }

    /**
     * Produce a picture of a sphere lighted by a narrow spot light
     */
    @Test
    public void sphereSpotSharp() {
        scene1.getGeometries().add(sphere);
        scene1.getLights().add(new SpotLight(spCL, spPL, new Vector(1, 1, -0.5))
                                       .setNarrowBeam(10)
                                       .setkLinear(0.001)
                                       .setkQuadratic(0.00004));

        ImageWriter imageWriter = new ImageWriter("0010lightSphereSpotSharp", 500, 500);
        camera1.setImageWriter(imageWriter)
               .setRayTracer(new RayTracerBasic(scene1))
               .renderImage()
               .writeToImage();
    }

    /**
     * Produce a picture of two triangles lighted by a narrow spot light
     */
    @Test
    public void trianglesSpotSharp() {
        scene2.getGeometries().add(triangle1, triangle2);
        scene2.getLights().add(
                new SpotLight(trCL, trPL, trDL)
                        .setNarrowBeam(10)
                        .setkLinear(0.001)
                        .setkQuadratic(0.00004));

        ImageWriter imageWriter = new ImageWriter("0011lightTrianglesSpotSharp", 500, 500);
        camera2.setImageWriter(imageWriter)
               .setRayTracer(new RayTracerBasic(scene2))
               .renderImage()
               .writeToImage();
    }

    /**
     * Produce a picture of a sphere lighted by multi light sources
     */
    @Test
    public void sphereMultiLightSources() {
        scene1.getGeometries().add(sphere);

        Collections.addAll(scene1.getLights(),
                           new SpotLight(
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
                                   new Vector(5, -1, 1)));


        ImageWriter imageWriter = new ImageWriter("0012multiLightsSphere", 500, 500);

        camera1.setImageWriter(imageWriter)
               .setRayTracer(new RayTracerBasic(scene1))
               .renderImage()
               .writeToImage();
    }

    /**
     * Produce a picture of two triangles lighted by multi light sources
     */
    @Test
    public void trianglesMultiLightSources() {

        scene2.getGeometries().add(triangle1, triangle2);

        Collections.addAll(scene2.getLights(),
                           new SpotLight(new Color(RED), trPL, trDL)
                                   .setkLinear(0.001)
                                   .setkQuadratic(0.0001),
                           new PointLight(new Color(BLUE), trPL)
                                   .setkLinear(0.001)
                                   .setkQuadratic(0.0002),
                           new DirectionalLight(new Color(GRAY), trDL));

        ImageWriter imageWriter = new ImageWriter("0013multiLightsTriangles", 500, 500);

        camera2.setImageWriter(imageWriter)
               .setRayTracer(new RayTracerBasic(scene2))
               .renderImage()
               .writeToImage();
    }
}
