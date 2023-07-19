package geometries;

import org.junit.jupiter.api.Test;
import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class SphereTest {

    Point p0 = new Point(0, 0, 0);
    Sphere sphere = new Sphere(p0, 1);


    @Test
    void testGetNormal() {

        // point on the surface of the sphere
        Point p1 = new Point(1, 0, 0);

        // Check if the point is on the surface of the sphere
        assertEquals(sphere.getRadius(), p1.distance(sphere.getCenter()), "The point is not on the surface of the sphere");

        assertEquals(new Vector(1, 0, 0).normalize(), sphere.getNormal(p1), "getNormal() is incorrect");
    }

    @Test
    void testFindIntersections() {

        Ray ray1 = new Ray(new Point(2, 2, 4), new Vector(1, 1, 1));
        Ray ray2 = new Ray(new Point(0.2, 0.2, 0.4), new Vector(1, 1, 1));
        Ray ray3 = new Ray(new Point(1, 0, 4), new Vector(0, 0, -1));
        Ray ray4 = new Ray(new Point(1, 1, 2), new Vector(-1, -2, -4));
        Ray ray5 = new Ray(new Point(-0.044067201740144, 0.06949208724542, -0.996608715364796), new Vector(0.144067201740144, 0.03050791275458, 0.596608715364796));
        Ray ray6 = new Ray(new Point(0, 0, 1), new Vector(0.2, 0.2, 1));
        Ray ray7 = new Ray(new Point(-2, 0, 0), new Vector(1, 0, 0));
        Ray ray8 = new Ray(new Point(-1, 0, 0), new Vector(1, 0, 0));
        Ray ray9 = new Ray(new Point(-0.5, 0, 0), new Vector(1, 0, 0));
        Ray ray10 = new Ray(new Point(0, 0, 0), new Vector(1, 0, 0));
        Ray ray11 = new Ray(new Point(1, 0, 0), new Vector(1, 0, 0));
        Ray ray12 = new Ray(new Point(2, 0, 0), new Vector(1, 0, 0));
        Ray ray13 = new Ray(new Point(-1, -1, 1), new Vector(1, 1, 0));
        Ray ray14 = new Ray(new Point(0, 0, 1), new Vector(1, 1, 0));
        Ray ray15 = new Ray(new Point(1, 1, 1), new Vector(1, 1, 0));

        // ============ Equivalence Partitions Tests ==============

        // First Equivalence Partition - No intersection points
        // TC1: The ray origin is outside the sphere and the ray does not intersect the sphere
        assertNull(sphere.findIntersections(ray1), "findIntersections() is incorrect");

        // Second Equivalence Partition - One intersection point
        // TC2: The ray origin is inside the sphere and the ray intersects the sphere at one point
        assertEquals(List.of(new Point(0.502933583021169, 0.502933583021169, 0.702933583021169)),
                     sphere.findIntersections(ray2), "findIntersections() is incorrect");
        assertEquals(1, sphere.findIntersections(ray2).size(), "findIntersections() is incorrect");
        // TC3: The ray origin is outside the sphere and the ray tangent the sphere at one point
        assertEquals(List.of(new Point(1, 0, 0)), sphere.findIntersections(ray3), "findIntersections() is incorrect");
        assertEquals(1, sphere.findIntersections(ray3).size(), "findIntersections() is incorrect");

        // Third Equivalence Partition - Two intersection points
        // TC4: The ray origin is outside the sphere and the ray intersects the sphere at two points
        assertEquals(List.of(new Point(0.285714285714286, -0.428571428571429, -0.857142857142857),
                             new Point(0.666666666666667, 0.333333333333333, 0.666666666666667)),
                     sphere.findIntersections(ray4), "findIntersections() is incorrect");
        assertEquals(2, sphere.findIntersections(ray4).size(), "findIntersections() is incorrect");

        // =============== Boundary Values Tests ==================

        // **** Group1: Ray crosses the sphere (but not the center)
        // TC5: Ray starts at the sphere envelope and goes inside (1 point)
        assertEquals(List.of(new Point(0.412834560983999, 0.16624637237233, 0.895505314853122)),
                     sphere.findIntersections(ray5), "findIntersections() is incorrect");
        assertEquals(1, sphere.findIntersections(ray5).size(), "findIntersections() is incorrect");
        // TC6: Ray starts at sphere and goes outside (0 points)
        assertNull(sphere.findIntersections(ray6), "findIntersections() is incorrect");

        // **** Group2: Ray goes through the center
        // TC7: Ray starts before the sphere (2 points)
        assertEquals(List.of(new Point(1, 0, 0), new Point(-1, 0, 0)),
                     sphere.findIntersections(ray7), "findIntersections() is incorrect");
        assertEquals(2, sphere.findIntersections(ray7).size(), "findIntersections() is incorrect");
        // TC8: Ray starts at sphere and goes inside (1 point)
        assertEquals(List.of(new Point(1, 0, 0)),
                     sphere.findIntersections(ray8), "findIntersections() is incorrect");
        assertEquals(1, sphere.findIntersections(ray8).size(), "findIntersections() is incorrect");
        // TC9: Ray starts inside (1 point)
        assertEquals(List.of(new Point(1, 0, 0)),
                     sphere.findIntersections(ray9), "findIntersections() is incorrect");
        assertEquals(1, sphere.findIntersections(ray9).size(), "findIntersections() is incorrect");
        // TC10: Ray starts at the center (1 point)
        assertEquals(List.of(new Point(1, 0, 0)),
                     sphere.findIntersections(ray10), "findIntersections() is incorrect");
        assertEquals(1, sphere.findIntersections(ray10).size(), "findIntersections() is incorrect");
        // TC11: Ray starts at sphere and goes outside (0 points)
        assertNull(sphere.findIntersections(ray11), "findIntersections() is incorrect");
        // TC12: Ray starts after sphere (0 points)
        assertNull(sphere.findIntersections(ray12), "findIntersections() is incorrect");

        // **** Group3: The ray is tangent to the sphere (all tests 0 points)
        // TC13: Ray starts before the tangent point
        assertNull(sphere.findIntersections(ray13), "findIntersections() is incorrect");
        // TC14: Ray starts at the tangent point
        assertNull(sphere.findIntersections(ray14), "findIntersections() is incorrect");
        // TC15: Ray starts after the tangent point
        assertNull(sphere.findIntersections(ray15), "findIntersections() is incorrect");

    }
}