package geometries;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.aggregator.ArgumentAccessException;
import primitives.Point;
import primitives.Vector;

import static org.junit.jupiter.api.Assertions.*;

class SphereTest {

    Point p0 = new Point(0,0,0);
    Sphere sphere = new Sphere(p0, 1);


    @Test
    void testGetNormal() {

        Point p1 = new Point(1,0,0);  // point on the surface of the sphere

        // Check if the point is on the envelope of the sphere
        assertEquals(sphere.radius, p1.distance(sphere.center), "the point is not on the surface of the sphere");

        assertEquals(new Vector(1,0,0).normalize(), sphere.getNormal(p1), "getNormal() is incorrect");
    }
}