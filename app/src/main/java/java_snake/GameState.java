package java_snake;

import java.util.Random;

public class GameState {
    private final int width;
    private final int height;
    private Snake snake;
    private Point food;
    private boolean onMenu;
    private boolean gameOver;
    private final Random random = new Random();
    private int score;

    public GameState(int width, int height) {

        this.width = width;
        this.height = height;
        this.gameOver = false;
        this.onMenu = true;
        this.snake = new Snake(new Point(width / 2, height / 2));
        this.score = 0;
        respawnFood();
    
    }

    public void start() {

        this.snake = new Snake(new Point(width / 2, height / 2));
        this.score = 0;
        onMenu = false;    
        respawnFood();

    }

    public void stop() {

        gameOver = false;
        onMenu = true;

    }

    public void reset() {
      
        // Create the snake again
        this.snake = new Snake(new Point(width / 2, height / 2));
        
        this.gameOver = false;
        
        this.score = 0;
        
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
        if (onMenu) return;

        Point nextHead = snake.peekNextHead();
        if (nextHead.equals(food)) {
            snake.setGrowing(true);
            score++;
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
    
    public boolean isOnMenu() {
        return onMenu;
    }
    
    public int getWidth() { return width; }
    public int getHeight() { return height; }
    public int getScore() {
        return score;
    }
}
