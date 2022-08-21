package geometries;

import org.junit.jupiter.api.Test;
import primitives.Point;
import primitives.Vector;

import static org.junit.jupiter.api.Assertions.*;

class TriangleTest {

    Point p1 = new Point(0,0,0);
    Point p2 = new Point(5,0,0);
    Point p3 = new Point(0,4,0);
    Triangle triangle = new Triangle(p1, p2, p3);

    @Test
    void testGetNormal() {

        Point p4 = new Point(1,1,0);  // A point inside the area of the triangle

        // Check if the point inside the area of the triangle
        // this compute was taken from https://math.stackexchange.com/questions/4322/check-whether-a-point-is-within-a-3d-triangle
        double triangleArea = p2.subtract(p1).crossProduct(p3.subtract(p1)).length() * 0.5;
        double firstThirdOfTheTriangle = p2.subtract(p4).crossProduct(p3.subtract(p4)).length() / (2 * triangleArea);
        double secondThirdOfTheTriangle = p3.subtract(p4).crossProduct(p1.subtract(p4)).length() / (2 * triangleArea);
        double thirdThirdOfTheTriangle = 1 - firstThirdOfTheTriangle - secondThirdOfTheTriangle;
        assertTrue(firstThirdOfTheTriangle >= 0 && secondThirdOfTheTriangle >= 0 && thirdThirdOfTheTriangle >= 0 &&
                            firstThirdOfTheTriangle <= 1 && secondThirdOfTheTriangle <= 1 && thirdThirdOfTheTriangle <= 1,
                "The given point is not inside the triangle");

        assertEquals(new Vector(0,0,-20).normalize(), triangle.getNormal(p4), "getNormal() is incorrect");

    }

}