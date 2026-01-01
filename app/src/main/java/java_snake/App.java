package java_snake;

import javax.swing.JFrame;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class App {
    public static void main(String[] args) {
        int boardWidth = 30;
        int boardHeight = 30;
        int tileSize = 20;
        int targetTps = 5; // Ticks Per Second
        
        GameState gameState = new GameState(boardWidth, boardHeight);
        GamePanel gamePanel = new GamePanel(gameState, tileSize);
        
        JFrame frame = new JFrame("Java Snake");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(gamePanel);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        
        // Input Handling
        frame.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                switch (e.getKeyCode()) {
                    case KeyEvent.VK_UP -> gameState.getSnake().setDirection(Direction.UP);
                    case KeyEvent.VK_DOWN -> gameState.getSnake().setDirection(Direction.DOWN);
                    case KeyEvent.VK_LEFT -> gameState.getSnake().setDirection(Direction.LEFT);
                    case KeyEvent.VK_RIGHT -> gameState.getSnake().setDirection(Direction.RIGHT);
                }
            }
        });
        
        // Game Loop
        GameLoop gameLoop = new GameLoop(gameState, gamePanel, targetTps);
        new Thread(gameLoop).start();
    }
}
