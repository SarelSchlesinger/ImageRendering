package geometries;

import primitives.Ray;

public class Cylinder extends Tube {

    final double height;

    public Cylinder(Ray axisRay, double radius, double height) {
        super(axisRay, radius);

        if (height <= 0) {
            throw new IllegalArgumentException("The height must be greater than 0");
        }

        this.height = height;
    }

    public double getHeight() {
        return this.height;
    }

}
