package java_snake;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class PointTest {
    @Test
    void testEquality() {
        Point p1 = new Point(1, 2);
        Point p2 = new Point(1, 2);
        Point p3 = new Point(3, 4);

        assertEquals(p1, p2);
        assertNotEquals(p1, p3);
    }
    
    @Test
    void testAccessors() {
        Point p = new Point(5, 10);
        assertEquals(5, p.x());
        assertEquals(10, p.y());
    }
}
