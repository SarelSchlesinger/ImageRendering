package geometries;

import org.junit.jupiter.api.Test;
import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import java.util.List;


import static org.junit.jupiter.api.Assertions.*;

class PlaneTest {

    Point p1 = new Point(3,0,0);
    Point p2 = new Point(0,4,0);
    Point p3 = new Point(0,0,0);
    Plane plane = new Plane(p1, p2, p3);


    @Test
    void testGetNormal() {
        assertEquals(plane.getNormal(), plane.getNormal(p3), "getNormal() is incorrect");

    }

    @Test
    void testFindIntersections() {

        Ray ray1 = new Ray(new Point(-1,-1,-1), new Vector(2,2,2));
        Ray ray2 = new Ray(new Point(-1,-1,-1), new Vector(2,2,-2));
        Ray ray3 = new Ray(new Point(0,0,0), new Vector(1,1,0));
        Ray ray4 = new Ray(new Point(3,0,5), new Vector(1,1,5));
        Ray ray5 = new Ray(new Point(5,4,0), new Vector(0,0,13));
        Ray ray6 = new Ray(new Point(5,4,3), new Vector(0,0,5));
        Ray ray7 = new Ray(new Point(5,4,-10), new Vector(0,0,20));
        Ray ray8 = new Ray(new Point(2,2,0), new Vector(1,1,1));
        Ray ray9 = new Ray(new Point(0,0,0), new Vector(1,1,1));


        // ============ Equivalence Partitions Tests ==============
        // The Ray must be neither orthogonal nor parallel to the plane
        // TC1: The ray intersects the plane
        assertEquals(List.of(new Point(0,0,0)), plane.findIntersections(ray1),"findIntersections() is incorrect");
        // TC2: The ray does not intersect the plane
        assertNull(plane.findIntersections(ray2), "findIntersections() is incorrect");

        // =============== Boundary Values Tests ==================
        // Ray is parallel to the plane - two cases
        // TC3: The ray is contained in the plane
        assertNull(plane.findIntersections(ray3), "findIntersections() is incorrect");
        // TC4: The ray does not contained in the plane
        assertNull(plane.findIntersections(ray4), "findIntersections() is incorrect");

        // Ray is orthogonal to the plane - three cases
        // TC5: The ray starts from the surface of the plane
        assertNull(plane.findIntersections(ray5),"findIntersections() is incorrect");
        // TC6: The ray starts after the plane
        assertNull(plane.findIntersections(ray6), "findIntersections() is incorrect");
        // TC7: The ray starts before the plane
        assertEquals(List.of(new Point(5,4,0)), plane.findIntersections(ray7), "findIntersections() is incorrect");

        // The ray is neither orthogonal nor parallel to the plane - two cases
        // TC8: The ray begins at the plane
        assertNull(plane.findIntersections(ray8), "findIntersections() is incorrect");
        // TC9: The ray begins at the same point which appears as a reference point in the plane
        assertNull(plane.findIntersections(ray9), "findIntersections() is incorrect");

    }
}