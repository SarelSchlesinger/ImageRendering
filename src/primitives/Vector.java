package primitives;

import static java.lang.Math.sqrt;
import static java.lang.Math.abs;
import static java.lang.Math.cos;
import static java.lang.Math.sin;
import static java.lang.Math.pow;
import static java.lang.Math.toRadians;
import static primitives.Util.isZero;

public class Vector extends Point {

    public Vector(double x, double y, double z) {
        super(x, y, z);
        if (isZero(x) && isZero(y) && isZero(z)) {
            throw new IllegalArgumentException("Cannot get vector of zero");
        }
    }

    public Vector(Double3 xyz) {
        super(xyz);
        if (isZero(xyz.d1) && isZero(xyz.d2) && isZero(xyz.d3)) {
            throw new IllegalArgumentException("Cannot get vector of zero");
        }
    }

    @Override
    public boolean equals(Object o) {
        return super.equals(o);
    }

    @Override
    public String toString() {
        return "Vector{} " + super.toString();
    }

    public Vector add(Vector vector) {
        return new Vector(this.xyz.add(vector.xyz));
    }

    public Vector scale(double scalar) {
        if (scalar == 0) {
            throw new IllegalArgumentException("Cannot get vector of zero");
        }
        return new Vector(this.xyz.scale(scalar));
    }

    public double dotProduct(Vector vector) {
        return this.xyz.d1 * vector.xyz.d1 +
                this.xyz.d2 * vector.xyz.d2 +
                this.xyz.d3 * vector.xyz.d3;
    }

    public Vector crossProduct(Vector vector) {
        return new Vector(this.xyz.d2 * vector.xyz.d3 - this.xyz.d3 * vector.xyz.d2,
                          this.xyz.d3 * vector.xyz.d1 - this.xyz.d1 * vector.xyz.d3,
                          this.xyz.d1 * vector.xyz.d2 - this.xyz.d2 * vector.xyz.d1);
    }

    public double lengthSquared() {
        return this.xyz.d1 * this.xyz.d1 +
                this.xyz.d2 * this.xyz.d2 +
                this.xyz.d3 * this.xyz.d3;
    }

    public double length() {
        return sqrt(this.lengthSquared());
    }

    public Vector normalize() {

        return new Vector(this.xyz.reduce(this.length()));
    }

    public Boolean isParallel(Vector vector) {
        Vector v1 = this.normalize();
        Vector v2 = vector.normalize();
        return abs(v1.getX()) == abs(v2.getX()) && abs(v1.getY()) == abs(v2.getY()) && abs(v1.getZ()) == abs(v2.getZ());
    }

    /**
     * method to find an orthogonal vector to a given vector
     */
    public Vector findOrthogonalVector() {
        if (this.getX() > this.getZ()) {
            if (isZero(this.getX())) {
                return new Vector(1,0,0).normalize();
            } else {
                return new Vector(this.getY(), -this.getX(), 0).normalize();
            }
        } else {
            return new Vector(0, -this.getZ(), this.getY()).normalize();
        }
    }

    /**
     * method to rotate a vector by a given angle around the x-axis
     *
     * @param angle - the angle of rotation
     * @return a new rotated vector
     */
    public Vector rotationAroundXAxis(double angle) {
        double radian = toRadians(angle);
        return new Vector(this.getX() + 0 + 0,
                          0 + this.getY() * cos(radian) - this.getZ() * sin(radian),
                          0 + this.getY() * sin(radian) + this.getZ() * cos(radian));
    }

    /**
     * method to rotate a vector by a given angle around the y-axis
     *
     * @param angle - the angle of rotation
     * @return a new rotated vector
     */
    public Vector rotationAroundYAxis(double angle) {
        double radian = toRadians(angle);
        return new Vector(this.getX() * cos(radian) + 0 + this.getZ() * sin(radian),
                          0 + this.getY() + 0,
                          -this.getX() * sin(radian) + 0 + this.getZ() * cos(radian));
    }

    /**
     * method to rotate a vector by a given angle around the z-axis
     *
     * @param angle - the angle of rotation
     * @return a new rotated vector
     */
    public Vector rotationAroundZAxis(double angle) {
        double radian = toRadians(angle);
        return new Vector(this.getX() * cos(radian) - this.getY() * sin(radian) + 0,
                          this.getX() * sin(radian) + this.getY() * cos(radian) + 0,
                          0 + 0 + this.getZ());
    }

    /**
     * method to rotate a vector by a given angle around an arbitrary axis.
     * this calculation is based on https://mathworld.wolfram.com/RodriguesRotationFormula.html
     *
     * @param axis  - the axis of the rotation
     * @param angle - the angle of rotation
     * @return a new rotated vector
     */
    public Vector rotationAroundArbitraryAxis(Vector axis, double angle) {
        Vector normalizedVector = axis.normalize();
        double cos_angle = cos(toRadians(angle));
        double sin_angle = sin(toRadians(angle));
        return new Vector(this.getX() * (cos_angle + pow(normalizedVector.getX(), 2) * (1 - cos_angle))
                                  + this.getY() * (normalizedVector.getX() * normalizedVector.getY() * (1 - cos_angle) - normalizedVector.getZ() * sin_angle)
                                  + this.getZ() * (normalizedVector.getY() * sin_angle + normalizedVector.getX() * normalizedVector.getZ() * (1 - cos_angle)),
                          this.getX() * (normalizedVector.getZ() * sin_angle + normalizedVector.getX() * normalizedVector.getY() * (1 - cos_angle))
                                  + this.getY() * (cos_angle + pow(normalizedVector.getY(), 2) * (1 - cos_angle))
                                  + this.getZ() * (-normalizedVector.getX() * sin_angle + normalizedVector.getY() * normalizedVector.getZ() * (1 - cos_angle)),
                          this.getX() * (-normalizedVector.getY() * sin_angle + normalizedVector.getX() * normalizedVector.getZ() * (1 - cos_angle))
                                  + this.getY() * (normalizedVector.getX() * sin_angle + normalizedVector.getY() * normalizedVector.getZ() * (1 - cos_angle))
                                  + this.getZ() * (cos_angle + pow(normalizedVector.getZ(), 2) * (1 - cos_angle)));
    }

}
