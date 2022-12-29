package geometries;

import org.junit.jupiter.api.Test;
import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class TriangleTest {

    Point p1 = new Point(0, 0, 0);
    Point p2 = new Point(5, 0, 0);
    Point p3 = new Point(0, 4, 0);
    Triangle triangle = new Triangle(p1, p2, p3);

    Point p4 = new Point(1, 1, 0);  // A point inside the area of the triangle

    Ray ray1 = new Ray(new Point(0, 0, -3), new Vector(1, 1, 1));
    Ray ray2 = new Ray(new Point(1, 1, -3), new Vector(-4, -4, 7));
    Ray ray3 = new Ray(new Point(0, 0, -3), new Vector(1, 1, 8));
    Ray ray4 = new Ray(new Point(0, 1, -1), new Vector(0, 0, 1));
    Ray ray5 = new Ray(new Point(0, 0, -1), new Vector(0, 0, 1));
    Ray ray6 = new Ray(new Point(0, 0, -1), new Vector(10, -1, 1));
    Ray ray7 = new Ray(new Point(-1, -1, 0), new Vector(1, 1, 0));
    Ray ray8 = new Ray(new Point(1, 1, 0), new Vector(1, 1, 1));

    @Test
    void testIsInside() {
        assertTrue(triangle.isInside(p4));
        assertFalse(triangle.isInside(new Point(3,3,0)));
        assertFalse(triangle.isInside(p1));
        assertFalse(triangle.isInside(new Point(1,0,0)));
    }

    @Test
    void testGetNormal() {
        assertEquals(triangle.plane.getNormal(), triangle.getNormal(p4), "getNormal() is incorrect");
    }

    @Test
    void testFindIntersections() {

        // ============ Equivalence Partitions Tests ==============

        // First Equivalence Partition - No intersection points
        // TC1: The ray does not intersect the triangle - the ray passes against the side
        assertNull(triangle.findIntersections(ray1), "findIntersections() is incorrect");
        // TC2: The ray does not intersect the triangle - the ray passes against the vertex
        assertNull(triangle.findIntersections(ray2), "findIntersections() is incorrect");

        // Second Equivalence Partition - One intersection point
        // TC3: The ray intersects the triangle at one point
        assertEquals(List.of(new Point(0.375, 0.375, 0)), triangle.findIntersections(ray3), "findIntersections() is incorrect");
        assertEquals(1, triangle.findIntersections(ray3).size(), "findIntersections() is incorrect");

        // =============== Boundary Values Tests ==================
        // TC4: The ray intersects the triangle's side
        assertNull(triangle.findIntersections(ray4), "findIntersections() is incorrect");
        // TC5: The ray intersects the triangle's vertex
        assertNull(triangle.findIntersections(ray5), "findIntersections() is incorrect");
        // TC6: The ray intersects the triangle's side continuation
        assertNull(triangle.findIntersections(ray6), "findIntersections() is incorrect");
        // TC7: The ray parallel to the triangle plane
        assertNull(triangle.findIntersections(ray7), "findIntersections() is incorrect");
        // TC8: The ray starts from the surface of the triangle
        assertNull(triangle.findIntersections(ray8), "findIntersections() is incorrect");
    }

}