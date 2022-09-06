package geometries;

import org.junit.jupiter.api.Test;
import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import static org.junit.jupiter.api.Assertions.*;

class TubeTest {

    Point p0 = new Point(0,0,0);
    Vector direction = new Vector(1,0,0);
    Ray ray = new Ray(p0, direction);
    Tube tube = new Tube(ray, 1);

    @Test
    void testGetNormal() {

        Point p1 = new Point(2,1,0);  // point on the surface of the tube
        double t = p1.subtract(tube.axisRay.getP0()).dotProduct(tube.axisRay.getDirection());
        Point pointOnRay = tube.axisRay.getP0().add(tube.axisRay.getDirection().scale(t));

        // Check if the point is on the surface of the tube
        assertEquals(tube.radius, p1.distance(pointOnRay),"the point is not on the surface of the tube");

        assertEquals(new Vector(0,1,0).normalize(), tube.getNormal(p1), "getNormal() is incorrect");

    }

    @Test
    void testFindIntersections() {
    }
}