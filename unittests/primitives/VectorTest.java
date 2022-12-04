package primitives;

import org.junit.jupiter.api.Test;

import static java.lang.Math.sqrt;
import static org.junit.jupiter.api.Assertions.*;

class VectorTest {

    Vector v1 = new Vector(1, 2, 3);
    Vector v2 = new Vector(2, 4, 6);

    @Test
    void testAdd() {
        assertEquals(v2, v1.add(v1), "add() is incorrect");
        assertThrows(IllegalArgumentException.class, () -> v1.add(new Vector(-1, -2, -3)).zeroVector(),
                     "vector (0,0,0) does not throw an exception");
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
        assertEquals(new Vector(0, 0, 0), v1.crossProduct(v2), "crossProduct() is incorrect");
        assertThrows(IllegalArgumentException.class, () -> v1.crossProduct(v2).zeroVector(), "vector (0,0,0) does not throw an exception");
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
        assertEquals(new Vector(1 / v1.length(), 2 / v1.length(), 3 / v1.length()), v1.normalize(), "normalize() is incorrect");
    }

    @Test
    void testRotationAroundZAxis() {
        assertEquals(new Vector(-2, 2, 5), new Vector(2, 2, 5).rotationAroundZAxis(90), "rotationAroundZAxis() is incorrect");
    }

    @Test
    void testRotationAroundArbitraryAxis() {

        // TC1: Rotation around x-axis
        assertEquals(new Vector(0, 0, 5), new Vector(0, 0, -5).rotationAroundArbitraryAxis(new Vector(1, 0, 0), 180), "rotationAroundArbitraryAxis() is incorrect");
        assertEquals(new Vector(0, 0, 0), new Vector(0, 0, 0).rotationAroundArbitraryAxis(new Vector(1, 0, 0), 180), "rotationAroundArbitraryAxis() is incorrect");
        // TC2: Rotation around y-axis
        assertEquals(new Vector(0, 0, 1), new Vector(0, 0, -1).rotationAroundArbitraryAxis(new Vector(0, 1, 0), 180), "rotationAroundArbitraryAxis() is incorrect");
        assertEquals(new Vector(0, 0, 0), new Vector(0, 0, 0).rotationAroundArbitraryAxis(new Vector(0, 1, 0), 180), "rotationAroundArbitraryAxis() is incorrect");
        // TC3: Rotation around z-axis
        assertEquals(new Vector(-2, 2, 5), new Vector(2, 2, 5).rotationAroundArbitraryAxis(new Vector(0, 0, 1), 90), "rotationAroundArbitraryAxis() is incorrect");
        assertEquals(new Vector(0, 0, 0), new Vector(0, 0, 0).rotationAroundArbitraryAxis(new Vector(0, 0, 1), 180), "rotationAroundArbitraryAxis() is incorrect");
        // TC4: Rotation around arbitrary axis
        assertEquals(new Vector(4, 4, 1), new Vector(2, 2, 5).rotationAroundArbitraryAxis(new Vector(1, 1, 1).normalize(), 180), "rotationAroundArbitraryAxis() is incorrect");
        assertEquals(new Vector(5, 2, 2), new Vector(2, 2, 5).rotationAroundArbitraryAxis(new Vector(1, 1, 1).normalize(), 120), "rotationAroundArbitraryAxis() is incorrect");
    }
}