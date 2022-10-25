package renderer;

import primitives.*;

import java.util.MissingResourceException;

import static primitives.Util.isZero;

public class Camera {

    private Point cameraPosition;
    private Vector vUp, vTo, vRight;
    private double height, width, distance;
    private ImageWriter imageWriter;
    private RayTracer rayTracer;

    public Camera(Point cameraPosition, Vector vTo, Vector vUp) {
        if (!isZero(vUp.dotProduct(vTo))) {
            throw new IllegalArgumentException("The vectors must be orthogonal");
        }
        this.cameraPosition = cameraPosition;
        this.vUp = vUp.normalize();
        this.vTo = vTo.normalize();
        this.vRight = this.vTo.crossProduct(this.vUp);
    }

    // Method Chaining
    public Camera setViewPlaneSize(double width, double height) {
        if (width <= 0 || height <= 0) {
            throw new IllegalArgumentException("Arguments must be greater than zero");
        }
        this.width = width;
        this.height = height;
        return this;
    }

    // Method Chaining
    public Camera setViewPlaneDistance(double distance) {
        if (distance <= 0) {
            throw new IllegalArgumentException("Argument must be greater than zero");
        }
        this.distance = distance;
        return this;
    }

    // Method Chaining
    public Camera setImageWriter(ImageWriter imageWriter) {
        this.imageWriter = imageWriter;
        return this;
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
    public Ray constructRay(int nX, int nY, int j, int i) {

        // pCenter is the point in the center of the view plane
        Point pCenter = this.cameraPosition.add(this.vTo.scale(this.distance));

        // pixels size
        double ratioX = this.width / nX;
        double ratioY = this.height / nY;

        // the center of P[i,j] pixel
        Point pIJ = pCenter;                            // In case that pCenter is exactly P[i,j] pixel
        double yI = -(i - (nY - 1) / 2d) * ratioY;        // The distance from pCenter to p[i,j] pixel's center in the y-axis
        double xJ = (j - (nX - 1) / 2d) * ratioX;         // The distance from pCenter to p[i,j] pixel's center in the x-axis


        if (!isZero(xJ)) {
            pIJ = pIJ.add(this.vRight.scale(xJ));
        }
        if (!isZero(yI)) {
            pIJ = pIJ.add(this.vUp.scale(yI));
        }

        Vector vIJ = pIJ.subtract(this.cameraPosition); // vector to the center of the pixel

        return new Ray(this.cameraPosition, vIJ);
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

    public Camera renderImage() {
        try {
            if (this.cameraPosition == null) {
                throw new MissingResourceException("Missing resource value", Point.class.getName(), "");
            }
            if (this.vUp == null || this.vRight == null || this.vTo == null) {
                throw new MissingResourceException("Missing resource value", Vector.class.getName(), "");
            }
            if (this.imageWriter == null) {
                throw new MissingResourceException("missing resource value", ImageWriter.class.getName(), "");
            }
            if (this.rayTracer == null) {
                throw new MissingResourceException("missing resource value", RayTracer.class.getName(), "");
            }

            // IMAGE RENDERING
            // Pass a ray from the camera through each pixel in the view plane and set the color
            int nX = this.imageWriter.getNx();
            int nY = this.imageWriter.getNy();
            for (int row = 0; row < nY; row++) {
                for (int col = 0; col < nX; col++) {
                    Ray ray = this.constructRay(nX, nY, col, row);
                    Color pixelColor = this.rayTracer.traceRay(ray);
                    this.imageWriter.writePixel(col, row, pixelColor);
                }
            }

        } catch (MissingResourceException exception) {
            throw new UnsupportedOperationException("The fields must not be null ----> " + exception.getClassName());
        }
        return this;
    }

    public Camera writeToImage() {
        if (this.imageWriter == null) {
            throw new MissingResourceException("missing resource value", ImageWriter.class.getName(), "");
        }
        this.imageWriter.writeToImage();
        return this;
    }

    public void printGrid(int interval, Color color) {

        if (this.imageWriter == null) {
            throw new MissingResourceException("missing resource value", ImageWriter.class.getName(), "");
        }

        int nY = this.imageWriter.getNy();
        int nX = this.imageWriter.getNx();
        for (int i = 0; i < nY; i++) {
            for (int j = 0; j < nX; j++) {
                if (i % interval == 0 || j % interval == 0) {
                    imageWriter.writePixel(i, j, color);
                }
            }
        }
    }
}
