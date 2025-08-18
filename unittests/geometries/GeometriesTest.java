package geometries;

import org.junit.jupiter.api.Test;
import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import static org.junit.jupiter.api.Assertions.*;

class GeometriesTest {

    Point a = new Point(3, 0, 0);
    Point b = new Point(0, 4, 0);
    Point c = Point.ZERO;
    Point d = new Point(0, 0, 7);
    Point e = new Point(5, 0, 0);
    Point f = new Point(0, 0, -3);
    Plane plane = new Plane(a, b, c);
    Triangle triangle = new Triangle(b, d, e);
    Sphere sphere = new Sphere(c, 1);
    Geometries geometries = new Geometries(plane, triangle, sphere);

    Ray ray1 = new Ray(f, new Vector(1, 4, 8));
    Ray ray2 = new Ray(f, new Vector(1, -1, 8));
    Ray ray3 = new Ray(f, new Vector(1, 1, 8));
    Ray ray4 = new Ray(f, new Vector(1, -5, 8));
    Ray ray5 = new Ray(f, new Vector(1, -5, -2));


    @Test
    void testFindIntersections() {

        // ============ Equivalence Partitions Tests ==============
        // The ray intersects the shapes but not all of them
        // TC1: The ray intersects the plane and the triangle but not the sphere (2 points)
        assertEquals(2, geometries.findIntersections(ray1).size(), "findIntersections() is incorrect");
        // TC2: The ray intersects the plane and the sphere but not the triangle (3 points)
        assertEquals(3, geometries.findIntersections(ray2).size(), "findIntersections() is incorrect");

        // =============== Boundary Values Tests ==================
        // TC3: The ray intersects all shapes
        assertEquals(4, geometries.findIntersections(ray3).size(), "findIntersections() is incorrect");
        // TC4: The ray intersects one shape
        assertEquals(1, geometries.findIntersections(ray4).size(), "findIntersections() is incorrect");
        // TC5: The ray does not intersect any shape
        assertNull(geometries.findIntersections(ray5), "findIntersections() is incorrect");

    }

    @Test
    void testFindGeoIntersectionsHelper() {

        // ============ Equivalence Partitions Tests ==============
        // The ray intersects the shapes but not all of them
        // TC1: The ray intersects the plane and the triangle but not the sphere (2 points)
        assertEquals(2, geometries.findGeoIntersectionsHelper(ray1, Double.POSITIVE_INFINITY).size(), "findGeoIntersectionsHelper() is incorrect");
        // TC2: The ray intersects the plane and the sphere but not the triangle (3 points)
        assertEquals(3, geometries.findGeoIntersectionsHelper(ray2, Double.POSITIVE_INFINITY).size(), "findGeoIntersectionsHelper() is incorrect");

        // =============== Boundary Values Tests ==================
        // TC3: The ray intersects all shapes
        assertEquals(4, geometries.findGeoIntersectionsHelper(ray3, Double.POSITIVE_INFINITY).size(), "findGeoIntersectionsHelper() is incorrect");
        // TC4: The ray intersects one shape
        assertEquals(1, geometries.findGeoIntersectionsHelper(ray4, Double.POSITIVE_INFINITY).size(), "findGeoIntersectionsHelper() is incorrect");
        // TC5: The ray does not intersect any shape
        assertNull(geometries.findGeoIntersectionsHelper(ray5, Double.POSITIVE_INFINITY), "findGeoIntersectionsHelper() is incorrect");
    }
}