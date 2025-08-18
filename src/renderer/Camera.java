package renderer;

import primitives.Color;
import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import java.util.LinkedList;
import java.util.List;
import java.util.MissingResourceException;

import static primitives.Util.findNearestSquareRoot;
import static primitives.Util.isZero;

public class Camera {

    private Point  cameraPosition;
    private Vector vTo, vUp, vRight;
    private double viewPlaneHeightSize, viewPlaneWidthSize, distance;
    private int         raysPerPixel = 1;
    private ImageWriter imageWriter;
    private RayTracer   rayTracer;

    public Camera(Point cameraPosition, Vector vTo, Vector vUp) {
        if (!isZero(vUp.dotProduct(vTo))) {
            throw new IllegalArgumentException("The vectors must be orthogonal");
        }
        this.cameraPosition = cameraPosition;
        this.vUp            = vUp.normalize();
        this.vTo            = vTo.normalize();
        this.vRight         = this.vTo.crossProduct(this.vUp);
    }

    // Method Chaining
    public Camera setViewPlaneSize(double viewPlaneWidthSize, double viewPlaneHeightSize) {
        if (viewPlaneWidthSize <= 0 || viewPlaneHeightSize <= 0) {
            throw new IllegalArgumentException("The width and height of the VP must be greater than zero");
        }
        this.viewPlaneWidthSize  = viewPlaneWidthSize;
        this.viewPlaneHeightSize = viewPlaneHeightSize;
        return this;
    }

    // Method Chaining
    public Camera setViewPlaneDistance(double distance) {
        if (distance <= 0) {
            throw new IllegalArgumentException("Distance must be greater than zero");
        }
        this.distance = distance;
        return this;
    }

    public Point getCameraPosition() {
        return this.cameraPosition;
    }

    public Vector getvUp() {
        return this.vUp;
    }

    public Vector getvTo() {
        return this.vTo;
    }

    public Vector getvRight() {
        return this.vRight;
    }

    public double getViewPlaneHeightSize() {
        return this.viewPlaneHeightSize;
    }

    public double getViewPlaneWidthSize() {
        return this.viewPlaneWidthSize;
    }

    public double getDistance() {
        return this.distance;
    }

    public int getRaysPerPixel() {
        return this.raysPerPixel;
    }

    // Method Chaining
    public Camera setRaysPerPixel(int raysPerPixel) {
        if (raysPerPixel < 1) {
            throw new IllegalArgumentException("raysPerPixel must be greater than zero");
        }
        this.raysPerPixel = raysPerPixel;
        return this;
    }

    public ImageWriter getImageWriter() {
        return this.imageWriter;
    }

    // Method Chaining
    public Camera setImageWriter(ImageWriter imageWriter) {
        this.imageWriter = imageWriter;
        return this;
    }

    public RayTracer getRayTracer() {
        return this.rayTracer;
    }

    // Method Chaining
    public Camera setRayTracer(RayTracer rayTracer) {
        this.rayTracer = rayTracer;
        return this;
    }

    /**
     * Creating a ray from the camera to the center of a specific pixel on the view plane
     *
     * @param nX Pixels number on the x-axis in the view plane (columns)
     * @param nY Pixels number on the y-axis in the view plane (rows)
     * @param j  index of column for a specific pixel
     * @param i  index of row for a specific pixel
     * @return Creating a ray from the camera to the center of a specific pixel on the view plane
     */
    public Ray constructRay(int nX, int nY, double j, double i) {

        // pCenter is the point in the center of the view plane
        Point pCenter = this.getCameraPosition().add(this.getvTo().scale(this.getDistance()));

        // pixels size
        double ratioX = this.getViewPlaneWidthSize() / nX;
        double ratioY = this.getViewPlaneHeightSize() / nY;

        // the center of P[i,j] pixel
        Point pIJ = pCenter;                            // In case that pCenter is exactly P[i,j] pixel
        double yI = -(i - (nY - 1) / 2d) * ratioY;        // The distance from pCenter to p[i,j] pixel's center in the y-axis
        double xJ = (j - (nX - 1) / 2d) * ratioX;         // The distance from pCenter to p[i,j] pixel's center in the x-axis


        if (!isZero(xJ)) {
            pIJ = pIJ.add(this.getvRight().scale(xJ));
        }
        if (!isZero(yI)) {
            pIJ = pIJ.add(this.getvUp().scale(yI));
        }

        Vector vIJ = pIJ.subtract(this.getCameraPosition()); // vector to the center of the pixel

        return new Ray(this.getCameraPosition(), vIJ);
    }

    /*
    public List<Ray> constructRays(int nX, int nY, int j, int i, int numOfRays) {
        List<Ray> rays = new LinkedList<>();
        for (int k = 0; k < numOfRays; k++) {
            rays.add(constructRay(nX, nY, j + random(), i + random()));
        }
        return rays;
    }
    */

    public List<Ray> constructRays(int nX, int nY, int j, int i, int numOfRays) {
        numOfRays = findNearestSquareRoot(numOfRays);
        List<Ray> rays = new LinkedList<>();
        for (int k = 0 ; k < numOfRays ; k++) {
            for (int l = 0 ; l < numOfRays ; l++) {
                rays.add(constructRay(nX, nY, j + (double) k / numOfRays, i + (double) l / numOfRays));
            }
        }
        return rays;
    }

    public Camera renderImage() {
        try {
            if (this.getCameraPosition() == null) {
                throw new MissingResourceException("Missing resource value", Point.class.getName(), "");
            }
            if (this.getvUp() == null || this.getvRight() == null || this.getvTo() == null) {
                throw new MissingResourceException("Missing resource value", Vector.class.getName(), "");
            }
            if (this.getImageWriter() == null) {
                throw new MissingResourceException("missing resource value", ImageWriter.class.getName(), "");
            }
            if (this.getRayTracer() == null) {
                throw new MissingResourceException("missing resource value", RayTracer.class.getName(), "");
            }

            // IMAGE RENDERING
            // Pass a ray from the camera through each pixel in the view plane and set the color
            int nX = this.getImageWriter().getNx();
            int nY = this.getImageWriter().getNy();

            for (int row = 0 ; row < nY ; row++) {
                for (int col = 0 ; col < nX ; col++) {
                    List<Ray> rays = this.constructRays(nX, nY, col, row, this.getRaysPerPixel());
                    Color pixelColor = this.getRayTracer().traceRays(rays);
                    this.getImageWriter().writePixel(col, row, pixelColor);
                }
            }

        } catch (MissingResourceException exception) {
            throw new UnsupportedOperationException("The fields must not be null ----> " + exception.getClassName());
        }
        return this;
    }

    public Camera writeToImage() {
        if (this.getImageWriter() == null) {
            throw new MissingResourceException("missing resource value", ImageWriter.class.getName(), "");
        }
        this.getImageWriter().writeToImage();
        return this;
    }

    public void printGrid(int interval, Color color) {

        if (this.getImageWriter() == null) {
            throw new MissingResourceException("missing resource value", ImageWriter.class.getName(), "");
        }

        int nY = this.getImageWriter().getNy();
        int nX = this.getImageWriter().getNx();
        for (int i = 0 ; i < nY ; i++) {
            for (int j = 0 ; j < nX ; j++) {
                if (i % interval == 0 || j % interval == 0) {
                    this.getImageWriter().writePixel(i, j, color);
                }
            }
        }
    }

    public Camera rotateCamera(Ray rotationAxis, double angle) {
        this.cameraPosition = rotationAxis.getP0().add(this.getCameraPosition()
                                                           .subtract(rotationAxis.getP0())
                                                           .rotateVector(rotationAxis.getDirection(), angle));
        this.vTo            = this.getvTo().rotateVector(rotationAxis.getDirection(), angle);
        this.vUp            = this.getvUp().rotateVector(rotationAxis.getDirection(), angle);
        this.vRight         = this.getvTo().crossProduct(this.getvUp());
        return this;
    }
}