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
        game.start();
        Point initialHead = game.getSnake().getHead();
        game.tick();
        assertNotEquals(initialHead, game.getSnake().getHead());
    }

    @Test
    void testWallCollision() {
        GameState game = new GameState(5, 5);
        // Snake starts at 2,2. Moves RIGHT.
        // 2,2 -> 3,2 -> 4,2 -> 5,2 (Collision)
        game.start();
        for (int i = 0; i < 10; i++) {
            game.tick();
            if (game.isGameOver()) break;
        }
        assertTrue(game.isGameOver());
    }
    
    @Test
    void testFoodConsumption() {
        GameState game = new GameState(10, 10);
        game.start();
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

    @Test
    void resetGameRestoresInitialState() {
        // Create a game board
        GameState state = new GameState(10, 10);
        // Simulate a "Game Over" scenario
        // We force the game to be over manually to test by crashing the snake to a wall
        state.start();
        state.getSnake().setDirection(Direction.LEFT); // Turn left towards wall 
        // Hit the wall (snake is at x=5, needs to go < 0) 
        for(int i = 0; i < 10; i++) {
            state.tick();
        }
        
        // Check if the snake is dead (Pre-condition check)
        assertTrue(state.isGameOver(), "Game should be over after hitting the wall");

        // Call the reset method 
        state.reset();
        
        // Check if the game is revived
        assertFalse(state.isGameOver(), "Game Over flag should be cleared after reset");
        
        // Check if the snake is back at start center position
        Point center = new Point(5, 5); // 10/2 = 5
        assertEquals(center, state.getSnake().getHead(), "Snake head should be back at center");
    }
    
    @Test
    void scoreTrackingAndReset() {
        GameState state = new GameState(10, 10);
        state.start();
        
        assertEquals(0, state.getScore());
        
        Point foodPos = new Point(6, 5);
        state.setFood(foodPos);
        state.tick();
        
        assertEquals(1, state.getScore());
        
        state.reset();
        
        assertEquals(0, state.getScore());
    }
}
