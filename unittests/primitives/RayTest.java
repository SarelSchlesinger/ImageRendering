package primitives;

import geometries.Intersectable.GeoPoint;

import geometries.Sphere;
import geometries.Triangle;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


class RayTest {

    Point p0 = new Point(1, 1, 1);
    Vector vector1 = new Vector(0, 0, 1);
    Vector vector2 = new Vector(1, 0, 0);
    Ray ray1 = new Ray(p0, vector1);
    Ray ray2 = new Ray(p0, vector1, vector2);

    @Test
    void testRayConstructorValidInput() {
        // ============ 2 parameters constructor ==============
        assertDoesNotThrow(() -> new Ray(p0, vector1));
        assertEquals(ray1, new Ray(p0, vector1));
        assertThrows(IllegalArgumentException.class, () -> new Ray(null, vector1));
        assertThrows(IllegalArgumentException.class, () -> new Ray(p0, null));

        // ============ 3 parameters constructor ==============
        assertDoesNotThrow(() -> new Ray(p0, vector1, vector2));
        assertEquals(ray2, new Ray(p0, vector1, vector2));
        assertThrows(IllegalArgumentException.class, () -> new Ray(null, vector1, vector2));
        assertThrows(IllegalArgumentException.class, () -> new Ray(p0, null, vector2));
        assertThrows(IllegalArgumentException.class, () -> new Ray(p0, vector1, null));
    }

    @Test
    void testGetPoint() {
        assertEquals(new Point(1, 1, 2), ray1.getPoint(1), "getPoint() is incorrect");
    }


    @Test
    void testFindClosestPoint() {

        Point p1 = new Point(1, 1, 2);
        Point p2 = new Point(1, 1, 3);
        Point p3 = new Point(1, 1, 4);

        // ============ Equivalence Partitions Tests ==============
        // TC1: The closest point is in the middle of the list
        assertEquals(p1, ray1.findClosestPoint(List.of(p2, p1, p3)), "findClosestPoint() is incorrect");

        // =============== Boundary Values Tests ==================
        // TC2: Empty list of points
        assertNull(ray1.findClosestPoint(List.of()), "findClosestPoint() is incorrect");
        // TC3: The closest point is the first point in the list
        assertEquals(p1, ray1.findClosestPoint(List.of(p1, p2, p3)), "findClosestPoint() is incorrect");
        // TC4: The closest point is the last point in the list
        assertEquals(p1, ray1.findClosestPoint(List.of(p2, p3, p1)), "findClosestPoint() is incorrect");
    }

    @Test
    void testFindClosestGeoPoint() {

        Point p1 = new Point(1, 1, 2);
        Point p2 = new Point(1, 1, 3);
        Point p3 = new Point(1, 1, 4);
        Sphere sphere = new Sphere(p2, 1);
        Triangle triangle = new Triangle(new Point(3, 3, 3),
                                         new Point(-8, -3, 3),
                                         new Point(3, -4, 3));
        GeoPoint geoPoint1 = new GeoPoint(sphere, p1);
        GeoPoint geoPoint2 = new GeoPoint(triangle, p2);
        GeoPoint geoPoint3 = new GeoPoint(sphere, p3);

        // ============ Equivalence Partitions Tests ==============
        // TC1: The closest point is in the middle of the list
        assertEquals(geoPoint1, ray1.findClosestGeoPoint(List.of(geoPoint2, geoPoint1, geoPoint3)), "findClosestPoint() is incorrect");

        // =============== Boundary Values Tests ==================
        // TC2: Empty list of points
        assertNull(ray1.findClosestPoint(List.of()), "findClosestPoint() is incorrect");
        // TC3: The closest point is the first point in the list
        assertEquals(geoPoint1, ray1.findClosestGeoPoint(List.of(geoPoint1, geoPoint2, geoPoint3)), "findClosestPoint() is incorrect");
        // TC4: The closest point is the last point in the list
        assertEquals(geoPoint1, ray1.findClosestGeoPoint(List.of(geoPoint2, geoPoint3, geoPoint1)), "findClosestPoint() is incorrect");

    }

    @Test
    void testFindPointOnTheOrthogonalVector() {
        assertEquals(new Point(1, -1, 5), ray1.findPointOnTheOrthogonalVector(4, 2));
    }
}
