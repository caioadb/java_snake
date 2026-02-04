package java_snake;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class SnakeTest {
    @Test
    void testInitialState() {
        Snake snake = new Snake(new Point(10, 10));
        assertEquals(1, snake.getBody().size());
        assertEquals(new Point(10, 10), snake.getHead());
    }

    @Test
    void testMove() {
        Snake snake = new Snake(new Point(10, 10));
        snake.setDirection(Direction.RIGHT);
        snake.move();
        assertEquals(new Point(11, 10), snake.getHead());
        assertEquals(1, snake.getBody().size());
    }

    @Test
    void testGrow() {
        Snake snake = new Snake(new Point(10, 10));
        snake.setDirection(Direction.RIGHT);
        snake.setGrowing(true);
        snake.move();
        assertEquals(2, snake.getBody().size());
        assertEquals(new Point(11, 10), snake.getHead());
        assertEquals(new Point(10, 10), snake.getBody().get(1));
    }
    
    @Test
    void testSelfCollision() {
        Snake snake = new Snake(new Point(10, 10));
        snake.setDirection(Direction.RIGHT);
        snake.setGrowing(true);
        snake.move(); // 11, 10 (Head), 10, 10
        
        snake.setDirection(Direction.DOWN);
        snake.setGrowing(true);
        snake.move(); // 11, 11 (Head), 11, 10, 10, 10
        
        snake.setDirection(Direction.LEFT);
        snake.setGrowing(true);
        snake.move(); // 10, 11 (Head), 11, 11, 11, 10, 10, 10
        
        snake.setDirection(Direction.UP);
        snake.setGrowing(true);
        snake.move(); // 10, 10 (Head) -> Collision with tail (10, 10)
        
        assertTrue(snake.checkSelfCollision());
    }
    
    @Test
    void testPreventReverseDirection() {
        Snake snake = new Snake(new Point(10, 10));
        snake.setDirection(Direction.RIGHT);
        snake.setDirection(Direction.LEFT); // Should be ignored
        assertEquals(Direction.RIGHT, snake.getDirection());
        
        snake.move(); // Reset canChangeDirection
        
        snake.setDirection(Direction.UP);
        assertEquals(Direction.UP, snake.getDirection());
    }
}
