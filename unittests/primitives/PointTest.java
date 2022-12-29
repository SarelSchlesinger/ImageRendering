package primitives;

import geometries.Triangle;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PointTest {

    Point p1 = new Point(1, 2, 3);
    Point p2 = new Point(10, 11, 12);
    Point p3 = new Point(9, 9, 9);
    Point p4 = new Point(5.5, 6.5, 7.5);


    @Test
    void testAdd() {
        assertEquals(p2, p1.add(p3), "add() is incorrect");
    }

    @Test
    void testSubtract() {
        assertEquals(new Vector(1, 2, 3), p2.subtract(p3), "subtract() is incorrect");
        assertThrows(IllegalArgumentException.class, () -> p1.subtract(p1));
    }

    @Test
    void testDistanceSquared() {
        assertEquals(243, p1.distanceSquared(p2), "distanceSquared() is incorrect");
    }

    @Test
    void testDistance() {
        assertEquals(Math.sqrt(243), p1.distance(p2), "distanceSquared() is incorrect");
    }

    @Test
    void testMidpoint() {
        assertEquals(p4, p1.midpoint(p2), "midpoint() is incorrect");
    }

    @Test
    void testIsBetween() {
        assertTrue(p4.isBetween(p1, p2));
        assertFalse(p3.isBetween(p1, p2));
        assertFalse(p2.isBetween(p1, p2));
        assertFalse(p1.isBetween(p1, p2));
        assertFalse(new Point(19, 20, 21).isBetween(p1, p2));
    }

    @Test
    void testIsOnEdge() {
        Triangle triangle = new Triangle(Point.ZERO, new Point(5, 0, 0), new Point(0, 4, 0));
        assertTrue(new Point(1, 0, 0).isOnEdge(triangle));
        assertFalse(new Point(6, 0, 0).isOnEdge(triangle));
        assertFalse(new Point(1, 1, 0).isOnEdge(triangle));
        assertTrue(Point.ZERO.isOnEdge(triangle));
    }
}