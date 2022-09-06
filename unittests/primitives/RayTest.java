package primitives;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RayTest {

    Ray ray = new Ray(new Point(1,1,1), new Vector(0,0,1));

    @Test
    void testGetPoint() {
        double t = 1;
        assertEquals(new Point(1,1,2), ray.getPoint(t), "getPoint() is incorrect");
    }
}