package primitives;

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
     *  throw an exception if vector == (0,0,0)
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
        return  this.xyz.d1 * vector.xyz.d1 +
                this.xyz.d2 * vector.xyz.d2 +
                this.xyz.d3 * vector.xyz.d3;
    }

    public Vector crossProduct(Vector vector) {
        return  new Vector(this.xyz.d2 * vector.xyz.d3 - this.xyz.d3 * vector.xyz.d2,
                           this.xyz.d1 * vector.xyz.d3 - this.xyz.d3 * vector.xyz.d1,
                           this.xyz.d1 * vector.xyz.d2 - this.xyz.d2 * vector.xyz.d1);
    }

    public double lengthSquared() {
        return  this.xyz.d1 * this.xyz.d1 +
                this.xyz.d2 * this.xyz.d2 +
                this.xyz.d3 * this.xyz.d3 ;
    }

    public double length() {
        return Math.sqrt(this.lengthSquared());
    }

    public Vector normalize() {

        return new Vector(this.xyz.reduce(this.length()));
    }

}
