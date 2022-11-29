package primitives;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PointTest {

    Point p1 = new Point(1,2,3);
    Point p2 = new Point(10,11,12);
    Vector v = new Vector(1,2,3);


    @Test
    void testAdd() {

        assertEquals(p1.add(new Point(9,9,9)),p2,"add() is incorrect");
    }

    @Test
    void testSubtract() {
        assertEquals(v, p2.subtract(new Point(9, 9, 9)), "subtract() is incorrect");
        assertThrows(IllegalArgumentException.class, () -> p1.subtract(p1).zeroVector(),
                "vector (0,0,0) does not throw an exception");
    }

    @Test
    void testDistanceSquared() {
        assertEquals(243,p1.distanceSquared(p2),"distanceSquared() is incorrect");
    }

    @Test
    void testDistance() {
        assertEquals(Math.sqrt(243),p1.distance(p2),"distanceSquared() is incorrect");
    }

    @Test
    void testMidpoint() {
        assertEquals(new Point(5.5,6.5,7.5),p1.midpoint(p2),"midpoint() is incorrect");
    }

}