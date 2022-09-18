package primitives;

import org.junit.jupiter.api.Test;

import java.util.LinkedList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class RayTest {

    Ray ray = new Ray(new Point(1,1,1), new Vector(0,0,1));

    @Test
    void testGetPoint() {
        double t = 1;
        assertEquals(new Point(1,1,2), ray.getPoint(t), "getPoint() is incorrect");
    }

    @Test
    void testFindClosestPoint() {

        Point p1 = new Point(1, 1, 2);
        Point p2 = new Point(1, 1, 3);
        Point p3 = new Point(1, 1, 4);
        //List<Point> points1 = new LinkedList<>();

        // ============ Equivalence Partitions Tests ==============
        // TC1: The closest point is in the middle of the list
        assertEquals(p1, ray.findClosestPoint(List.of(p2, p1, p3)), "findClosestPoint() is incorrect");

        // =============== Boundary Values Tests ==================
        // TC2: Empty list of points
        assertNull(ray.findClosestPoint(List.of()), "findClosestPoint() is incorrect");
        // TC3: The closest point is the first point in the list
        assertEquals(p1, ray.findClosestPoint(List.of(p1, p2, p3)), "findClosestPoint() is incorrect");
        // TC4: The closest point is the last point in the list
        assertEquals(p1, ray.findClosestPoint(List.of(p2, p3, p1)), "findClosestPoint() is incorrect");

    }
}