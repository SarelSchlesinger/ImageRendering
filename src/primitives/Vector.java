package primitives;

import static java.lang.Math.sqrt;
import static java.lang.Math.cos;
import static java.lang.Math.sin;
import static java.lang.Math.pow;
import static java.lang.Math.toRadians;
import static primitives.Util.alignZero;
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

    public Vector reverse() {
        return new Vector(-this.getX(), -this.getY(), -this.getZ());
    }

    public Boolean isParallel(Vector vector) {
        return pow(this.dotProduct(vector), 2) == this.lengthSquared() * vector.lengthSquared();
    }

    /**
     * method to find an orthogonal vector to a given vector
     */
    public Vector findOrthogonalVector() {
        if (this.getX() > this.getZ()) {
            if (isZero(this.getX())) {
                return new Vector(1, 0, 0);
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
     * this calculation is based on <a href="https://mathworld.wolfram.com/RodriguesRotationFormula.html">...</a>
     *
     * @param normalizedVector - the axis of the rotation
     * @param angle            - the angle of rotation
     * @return a new rotated vector
     */
    public Vector rotationAroundArbitraryAxis(Vector normalizedVector, double angle) {
        double cos = alignZero(cos(toRadians(angle)));
        double sin = alignZero(sin(toRadians(angle)));
        return new Vector(this.getX() * (cos + pow(normalizedVector.getX(), 2) * (1 - cos))
                                  + this.getY() * (normalizedVector.getX() * normalizedVector.getY() * (1 - cos) - normalizedVector.getZ() * sin)
                                  + this.getZ() * (normalizedVector.getY() * sin + normalizedVector.getX() * normalizedVector.getZ() * (1 - cos)),
                          this.getX() * (normalizedVector.getZ() * sin + normalizedVector.getX() * normalizedVector.getY() * (1 - cos))
                                  + this.getY() * (cos + pow(normalizedVector.getY(), 2) * (1 - cos))
                                  + this.getZ() * (-normalizedVector.getX() * sin + normalizedVector.getY() * normalizedVector.getZ() * (1 - cos)),
                          this.getX() * (-normalizedVector.getY() * sin + normalizedVector.getX() * normalizedVector.getZ() * (1 - cos))
                                  + this.getY() * (normalizedVector.getX() * sin + normalizedVector.getY() * normalizedVector.getZ() * (1 - cos))
                                  + this.getZ() * (cos + pow(normalizedVector.getZ(), 2) * (1 - cos)));
    }

    /**
     * method to select the appropriate rotation method for this particular rotation axis
     *
     * @param axis  - the axis of the rotation
     * @param angle - the angle of rotation
     */
    public Vector rotateVector(Vector axis, double angle) {
        Vector normalizedAxisVector = axis.normalize();
        if (isZero(normalizedAxisVector.getY()) && isZero(normalizedAxisVector.getZ())) {
            return this.rotationAroundXAxis(angle);
        } else if (isZero(normalizedAxisVector.getX()) && isZero(normalizedAxisVector.getZ())) {
            return this.rotationAroundYAxis(angle);
        } else if (isZero(normalizedAxisVector.getX()) && isZero(normalizedAxisVector.getY())) {
            return this.rotationAroundZAxis(angle);
        } else {
            return this.rotationAroundArbitraryAxis(normalizedAxisVector, angle);
        }
    }

}
