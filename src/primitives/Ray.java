package primitives;

import java.util.Objects;

public class Ray {

    final private Point p0;
    final private Vector direction;

    public Ray(Point p0, Vector direction) {

        if (p0 == null) {
            throw new IllegalArgumentException("p0 cannot be null");
        }

        if (direction == null) {
            throw new IllegalArgumentException("direction cannot be null");
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
