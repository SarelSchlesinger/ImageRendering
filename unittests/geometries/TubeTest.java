package geometries;

import org.junit.jupiter.api.Test;
import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class TubeTest {

    Point p0 = new Point(0, 0, 0);
    Vector direction = new Vector(0, 0, 1);
    Ray ray = new Ray(p0, direction);
    Tube tube = new Tube(ray, 1);

    @Test
    void testGetNormal() {
        assertEquals(new Vector(0, 1, 0), tube.getNormal(new Point(0, 1, 1)), "getNormal() is incorrect");
    }

    @Test
    void testFindIntersections() {

        Ray ray1 = new Ray(new Point(-3, -3, -3), new Vector(-2, 2.6, 3));
        Ray ray2 = new Ray(new Point(1, 1, 0), new Vector(-1, 0, 0));
        Ray ray3 = new Ray(new Point(0.5, 0.5, 0), new Vector(0.5, 0.5, 1));
        Ray ray4 = new Ray(new Point(-3, -3, -3), new Vector(2.3, 2.6, 3));
        Ray ray5 = new Ray(new Point(-1, 0, -2), new Vector(1, 1, 2));
        Ray ray6 = new Ray(new Point(-1, 0, -2), new Vector(-2, -1, 2));
        Ray ray7 = new Ray(new Point(-2, -2, -2), new Vector(2, 2, 2));
        Ray ray8 = new Ray(new Point(1, 0, 0), new Vector(-1, 0, 0));
        Ray ray9 = new Ray(new Point(0.5, 0, 0), new Vector(-1, 0, 0));
        Ray ray10 = new Ray(new Point(0, 0, 3), new Vector(1, 1, 1));
        Ray ray11 = new Ray(new Point(1, 0, 0), new Vector(1, 0, 0));
        Ray ray12 = new Ray(new Point(2, 0, 0), new Vector(1, 0, 0));
        Ray ray13 = new Ray(new Point(1, 1, 0), new Vector(0, -1, 0));
        Ray ray14 = new Ray(new Point(1, 0, 0), new Vector(0, 1, 0));
        Ray ray15 = new Ray(new Point(1, 1, 0), new Vector(0, 1, 0));
        Ray ray16 = new Ray(new Point(1, 0, 0), new Vector(0, 0, 1));
        Ray ray17 = new Ray(new Point(2, 0, 0), new Vector(0, 0, 1));
        Ray ray18 = new Ray(new Point(0.5, 0, 0), new Vector(0, 0, 1));
        Ray ray19 = new Ray(new Point(0, 0, 1), new Vector(0, 0, 1));
        Ray ray20 = new Ray(new Point(0, 0, -1), new Vector(0, 0, 1));

        // ============ Equivalence Partitions Tests ==============

        // First Equivalence Partition - No intersection points
        // TC1: The ray origin is outside the tube and the ray does not intersect the tube
        assertNull(tube.findIntersections(ray1), "findIntersections() is incorrect");
        // TC2: The ray origin is outside the tube and the ray tangent the tube
        assertNull(tube.findIntersections(ray2), "findIntersections() is incorrect");

        // Second Equivalence Partition - One intersection point
        // TC3: The ray origin is inside the tube and the ray intersects the tube at one point
        assertEquals(List.of(new Point(0.707106781186548, 0.707106781186548, 0.414213562373095)),
                     tube.findIntersections(ray3), "findIntersections() is incorrect");
        assertEquals(1, tube.findIntersections(ray3).size(), "findIntersections() is incorrect");

        // Third Equivalence Partition - Two intersection points
        // TC4: The ray origin is outside the tube and the ray intersects the tube at two points
        assertEquals(List.of(new Point(-0.834108308925352, -0.551600697046049, -0.17492388120698),
                             new Point(0.445726566186761, 0.895169161776339, 1.494425955895776)),
                     tube.findIntersections(ray4), "findIntersections() is incorrect");
        assertEquals(2, tube.findIntersections(ray4).size(), "findIntersections() is incorrect");


        // =============== Boundary Values Tests ==================

        // **** Group1: Ray crosses the tube (but not the center)
        // TC5: Ray starts at the tube envelope and goes inside (1 point)
        assertEquals(List.of(new Point(0, 1, 0)),
                     tube.findIntersections(ray5), "findIntersections() is incorrect");
        assertEquals(1, tube.findIntersections(ray5).size(), "findIntersections() is incorrect");
        // TC6: Ray starts at the tube envelope and goes outside (0 points)
        assertNull(tube.findIntersections(ray6), "findIntersections() is incorrect");

        // **** Group2: Ray passes through the central axis of the tube
        // TC7: Ray starts before the tube (2 points)
        assertEquals(List.of(new Point(-0.707106781186548, -0.707106781186548, -0.707106781186548),
                             new Point(0.707106781186548, 0.707106781186548, 0.707106781186548)),
                     tube.findIntersections(ray7), "findIntersections() is incorrect");
        assertEquals(2, tube.findIntersections(ray7).size(), "findIntersections() is incorrect");
        // TC8: Ray starts at the tube envelope and goes inside (1 point)
        assertEquals(List.of(new Point(-1, 0, 0)),
                     tube.findIntersections(ray8), "findIntersections() is incorrect");
        assertEquals(1, tube.findIntersections(ray8).size(), "findIntersections() is incorrect");
        // TC9: Ray starts inside the tube (1 point)
        assertEquals(List.of(new Point(-1, 0, 0)),
                     tube.findIntersections(ray9), "findIntersections() is incorrect");
        assertEquals(1, tube.findIntersections(ray9).size(), "findIntersections() is incorrect");
        // TC10: Ray starts at the central axis of the tube (1 point)
        assertEquals(List.of(new Point(0.707106781186548, 0.707106781186548, 3.707106781186548)),
                     tube.findIntersections(ray10), "findIntersections() is incorrect");
        assertEquals(1, tube.findIntersections(ray10).size(), "findIntersections() is incorrect");
        // TC11: Ray starts at the tube envelope and goes outside (0 points)
        assertNull(tube.findIntersections(ray11), "findIntersections() is incorrect");
        // TC12: Ray starts after tube (0 points)
        assertNull(tube.findIntersections(ray12), "findIntersections() is incorrect");

        // **** Group3: The ray is tangent to the tube (all tests 0 points)
        // TC13: Ray starts before the tangent point
        assertNull(tube.findIntersections(ray13), "findIntersections() is incorrect");
        // TC14: Ray starts at the tangent point
        assertNull(tube.findIntersections(ray14), "findIntersections() is incorrect");
        // TC15: Ray starts after the tangent point
        assertNull(tube.findIntersections(ray15), "findIntersections() is incorrect");

        // **** Group4: The ray is parallel to the central axis of the tube (all tests 0 points)
        // TC16: The ray origin is on the tube's envelope and the ray is contained in the tube's envelope
        assertNull(tube.findIntersections(ray16), "findIntersections() is incorrect");
        // TC17: The ray origin is outside the tube
        assertNull(tube.findIntersections(ray17), "findIntersections() is incorrect");
        // TC18: The ray origin is inside the tube
        assertNull(tube.findIntersections(ray18), "findIntersections() is incorrect");
        // TC19: Ray is contained in the central axis of the tube
        assertNull(tube.findIntersections(ray19), "findIntersections() is incorrect");
        // TC20: Tube's central axis is contained in the ray
        assertNull(tube.findIntersections(ray20), "findIntersections() is incorrect");

    }

}