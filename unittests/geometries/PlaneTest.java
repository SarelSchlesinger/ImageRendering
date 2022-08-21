package geometries;

import org.junit.jupiter.api.Test;
import primitives.Point;
import primitives.Vector;

import static org.junit.jupiter.api.Assertions.*;

class PlaneTest {

    Point p1 = new Point(3,0,0);
    Point p2 = new Point(0,4,0);
    Point p3 = new Point(0,0,0);
    Plane plane = new Plane(p1, p2, p3);

    @Test
    void testGetNormal() {
        assertEquals(new Vector(0,0,12).normalize(), plane.getNormal(p3), "getNormal() is incorrect");

    }
}