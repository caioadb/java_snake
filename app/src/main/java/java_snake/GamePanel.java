package java_snake;

import javax.swing.JPanel;
import java.awt.Color;
import java.awt.Font;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Toolkit;

public class GamePanel extends JPanel {
    private final GameState gameState;
    private Menu menu;
    private Sounds sounds;
    private final int tileSize;

    public GamePanel(GameState gameState, int tileSize) {

        this.gameState = gameState;
        this.tileSize = tileSize;
        this.menu = new Menu(gameState, tileSize);
        this.sounds = new Sounds(gameState);

        this.setPreferredSize(new Dimension(gameState.getWidth() * tileSize, gameState.getHeight() * tileSize));
        this.setBackground(Color.BLACK);
        this.setDoubleBuffered(true);
        // Disabling default layout to position button manually
        this.setLayout(null);

        gameState.addObserver(menu);
        gameState.addObserver(sounds);

        this.add(menu);
        menu.updateState();

        // Creating the Buttons
        
    }
    
    @Override
    protected void paintComponent(Graphics g) {    

        super.paintComponent(g);

        g.setColor(Color.RED);
        Point food = gameState.getFood();
        g.fillRect(food.x() * tileSize, food.y() * tileSize, tileSize, tileSize);

        g.setColor(Color.GREEN);
        for (Point p : gameState.getSnake().getBody()) {
            g.fillRect(p.x() * tileSize, p.y() * tileSize, tileSize, tileSize);
        }

        Toolkit.getDefaultToolkit().sync();

        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.BOLD, 14));
        g.drawString("Score: " + gameState.getScore(), 10, 20);

    }
    
}
