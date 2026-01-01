package java_snake;

import javax.swing.JPanel;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Toolkit;

public class GamePanel extends JPanel {
    private final GameState gameState;
    private final int tileSize;

    public GamePanel(GameState gameState, int tileSize) {
        this.gameState = gameState;
        this.tileSize = tileSize;
        this.setPreferredSize(new Dimension(gameState.getWidth() * tileSize, gameState.getHeight() * tileSize));
        this.setBackground(Color.BLACK);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        
        // Draw Food
        g.setColor(Color.RED);
        Point food = gameState.getFood();
        g.fillRect(food.x() * tileSize, food.y() * tileSize, tileSize, tileSize);
        
        // Draw Snake
        g.setColor(Color.GREEN);
        for (Point p : gameState.getSnake().getBody()) {
            g.fillRect(p.x() * tileSize, p.y() * tileSize, tileSize, tileSize);
        }
        
        // Draw Game Over
        if (gameState.isGameOver()) {
            g.setColor(Color.WHITE);
            g.drawString("Game Over", getWidth() / 2 - 30, getHeight() / 2);
        }
        
        Toolkit.getDefaultToolkit().sync();
    }
}
