package primitives;

import org.junit.jupiter.api.Test;

import static java.lang.Math.sqrt;
import static org.junit.jupiter.api.Assertions.*;

class VectorTest {

    Vector v1 = new Vector(1, 2, 3);
    Vector v2 = new Vector(2, 4, 6);
    Vector v3 = new Vector(1, 0, 0);
    Vector v4 = new Vector(2, 2, 5);
    Vector v5 = new Vector(0, 1, 0);
    Vector v6 = new Vector(0, 0, 1);
    Vector v7 = new Vector(1, 1, 1);

    @Test
    void testAdd() {
        assertEquals(v2, v1.add(v1), "add() is incorrect");
        assertThrows(IllegalArgumentException.class,
                     () -> v1.add(v1.reverse()), "vector (0,0,0) does not throw an exception");
        assertThrows(IllegalArgumentException.class,
                     () -> v1.add(new Vector(new Double3(-v1.getX(), -v1.getY(), -v1.getZ()))), "vector (0,0,0) does not throw an exception");
    }

    @Test
    void testSubtract() {
        assertEquals(v1, v2.subtract(v1), "subtract() is incorrect");
        assertThrows(IllegalArgumentException.class, () -> v1.subtract(v1), "vector (0,0,0) does not throw an exception");
    }

    @Test
    void testScale() {
        assertEquals(v2, v1.scale(2), "scale() is incorrect");
        assertThrows(IllegalArgumentException.class, () -> v1.scale(0), "vector (0,0,0) does not throw an exception");
    }

    @Test
    void testDotProduct() {
        assertEquals(28, v1.dotProduct(v2), "dotProduct() is incorrect");
    }

    @Test
    void testCrossProduct() {
        assertEquals(new Vector(-1, 2, -1), v1.crossProduct(new Vector(Double3.ONE)), "crossProduct() is incorrect");
        assertThrows(IllegalArgumentException.class, () -> v1.crossProduct(v2));
        assertEquals(new Vector(-1, 0, 0), new Vector(0, -1, 0).crossProduct(v6), "crossProduct() is incorrect");
    }

    @Test
    void testLengthSquared() {
        assertEquals(14, v1.lengthSquared(), "lengthSquared() is incorrect");
    }

    @Test
    void testLength() {
        assertEquals(sqrt(14), v1.length(), "length() is incorrect");
    }

    @Test
    void testNormalize() {
        assertEquals(new Vector(v1.getX() / v1.length(), v1.getY() / v1.length(), v1.getZ() / v1.length()), v1.normalize(), "normalize() is incorrect");
    }

    @Test
    void testReverse() {
        assertEquals(new Vector(-v1.getX(), -v1.getY(), -v1.getZ()), v1.reverse(), "reverse() is incorrect");
    }

    @Test
    void testIsParallel() {
        assertFalse(new Vector(12, 6, 9).isParallel(new Vector(-12, 6, -9)), "isParallel() is incorrect");
        assertTrue(v1.isParallel(v1.reverse()), "isParallel() is incorrect");
        assertTrue(v1.isParallel(v2.reverse()), "isParallel() is incorrect");
    }

    @Test
    void testFindOrthogonalVector() {
        assertEquals(new Vector(0, -3, 2).normalize(), v1.findOrthogonalVector(), "findOrthogonalVector() is incorrect");
        assertEquals(new Vector(0, -6, 4).normalize(), v2.findOrthogonalVector(), "findOrthogonalVector() is incorrect");
        assertDoesNotThrow(() -> new Vector(0, 0, -1).findOrthogonalVector());
        assertEquals(v3, new Vector(0, 3, -5).findOrthogonalVector(), "findOrthogonalVector() is incorrect");
        assertDoesNotThrow(() -> new Vector(0, 5, -1).findOrthogonalVector());
    }

    @Test
    void testRotationAroundXAxis() {
        assertEquals(new Vector(2, -5, 2), v4.rotationAroundXAxis(90), "rotationAroundXAxis() is incorrect");
    }

    @Test
    void testRotationAroundYAxis() {
        assertEquals(new Vector(5, 2, -2), v4.rotationAroundYAxis(90), "rotationAroundYAxis() is incorrect");
        assertEquals(new Vector(-1, -5, 0), new Vector(1, -5, 0).rotationAroundYAxis(180));
    }

    @Test
    void testRotationAroundZAxis() {
        assertEquals(new Vector(-2, 2, 5), v4.rotationAroundZAxis(90), "rotationAroundZAxis() is incorrect");
        assertEquals(new Vector(0, -1, 0), new Vector(-1, 0, 0).rotationAroundZAxis(90), "rotationAroundZAxis() is incorrect");
        assertEquals(new Vector(5, 0, 0), new Vector(0, -5, 0).rotationAroundZAxis(90), "rotationAroundZAxis() is incorrect");
    }

    @Test
    void testRotationAroundArbitraryAxis() {
        // TC1: Rotation around x-axis
        assertEquals(new Vector(1, -2, -3), v1.rotationAroundArbitraryAxis(v3, 180), "rotationAroundArbitraryAxis() is incorrect");
        assertEquals(new Vector(1, -3, 2), v1.rotationAroundArbitraryAxis(v3, 90), "rotationAroundArbitraryAxis() is incorrect");
        // TC2: Rotation around y-axis
        assertEquals(new Vector(-1, 2, -3), v1.rotationAroundArbitraryAxis(v5, 180), "rotationAroundArbitraryAxis() is incorrect");
        assertEquals(new Vector(3, 2, -1), v1.rotationAroundArbitraryAxis(v5, 90), "rotationAroundArbitraryAxis() is incorrect");
        // TC3: Rotation around z-axis
        assertEquals(new Vector(-2, -2, 5), v4.rotationAroundArbitraryAxis(v6, 180), "rotationAroundArbitraryAxis() is incorrect");
        assertEquals(new Vector(-2, 2, 5), v4.rotationAroundArbitraryAxis(v6, 90), "rotationAroundArbitraryAxis() is incorrect");
        // TC4: Rotation around arbitrary axis
        assertEquals(new Vector(4, 4, 1), v4.rotationAroundArbitraryAxis(v7.normalize(), 180), "rotationAroundArbitraryAxis() is incorrect");
        assertEquals(new Vector(5, 2, 2), v4.rotationAroundArbitraryAxis(v7.normalize(), 120), "rotationAroundArbitraryAxis() is incorrect");
    }

    @Test
    void testRotateVector() {
        // TC1: Rotation around x-axis
        assertEquals(v1.rotationAroundXAxis(180), v1.rotateVector(v3, 180), "rotateVector() is incorrect");
        assertEquals(v1.rotationAroundXAxis(90), v1.rotateVector(v3, 90), "rotateVector() is incorrect");
        // TC2: Rotation around y-axis
        assertEquals(v1.rotationAroundYAxis(180), v1.rotateVector(v5, 180), "rotateVector() is incorrect");
        assertEquals(v1.rotationAroundYAxis(90), v1.rotateVector(v5, 90), "rotateVector() is incorrect");
        // TC3: Rotation around z-axis
        assertEquals(v4.rotationAroundZAxis(180), v4.rotateVector(v6, 180), "rotateVector() is incorrect");
        assertEquals(v4.rotationAroundZAxis(90), v4.rotateVector(v6, 90), "rotateVector() is incorrect");
        // TC4: Rotation around arbitrary axis
        assertEquals(v4.rotationAroundArbitraryAxis(v7.normalize(), 180), v4.rotateVector(v7.normalize(), 180), "rotateVector() is incorrect");
        assertEquals(v4.rotationAroundArbitraryAxis(v7.normalize(), 120), v4.rotateVector(v7.normalize(), 120), "rotateVector() is incorrect");
    }

}