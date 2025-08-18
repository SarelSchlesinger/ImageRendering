package primitives;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UtilTest {

    @Test
    void testFindNearestSquare() {
        assertEquals(3, Util.findNearestSquareRoot(8), "findNearestSquareRoot() is incorrect");
        assertEquals(3, Util.findNearestSquareRoot(9), "findNearestSquareRoot() is incorrect");
        assertEquals(3, Util.findNearestSquareRoot(10), "findNearestSquareRoot() is incorrect");
        assertEquals(3, Util.findNearestSquareRoot(12), "findNearestSquareRoot() is incorrect");
        assertEquals(4, Util.findNearestSquareRoot(13), "findNearestSquareRoot() is incorrect");
        assertEquals(1, Util.findNearestSquareRoot(1), "findNearestSquareRoot() is incorrect");
        assertEquals(7, Util.findNearestSquareRoot(50), "findNearestSquareRoot() is incorrect");
        assertEquals(10, Util.findNearestSquareRoot(100), "findNearestSquareRoot() is incorrect");
        assertEquals(12, Util.findNearestSquareRoot(150), "findNearestSquareRoot() is incorrect");
        assertEquals(14, Util.findNearestSquareRoot(200), "findNearestSquareRoot() is incorrect");
        assertEquals(20, Util.findNearestSquareRoot(400), "findNearestSquareRoot() is incorrect");
    }
}