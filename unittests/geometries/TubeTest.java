package geometries;

import org.junit.jupiter.api.Test;
import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import static org.junit.jupiter.api.Assertions.*;

class TubeTest {

    Point p0 = new Point(0,0,0);
    Vector direction = new Vector(5,0,0).normalize();
    Ray ray = new Ray(p0, direction);
    Tube tube = new Tube(ray, 1);

    @Test
    void testGetNormal() {

        Point p1 = new Point(1,0,0);  // point on the surface of the tube

        // Check if the point is on the surface of the tube
        assertEquals(tube.radius, p1.distance(tube.axisRay.getP0()), "the point is not on the surface of the tube");

        assertEquals(new Vector(1,0,0).normalize(), tube.getNormal(p1), "getNormal() is incorrect");

    }
}