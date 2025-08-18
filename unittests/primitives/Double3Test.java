package primitives;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class Double3Test {

    Double3 double3a = new Double3(2.9, 2, 3);
    Double3 double3b = new Double3(0, -2, 10);
    Double3 double3c = new Double3(9.1, 4.5, 2);

    @Test
    void testAdd() {
        assertEquals(new Double3(2.9, 0, 13), double3a.add(double3b), "add() is incorrect");
        assertEquals(Double3.ZERO, Double3.ZERO.add(Double3.ZERO), "add() is incorrect");
        assertEquals(Double3.ONE, Double3.ZERO.add(Double3.ONE), "add() is incorrect");
    }

    @Test
    void testSubtract() {
        assertEquals(new Double3(2.9, 4, -7), double3a.subtract(double3b), "subtract() is incorrect");
        assertEquals(Double3.ZERO, double3a.subtract(double3a), "subtract() is incorrect");
    }

    @Test
    void testScale() {
        assertEquals(new Double3(0, -4, 20), double3b.scale(2), "scale() is incorrect");
        assertEquals(Double3.ZERO, double3a.scale(0), "scale() is incorrect");
    }

    @Test
    void testReduce() {
        assertEquals(new Double3(0, -1, 5), double3b.reduce(2), "reduce() is incorrect");
        assertEquals(double3b, double3b.reduce(1), "reduce() is incorrect");
        assertThrows(IllegalArgumentException.class, () -> double3a.reduce(0), "reduce() is incorrect");
    }

    @Test
    void testProduct() {
        assertEquals(new Double3(0, -4, 30), double3a.product(double3b), "product() is incorrect");
        assertEquals(Double3.ZERO, double3a.product(Double3.ZERO), "product() is incorrect");
        assertEquals(double3a, double3a.product(Double3.ONE), "product() is incorrect");
    }

    @Test
    void testAvg() {
        assertEquals(new Double3(4, 1.5, 5), Double3.avg(List.of(double3a, double3b, double3c)), "avg() is incorrect");
        assertEquals(double3a, Double3.avg(List.of(double3a)), "avg() is incorrect");
        assertThrows(IllegalArgumentException.class, () -> Double3.avg(new ArrayList<>()), "avg() is incorrect");
    }
}