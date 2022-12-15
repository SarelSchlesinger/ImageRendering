package primitives;

import geometries.Intersectable.GeoPoint;

import geometries.Sphere;
import geometries.Triangle;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class RayTest {

    Ray ray = new Ray(new Point(1, 1, 1), new Vector(0, 0, 1));

    @Test
    void testGetPoint() {
        double t = 1;
        assertEquals(new Point(1, 1, 2), ray.getPoint(t), "getPoint() is incorrect");
    }


    @Test
    void testFindClosestPoint() {

        Point p1 = new Point(1, 1, 2);
        Point p2 = new Point(1, 1, 3);
        Point p3 = new Point(1, 1, 4);

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
        assertEquals(geoPoint1, ray.findClosestGeoPoint(List.of(geoPoint2, geoPoint1, geoPoint3)), "findClosestPoint() is incorrect");

        // =============== Boundary Values Tests ==================
        // TC2: Empty list of points
        assertNull(ray.findClosestPoint(List.of()), "findClosestPoint() is incorrect");
        // TC3: The closest point is the first point in the list
        assertEquals(geoPoint1, ray.findClosestGeoPoint(List.of(geoPoint1, geoPoint2, geoPoint3)), "findClosestPoint() is incorrect");
        // TC4: The closest point is the last point in the list
        assertEquals(geoPoint1, ray.findClosestGeoPoint(List.of(geoPoint2, geoPoint3, geoPoint1)), "findClosestPoint() is incorrect");

    }

    @Test
    void testFindPointOnTheOrthogonalVector() {
        assertEquals(new Point(1, -1, 5), ray.findPointOnTheOrthogonalVector(4, 2));
    }
}
