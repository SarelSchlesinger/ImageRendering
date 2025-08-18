package primitives;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ColorTest {

    Color color1 = Color.BLACK;
    Color color2 = new Color(255, 255, 255);
    Color color3 = new Color(45, 99, 201);
    Color color4 = new Color(10, 10, 10);
    Color color5 = new Color(100, 100, 100);

    @Test
    void testAdd() {
        assertEquals(color1, color1.add(color1), "add() is incorrect");
        assertEquals(color3, color1.add(color3), "add() is incorrect");
    }

    @Test
    void testScale() {
        assertEquals(color1, color1.scale(color4.getRgb()), "scale() is incorrect");
        assertEquals(color1, color4.scale(color1.getRgb()), "scale() is incorrect");
        assertEquals(color5, color4.scale(color4.getRgb()), "scale() is incorrect");
        assertEquals(color5, color4.scale(10), "scale() is incorrect");
    }

    @Test
    void testReduce() {
        assertEquals(color4, color5.reduce(color4.getRgb()), "reduce() is incorrect");
        assertEquals(color4, color5.reduce(10), "reduce() is incorrect");
        assertThrows(IllegalArgumentException.class, () -> color2.reduce(color1.getRgb()), "reduce() is incorrect");
    }

    @Test
    void testAvg() {
        assertEquals(new Color(100d, 118d, 152d), Color.avg(List.of(color1, color2, color3)), "avg() is incorrect");
        assertEquals(color3, Color.avg(List.of(color3)), "avg() is incorrect");
        assertThrows(IllegalArgumentException.class, () -> Color.avg(List.of()), "avg() is incorrect");
    }
}