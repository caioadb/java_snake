package java_snake;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class AppTest {
    @Test void appClassExists() {
        try {
            Class.forName("java_snake.App");
        } catch (ClassNotFoundException e) {
            fail("App class should exist");
        }
    }
}
