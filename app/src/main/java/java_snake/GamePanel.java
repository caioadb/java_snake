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
    private JButton startButton;
    private JButton settingsButton;
    private JButton quitButton;
    private JButton restartButton;
    private JButton toMenuButton;

    public GamePanel(GameState gameState, int tileSize) {
        this.gameState = gameState;
        this.tileSize = tileSize;
        this.setPreferredSize(new Dimension(gameState.getWidth() * tileSize, gameState.getHeight() * tileSize));
        this.setBackground(Color.BLACK);
        this.setDoubleBuffered(true);
        // Disabling default layout to position button manually
        this.setLayout(null);
        // Creating the Restart Button
        createButtons();
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

        if (gameState.isOnMenu()) {
            g.setColor(Color.WHITE);
            g.drawString("SNAKE!", getWidth() / 2 - 30, getHeight() / 2);
            int btnWidth = 110;
            int btnHeight = 40;
            int x = (getWidth() - btnWidth) / 2;
            int y = (getHeight() - btnHeight) / 2; // Slightly below center
            startButton.setBounds(x, y - 50, btnWidth, btnHeight);
            startButton.setVisible(true);
            settingsButton.setBounds(x, y, btnWidth, btnHeight);
            settingsButton.setVisible(true);
            quitButton.setBounds(x, y + 50, btnWidth, btnHeight);
            quitButton.setVisible(true);
        }

        if (gameState.isGameOver()) {
            g.setColor(Color.WHITE);
            g.drawString("Game Over", getWidth() / 2 - 30, getHeight() / 2 - 40);
            // Calculate center position for the button
            int btnWidth = 110;
            int btnHeight = 40;
            int x = (getWidth() - btnWidth) / 2;
            int y = (getHeight() - btnHeight) / 2; // Slightly below center
            // Position and show the button
            restartButton.setBounds(x, y, btnWidth, btnHeight);
            restartButton.setVisible(true);
            toMenuButton.setBounds(x, y + 50, btnWidth, btnHeight);
            toMenuButton.setVisible(true);
    
        }

        Toolkit.getDefaultToolkit().sync();

        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.BOLD, 14));
        g.drawString("Score: " + gameState.getScore(), 10, 20);
    }

    private void createButtons() {
        restartButton = new JButton("Play again");
        restartButton.setFocusable(false); // Preventing button from stealing keyboard focus!
        restartButton.setVisible(false);   // Hiding it initially

        startButton = new JButton("GO!");
        startButton.setFocusable(false);
        startButton.setVisible(false);
        
        settingsButton = new JButton("Settings");
        settingsButton.setFocusable(false);
        settingsButton.setVisible(false);
        
        quitButton = new JButton("Quit");
        quitButton.setFocusable(false);
        quitButton.setVisible(false);

        toMenuButton = new JButton("Quit to Menu");
        toMenuButton.setFocusable(false);
        toMenuButton.setVisible(false);
        
        // Defining what happens when clicked
        restartButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                gameState.reset();
                restartButton.setVisible(false); // Hiding button again
                toMenuButton.setVisible(false);
                requestFocusInWindow(); // Making sure keyboard still works
            }
        });

        toMenuButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setButtonsInv();
                gameState.stop();
                restartButton.setVisible(false); // Hiding button again
                toMenuButton.setVisible(false);
                requestFocusInWindow(); // Making sure keyboard still works
            }
        });
        
        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setButtonsInv();
                gameState.start();
            }
        });
        settingsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setButtonsInv();
                //gameState.settings();
            }
        });
        quitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
        toMenuButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setButtonsInv();
                gameState.stop();
            }
        });
        this.add(startButton);
        this.add(settingsButton);
        this.add(quitButton);
        this.add(restartButton);
        this.add(toMenuButton);
    }
    private void setButtonsInv() {
        startButton.setVisible(false);
        restartButton.setVisible(false);
        settingsButton.setVisible(false);
        quitButton.setVisible(false);
        toMenuButton.setVisible(false);
    }
    
}
