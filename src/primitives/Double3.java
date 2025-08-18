package primitives;

import java.util.List;

import static primitives.Util.*;

/**
 * This class will serve all primitive classes based on three numbers
 *
 * @author Dan Zilberstein
 */
public class Double3 {
    /**
     * Zero triad (0,0,0)
     */
    public static final Double3 ZERO = new Double3(0, 0, 0);
    /**
     * Ones triad (1,1,1)
     */
    public static final Double3 ONE = new Double3(1, 1, 1);
    final double d1;
    final double d2;
    final double d3;

    /**
     * Constructor to initialize Double3 based object with its three number values
     *
     * @param d1 first number value
     * @param d2 second number value
     * @param d3 third number value
     */
    public Double3(double d1, double d2, double d3) {
        this.d1 = d1;
        this.d2 = d2;
        this.d3 = d3;
    }

    /**
     * Constructor to initialize Double3 based object the same number values
     *
     * @param value number value for all 3 numbers
     */
    public Double3(double value) {
        this.d1 = value;
        this.d2 = value;
        this.d3 = value;
    }

    public static Double3 avg(List<Double3> double3List) {
        if (double3List.size() == 1) return double3List.get(0);
        Double3 double3 = Double3.ZERO;
        for (Double3 doub3 : double3List) {
            double3 = double3.add(doub3);
        }
        return double3.reduce(double3List.size());
    }

    public double getD1() {
        return this.d1;
    }

    public double getD2() {
        return this.d2;
    }

    public double getD3() {
        return this.d3;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (obj instanceof Double3 other)
            return isZero(this.getD1() - other.getD1()) &&
                   isZero(this.getD2() - other.getD2()) &&
                   isZero(this.getD3() - other.getD3());
        return false;
    }

    @Override
    public int hashCode() {
        return (int) Math.round(d1 + d2 + d3);
    }

    @Override
    public String toString() {
        return "(" + d1 + "," + d2 + "," + d3 + ")";
    }

    /**
     * Sum two floating point triads into a new triad where each couple of numbers
     * is summarized
     *
     * @param rhs right handle side operand for addition
     * @return result of add
     */
    public Double3 add(Double3 rhs) {
        return new Double3(this.getD1() + rhs.getD1(),
                           this.getD2() + rhs.getD2(),
                           this.getD3() + rhs.getD3());
    }

    /**
     * Subtract two floating point triads into a new triad where each couple of
     * numbers is subtracted
     *
     * @param rhs right handle side operand for addition
     * @return result of add
     */
    public Double3 subtract(Double3 rhs) {
        return new Double3(this.getD1() - rhs.getD1(),
                           this.getD2() - rhs.getD2(),
                           this.getD3() - rhs.getD3());
    }

    /**
     * Scale (multiply) floating point triad by a number into a new triad where each
     * number is multiplied by the number
     *
     * @param rhs right handle side operand for scaling
     * @return result of scale
     */
    public Double3 scale(double rhs) {
        return new Double3(this.getD1() * rhs,
                           this.getD2() * rhs,
                           this.getD3() * rhs);
    }

    /**
     * Reduce (divide) floating point triad by a number into a new triad where each
     * number is divided by the number
     *
     * @param rhs right handle side operand for reducing
     * @return result of scale
     */
    public Double3 reduce(double rhs) {
        if (rhs == 0) throw new IllegalArgumentException("The argument cannot be zero");
        return new Double3(this.getD1() / rhs,
                           this.getD2() / rhs,
                           this.getD3() / rhs);
    }

    /**
     * Product two floating point triads into a new triad where each couple of
     * numbers is multiplied
     *
     * @param rhs right handle side operand for product
     * @return result of product
     */
    public Double3 product(Double3 rhs) {
        return new Double3(this.getD1() * rhs.getD1(),
                           this.getD2() * rhs.getD2(),
                           this.getD3() * rhs.getD3());
    }

    /**
     * Checks whether all the numbers are lower than a test number
     *
     * @param k the test number
     * @return true if all the numbers are less than k, false otherwise
     */
    public boolean lowerThan(double k) {
        return d1 < k && d2 < k && d3 < k;
    }
}
