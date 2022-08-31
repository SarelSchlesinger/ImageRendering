package geometries;

import org.junit.jupiter.api.Test;
import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class SphereTest {

    Point p0 = new Point(0,0,0);
    Sphere sphere = new Sphere(p0, 1);


    @Test
    void testGetNormal() {

        Point p1 = new Point(1,0,0);  // point on the surface of the sphere

        // Check if the point is on the surface of the sphere
        assertEquals(sphere.radius, p1.distance(sphere.center), "the point is not on the surface of the sphere");

        assertEquals(new Vector(1,0,0).normalize(), sphere.getNormal(p1), "getNormal() is incorrect");
    }

    @Test
    void testFindIntersections() {

        Ray ray1 = new Ray(new Point(2,2,4), new Vector(1,1,1));
        Ray ray2 = new Ray(new Point(0.2,0.2,0.4), new Vector(1,1,1));
        Ray ray3 = new Ray(new Point(1,0,4), new Vector(0,0,-1));
        Ray ray4 = new Ray(new Point(2,2,4), new Vector(-1.8, -1.7, -4.4));

        // ============ Equivalence Partitions Tests ==============
        // First Equivalence Partition - No intersection points
        // TC1: The ray origin is outside the sphere and the ray does not intersect the sphere
        assertNull(sphere.findIntersections(ray1), "findIntersections() is incorrect");
        assertEquals(0, sphere.findIntersections(ray1).size(), "findIntersections() is incorrect");

        // Second Equivalence Partition - One intersection point
        // TC2: The ray origin is inside the sphere and the ray intersects the sphere at one point
        assertEquals(List.of(new Point(0.502933583021169, 0.502933583021169, 0.702933583021169)),
                sphere.findIntersections(ray2), "findIntersections() is incorrect");
        assertEquals(1, sphere.findIntersections(ray2).size(), "findIntersections() is incorrect");
        // TC3: The ray origin is outside the sphere and the ray tangent the sphere at one point
        assertEquals(List.of(new Point(1,0,0)), sphere.findIntersections(ray3), "findIntersections() is incorrect");
        assertEquals(1, sphere.findIntersections(ray3).size(), "findIntersections() is incorrect");

        // Third Equivalence Partition - Two intersection points
        // TC4: The ray origin is outside the sphere and the ray intersects the sphere at two points
        assertEquals(List.of(new Point(0.56976355325054, 0.64922113362551, 0.50386646350132),
                             new Point(-0.044067201740144, 0.06949208724542, -0.996608715364796)),
                sphere.findIntersections(ray4), "findIntersections() is incorrect");
        assertEquals(2, sphere.findIntersections(ray4).size(), "findIntersections() is incorrect");

        // =============== Boundary Values Tests ==================

    }
}