package java_snake;

import java.util.Random;
import java.util.ArrayList;
import java.util.List;

public class GameState {
    
    private final int width;
    private final int height;
    private List<GameObserver> observers = new ArrayList<>();
    private Snake snake;
    private Point food;
    private boolean onMenu;
    private boolean gameOver;
    private final Random random = new Random();
    private int score;
    private HighScoreManager highscoreManager;

    public GameState(int width, int height) {

        this.width = width;
        this.height = height;
        this.gameOver = false;
        this.onMenu = true;
        this.snake = new Snake(new Point(width / 2, height / 2));
        this.score = 0;
        this.highscoreManager = new HighScoreManager();
        respawnFood();

    }
    
    public void addObserver(GameObserver observer) {
        observers.add(observer);
    }

    private void notifyObservers() {
        for (GameObserver observer : observers) {
            observer.onGameState(this);
        }
    }

    private void doSound(int choice) {
        for (GameObserver observer : observers) {
            observer.playSound(this, choice);
        }
    }

    public void start() {

        this.snake = new Snake(new Point(width / 2, height / 2));
        this.score = 0;
        onMenu = false;    
        respawnFood();
        notifyObservers();
        doSound(1);

    }

    public void stop() {

        gameOver = false;
        onMenu = true;
        notifyObservers();
        doSound(0);

    }

    public void reset() {
      
        // Create the snake again
        this.snake = new Snake(new Point(width / 2, height / 2));
        this.gameOver = false;
        this.score = 0;
        respawnFood();
        notifyObservers();
        doSound(1);

    }

    private void respawnFood() {
        
        int x = random.nextInt(width);
        int y = random.nextInt(height);
        this.food = new Point(x, y);
        doSound(0);
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
        if (gameOver || onMenu) {
            notifyObservers();
            return;
        }

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
            notifyObservers();
            doSound(2);
            gameOver = true;
            if (highscoreManager.isHighScore(score)) {
                String name = javax.swing.JOptionPane.showInputDialog("NEW RECORD! Enter 3 Initials:");
                if (name != null && !name.isEmpty()) {
                    highscoreManager.addScore(name, score);
                }
            }
            return;
        }
        
        // Check Self Collision
        if (snake.checkSelfCollision()) {
            notifyObservers();
            doSound(2);
            gameOver = true;
            if (highscoreManager.isHighScore(score)) {
                String name = javax.swing.JOptionPane.showInputDialog("NEW RECORD! Enter 3 Initials:");
                if (name != null && !name.isEmpty()) {
                    highscoreManager.addScore(name, score);
                }
            }
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
    public int getScore() { return score; }
}
