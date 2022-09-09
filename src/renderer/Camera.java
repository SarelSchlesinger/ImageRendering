package renderer;

import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import static primitives.Util.isZero;

public class Camera {

    private Point p0; // camera location
    private Vector vUp, vTo, vRight;
    private double height, width, distance;

    public Camera(Point p0, Vector vTo, Vector vUp) {
        if (!isZero(vUp.dotProduct(vTo))) {
            throw new IllegalArgumentException("The vectors must be orthogonal");
        }
        this.p0 = p0;
        this.vUp = vUp.normalize();
        this.vTo = vTo.normalize();
        this.vRight = this.vTo.crossProduct(this.vUp);
    }

    // Method Chaining
    public Camera setViewPlaneSize(double width, double height) {
        this.width = width;
        this.height = height;
        return this;
    }

    // Method Chaining
    public Camera setViewPlaneDistance(double distance) {
        this.distance = distance;
        return this;
    }

    /**
     * Creating a ray from the camera to the center of a specific pixel on the view plane
     * @param nX Pixels number on the x-axis in the view plane (columns)
     * @param nY Pixels number on the y-axis in the view plane (rows)
     * @param j index of column for a specific pixel
     * @param i index of row for a specific pixel
     * @return Creating a ray from the camera to the center of a specific pixel on the view plane
     */
    public Ray constructRay(int nX, int nY, int j, int i) {

        // pCenter is the point in the center of the view plane
        Point pCenter = this.p0.add(this.vTo.scale(this.distance));

        // pixels size
        double ratioX = this.width / nX;
        double ratioY = this.height / nY;

        // the center of P[i,j] pixel
        Point pIJ = pCenter;                            // In case that pCenter is exactly P[i,j] pixel
        double yI = -(i - (nY-1) / 2d) * ratioY;        // The distance from pCenter to p[i,j] pixel's center in the y-axis
        double xJ = (j - (nX-1) / 2d) * ratioX;         // The distance from pCenter to p[i,j] pixel's center in the x-axis


        if (!isZero(xJ)) {
            pIJ = pIJ.add(this.vRight.scale(xJ));
        }
        if (!isZero(yI)) {
            pIJ = pIJ.add(this.vUp.scale(yI));
        }

        Vector vIJ = pIJ.subtract(this.p0); // vector to the center of the pixel

        return new Ray(this.p0, vIJ);
    }

    public Point getP0() {
        return p0;
    }

    public Vector getvUp() {
        return vUp;
    }

    public Vector getvTo() {
        return vTo;
    }

    public Vector getvRight() {
        return vRight;
    }

}
