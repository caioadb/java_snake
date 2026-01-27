package java_snake;

import javax.swing.JPanel;
import java.awt.Color;
import java.awt.Font;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Toolkit;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class GamePanel extends JPanel {
    private final GameState gameState;
    private final int tileSize;
    private JButton restartButton;

    public GamePanel(GameState gameState, int tileSize) {
        this.gameState = gameState;
        this.tileSize = tileSize;
        this.setPreferredSize(new Dimension(gameState.getWidth() * tileSize, gameState.getHeight() * tileSize));
        this.setBackground(Color.BLACK);
        this.setDoubleBuffered(true);
        // Disabling default layout to position button manually
        this.setLayout(null);
        // Creating the Restart Button
        restartButton = new JButton("RESTART");
        restartButton.setFocusable(false); // Preventing button from stealing keyboard focus!
        restartButton.setVisible(false);   // Hiding it initially
        // Defining what happens when clicked
        restartButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                gameState.reset();
                restartButton.setVisible(false); // Hiding button again
                requestFocusInWindow(); // Making sure keyboard still works
            }
        });
        // Adding button to the panel
        this.add(restartButton);
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

        if (gameState.isGameOver()) {
            g.setColor(Color.WHITE);
            g.drawString("Game Over", getWidth() / 2 - 30, getHeight() / 2);
            // Calculate center position for the button
        int btnWidth = 100;
        int btnHeight = 40;
        int x = (getWidth() - btnWidth) / 2;
        int y = (getHeight() - btnHeight) / 2 + 50; // Slightly below center

        // Position and show the button
        restartButton.setBounds(x, y, btnWidth, btnHeight);
        restartButton.setVisible(true);
    
        }

        Toolkit.getDefaultToolkit().sync();

        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.BOLD, 14));
        g.drawString("Score: " + gameState.getScore(), 10, 20);
    }
    
}
