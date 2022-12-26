package renderer;

import static java.lang.Math.sqrt;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import primitives.*;

/**
 * Testing Camera Class
 *
 * @author Dan
 */
class CameraTest {

    Vector x_axis = new Vector(1, 0, 0);
    Vector y_axis = new Vector(0, 1, 0);
    Vector z_axis = new Vector(0, 0, 1);

    Camera camera1 = new Camera(Point.ZERO,
                                new Vector(0, 0, -1),
                                new Vector(0, -1, 0))
            .setViewPlaneDistance(10);

    Camera camera2 = new Camera(new Point(0, 3, 0),
                                new Vector(0, -1, 0),
                                z_axis);

    Camera camera3 = new Camera(new Point(2, 0, 0),
                                new Vector(-1, 0, 0),
                                z_axis);

    Camera camera4 = new Camera(new Point(0, 5, 0),
                                new Vector(0, -1, 0),
                                z_axis);

    Camera camera5 = new Camera(new Point(-sqrt(2), sqrt(2), 0),
                                new Vector(sqrt(2), -sqrt(2), 0),
                                new Vector(-sqrt(2d / 3), -sqrt(2d / 3), 2 * sqrt(2d / 3)));

    /**
     * Test method for
     * {@link renderer.Camera#constructRay(int, int, int, int)}.
     */
    @Test
    void testConstructRay() {

        // ============ Equivalence Partitions Tests ==============
        // EP01: 4X4 Inside (1,1)
        assertEquals(new Ray(Point.ZERO, new Vector(1, -1, -10)),
                     camera1.setViewPlaneSize(8, 8)
                            .constructRay(4, 4, 1, 1), "constructRay() is incorrect");

        // =============== Boundary Values Tests ==================
        // BV01: 3X3 Center (1,1)
        assertEquals(new Ray(Point.ZERO, new Vector(0, 0, -10)),
                     camera1.setViewPlaneSize(6, 6)
                            .constructRay(3, 3, 1, 1), "constructRay() is incorrect");

        // BV02: 3X3 Center of Upper Side (0,1)
        assertEquals(new Ray(Point.ZERO, new Vector(0, -2, -10)),
                     camera1.setViewPlaneSize(6, 6)
                            .constructRay(3, 3, 1, 0), "constructRay() is incorrect");

        // BV03: 3X3 Center of Left Side (1,0)
        assertEquals(new Ray(Point.ZERO, new Vector(2, 0, -10)),
                     camera1.setViewPlaneSize(6, 6)
                            .constructRay(3, 3, 0, 1), "constructRay() is incorrect");

        // BV04: 3X3 Corner (0,0)
        assertEquals(new Ray(Point.ZERO, new Vector(2, -2, -10)),
                     camera1.setViewPlaneSize(6, 6)
                            .constructRay(3, 3, 0, 0), "constructRay() is incorrect");

        // BV05: 4X4 Corner (0,0)
        assertEquals(new Ray(Point.ZERO, new Vector(3, -3, -10)),
                     camera1.setViewPlaneSize(8, 8)
                            .constructRay(4, 4, 0, 0), "constructRay() is incorrect");

        // BV06: 4X4 Side (0,1)
        assertEquals(new Ray(Point.ZERO, new Vector(1, -3, -10)),
                     camera1.setViewPlaneSize(8, 8)
                            .constructRay(4, 4, 1, 0), "constructRay() is incorrect");

    }

    @Test
    void testRotateCamera() {

        // **** Group1: The camera rotates around the X-axis
        camera2.rotateCamera(new Ray(Point.ZERO, x_axis), 90);
        assertEquals(new Point(0, 0, 3), camera2.getCameraPosition());
        assertEquals(new Vector(0, 0, -1), camera2.getvTo());
        assertEquals(new Vector(0, -1, 0), camera2.getvUp());
        assertEquals(new Vector(-1, 0, 0), camera2.getvRight());

        // **** Group2: The camera rotates around the Y-axis
        camera3.rotateCamera(new Ray(Point.ZERO, y_axis), 90);
        assertEquals(new Point(0, 0, -2), camera3.getCameraPosition());
        assertEquals(z_axis, camera3.getvTo());
        assertEquals(x_axis, camera3.getvUp());
        assertEquals(y_axis, camera3.getvRight());

        // **** Group3: The camera rotates around the Z-axis
        camera4.rotateCamera(new Ray(Point.ZERO, z_axis), 90);
        assertEquals(new Point(-5, 0, 0), camera4.getCameraPosition());
        assertEquals(x_axis, camera4.getvTo());
        assertEquals(z_axis, camera4.getvUp());
        assertEquals(new Vector(0, -1, 0), camera4.getvRight());

        // **** Group4: The camera rotates around arbitrary axis
        camera5.rotateCamera(new Ray(Point.ZERO, new Vector(1, 1, 1)), 180);
        assertEquals(new Point(sqrt(2), -sqrt(2), 0), camera5.getCameraPosition());
        assertEquals(new Vector(-sqrt(2), sqrt(2), 0).normalize(), camera5.getvTo());
        assertEquals(new Vector(sqrt(2d / 3), sqrt(2d / 3), -2 * sqrt(2d / 3)).normalize(), camera5.getvUp());
        assertEquals(new Vector(-1 / sqrt(3), -1 / sqrt(3), -1 / sqrt(3)), camera5.getvRight());
    }
}