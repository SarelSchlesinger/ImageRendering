package primitives;

import static java.lang.Math.sqrt;
import static java.lang.Math.abs;
import static java.lang.Math.pow;
import static java.lang.Math.sin;
import static java.lang.Math.cos;
import static java.lang.Math.toRadians;

public class Vector extends Point {

    public Vector(double x, double y, double z) {
        super(x, y, z);

        if (this.equals(Double3.ZERO)) {
            throw new IllegalArgumentException("Cannot get vector of zero");
        }
    }

    public Vector(Double3 xyz) {
        super(xyz);

        if (this.equals(Double3.ZERO)) {
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

    /**
     * throw an exception if vector == (0,0,0)
     */
    public void zeroVector() {
        if (Util.isZero(this.dotProduct(new Vector(1, 1, 1)))) {
            throw new IllegalArgumentException("Cannot get vector of zero");
        }
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
        double cos_angle = cos(toRadians(angle));
        double sin_angle = sin(toRadians(angle));
        return new Vector(this.getX() * (cos_angle + pow(axis.getX(), 2) * (1 - cos_angle))
                                  + this.getY() * (axis.getX() * axis.getY() * (1 - cos_angle) - axis.getZ() * sin_angle)
                                  + this.getZ() * (axis.getY() * sin_angle + axis.getX() * axis.getZ() * (1 - cos_angle)),
                          this.getX() * (axis.getZ() * sin_angle + axis.getX() * axis.getY() * (1 - cos_angle))
                                  + this.getY() * (cos_angle + pow(axis.getY(), 2) * (1 - cos_angle))
                                  + this.getZ() * (-axis.getX() * sin_angle + axis.getY() * axis.getZ() * (1 - cos_angle)),
                          this.getX() * (-axis.getY() * sin_angle + axis.getX() * axis.getZ() * (1 - cos_angle))
                                  + this.getY() * (axis.getX() * sin_angle + axis.getY() * axis.getZ() * (1 - cos_angle))
                                  + this.getZ() * (cos_angle + pow(axis.getZ(), 2) * (1 - cos_angle)));
    }


}
