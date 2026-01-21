package java_snake;

import java.util.Random;

public class GameState {
    private final int width;
    private final int height;
    private Snake snake;
    private Point food;
    private boolean gameOver;
    private final Random random = new Random();

    public GameState(int width, int height) {
        this.width = width;
        this.height = height;
        this.snake = new Snake(new Point(width / 2, height / 2));
        this.gameOver = false;
        respawnFood();
    }

    public void reset() {
      
        // Create the snake again
        this.snake = new Snake(new Point(width / 2, height / 2));
        
        this.gameOver = false;
        
        respawnFood();
            }

    private void respawnFood() {
        int x = random.nextInt(width);
        int y = random.nextInt(height);
        this.food = new Point(x, y);
        
        // Ensure food doesn't spawn on snake body
        while (snake.getBody().contains(this.food)) {
             x = random.nextInt(width);
             y = random.nextInt(height);
             this.food = new Point(x, y);
        }
    }
    
    public void setFood(Point food) {
        this.food = food;
    }

    public void tick() {
        if (gameOver) return;

        Point nextHead = snake.peekNextHead();
        if (nextHead.equals(food)) {
            snake.setGrowing(true);
            respawnFood();
        }

        snake.move();
        
        Point head = snake.getHead();
        
        // Check Wall Collision
        if (head.x() < 0 || head.x() >= width || head.y() < 0 || head.y() >= height) {
            gameOver = true;
            return;
        }
        
        // Check Self Collision
        if (snake.checkSelfCollision()) {
            gameOver = true;
            return;
        }
    }

    public Snake getSnake() {
        return snake;
    }

    public Point getFood() {
        return food;
    }

    public boolean isGameOver() {
        return gameOver;
    }
    
    public int getWidth() { return width; }
    public int getHeight() { return height; }
}
