/**
 *
 */
package geometries;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import primitives.*;

import java.util.List;

/**
 * Testing Polygons
 *
 * @author Dan
 */
public class PolygonTest {

    /**
     * Test method for {@link geometries.Polygon#Polygon(primitives.Point...)}.
     */
    @Test
    public void testConstructor() {

        // ============ Equivalence Partitions Tests ==============

        // TC01: Correct concave quadrangular with vertices in correct order
        try {
            new Polygon(new Point(0, 0, 1), new Point(1, 0, 0), new Point(0, 1, 0), new Point(-1, 1, 1));
        } catch (IllegalArgumentException e) {
            fail("Failed constructing a correct polygon");
        }

        // TC02: Wrong vertices order
        assertThrows(IllegalArgumentException.class, //
                     () -> new Polygon(new Point(0, 0, 1), new Point(0, 1, 0), new Point(1, 0, 0), new Point(-1, 1, 1)), //
                     "Constructed a polygon with wrong order of vertices");

        // TC03: Not in the same plane
        assertThrows(IllegalArgumentException.class, //
                     () -> new Polygon(new Point(0, 0, 1), new Point(1, 0, 0), new Point(0, 1, 0), new Point(0, 2, 2)), //
                     "Constructed a polygon with vertices that are not in the same plane");

        // TC04: Concave quadrangular
        assertThrows(IllegalArgumentException.class, //
                     () -> new Polygon(new Point(0, 0, 1), new Point(1, 0, 0), new Point(0, 1, 0),
                                       new Point(0.5, 0.25, 0.5)), //
                     "Constructed a concave polygon");

        // =============== Boundary Values Tests ==================

        // TC10: Vertex on a side of a quadrangular
        assertThrows(IllegalArgumentException.class, //
                     () -> new Polygon(new Point(0, 0, 1), new Point(1, 0, 0), new Point(0, 1, 0), new Point(0, 0.5, 0.5)),
                     "Constructed a polygon with vertix on a side");

        // TC11: Last point = first point
        assertThrows(IllegalArgumentException.class, //
                     () -> new Polygon(new Point(0, 0, 1), new Point(1, 0, 0), new Point(0, 1, 0), new Point(0, 0, 1)),
                     "Constructed a polygon with vertice on a side");

        // TC12: Co-located points
        assertThrows(IllegalArgumentException.class, //
                     () -> new Polygon(new Point(0, 0, 1), new Point(1, 0, 0), new Point(0, 1, 0), new Point(0, 1, 0)),
                     "Constructed a polygon with vertice on a side");

    }

    /**
     * Test method for {@link geometries.Polygon#getNormal(primitives.Point)}.
     */
    @Test
    public void testGetNormal() {

        // ============ Equivalence Partitions Tests ==============
        // TC01: There is a simple single test here
        Polygon pl = new Polygon(new Point(0, 0, 1), new Point(1, 0, 0), new Point(0, 1, 0), new Point(-1, 1, 1));
        double sqrt3 = Math.sqrt(1d / 3);
        assertEquals(new Vector(sqrt3, sqrt3, sqrt3), pl.getNormal(new Point(0, 0, 1)), "Bad normal to trinagle");
    }

    @Test
    void testFindIntersections() {
        Polygon polygon = new Polygon(
                new Point(5, -2, 0),
                new Point(4, 4, 0),
                new Point(0, 5, 0),
                new Point(-4, 1, 0),
                new Point(0, -3, 0));

        Ray ray1 = new Ray(new Point(0, 0, -5), new Vector(-2, -2, 5));
        Ray ray2 = new Ray(new Point(0, 0, -5), new Vector(0, -6, 5));
        Ray ray3 = new Ray(new Point(0, 5, -3), new Vector(0, -6, 3));
        Ray ray4 = new Ray(new Point(0, 0, -5), new Vector(2, 4.5, 5));
        Ray ray5 = new Ray(new Point(0, 0, -5), new Vector(0, -3, 5));
        Ray ray6 = new Ray(new Point(0, 5, -3), new Vector(17.77777777777778, 0.555555555555555, 5));
        Ray ray7 = new Ray(new Point(-6, 4, 0), new Vector(16, -14, 0));
        Ray ray8 = new Ray(new Point(0, 0, 0), new Vector(16, 2, 1));

        // ============ Equivalence Partitions Tests ==============

        // First Equivalence Partition - No intersection points
        // TC1: The ray does not intersect the polygon - the ray passes against the side
        assertNull(polygon.findIntersections(ray1), "findIntersections() is incorrect");
        // TC2: The ray does not intersect the polygon - the ray passes against the vertex
        assertNull(polygon.findIntersections(ray2), "findIntersections() is incorrect");

        // Second Equivalence Partition - One intersection point
        // TC3: The ray intersects the polygon at one point
        assertEquals(List.of(new Point(0, -1, 0)), polygon.findIntersections(ray3), "findIntersections() is incorrect");
        assertEquals(1, polygon.findIntersections(ray3).size(), "findIntersections() is incorrect");

        // =============== Boundary Values Tests ==================
        // TC4: The ray intersects the polygon's side
        assertNull(polygon.findIntersections(ray4), "findIntersections() is incorrect");
        // TC5: The ray intersects the polygon's vertex
        assertNull(polygon.findIntersections(ray5), "findIntersections() is incorrect");
        // TC6: The ray intersects the polygon's side continuation
        assertNull(polygon.findIntersections(ray6), "findIntersections() is incorrect");
        // TC7: The ray parallel to the polygon plane
        assertNull(polygon.findIntersections(ray7), "findIntersections() is incorrect");
        // TC8: The ray starts from the surface of the polygon
        assertNull(polygon.findIntersections(ray8), "findIntersections() is incorrect");

    }

}
