package primitives;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PointTest {

    Point p1 = new Point(1, 2, 3);
    Point p2 = new Point(10, 11, 12);
    Point p3 = new Point(9, 9, 9);


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
        assertEquals(new Point(5.5, 6.5, 7.5), p1.midpoint(p2), "midpoint() is incorrect");
    }

}