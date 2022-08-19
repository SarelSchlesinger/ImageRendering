package primitives;

import java.util.Objects;

public class Ray {

    final private Point p0;
    final private Vector direction;

    public Ray(Point p0, Vector direction) {

        if (!(p0 instanceof Point)) {
            throw new IllegalArgumentException("The p0 must be of Point type");
        }

        if (!(direction instanceof Vector)) {
            throw new IllegalArgumentException("The direction must be of Vector type");
        }

        this.p0 = p0;
        this.direction = direction.normalize();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Ray ray = (Ray) o;
        return p0.equals(ray.p0) && direction.equals(ray.direction);
    }

    @Override
    public String toString() {
        return "Ray{" + "p0=" + p0 + ", direction=" + direction + '}';
    }

    public Point getP0() {
        return p0;
    }

    public Vector getDirection() {
        return direction;
    }
}
