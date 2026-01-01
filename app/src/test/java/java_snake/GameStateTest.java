package java_snake;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class GameStateTest {
    @Test
    void testInitialization() {
        GameState game = new GameState(20, 20);
        assertNotNull(game.getSnake());
        assertNotNull(game.getFood());
        assertFalse(game.isGameOver());
        assertEquals(20, game.getWidth());
        assertEquals(20, game.getHeight());
    }

    @Test
    void testTickMovesSnake() {
        GameState game = new GameState(20, 20);
        Point initialHead = game.getSnake().getHead();
        game.tick();
        assertNotEquals(initialHead, game.getSnake().getHead());
    }

    @Test
    void testWallCollision() {
        GameState game = new GameState(5, 5);
        // Snake starts at 2,2. Moves RIGHT.
        // 2,2 -> 3,2 -> 4,2 -> 5,2 (Collision)
        
        for (int i = 0; i < 10; i++) {
            game.tick();
            if (game.isGameOver()) break;
        }
        assertTrue(game.isGameOver());
    }
    
    @Test
    void testFoodConsumption() {
        GameState game = new GameState(10, 10);
        Point head = game.getSnake().getHead();
        // Place food right in front of snake (RIGHT)
        Point foodPos = new Point(head.x() + 1, head.y());
        game.setFood(foodPos);
        
        game.tick();
        
        // Snake head should be at food pos
        assertEquals(foodPos, game.getSnake().getHead());
        // Food should have moved
        assertNotEquals(foodPos, game.getFood());
        // Snake should have grown (size 2)
        assertEquals(2, game.getSnake().getBody().size());
    }
}
