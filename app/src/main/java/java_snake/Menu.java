package java_snake;

import javax.swing.JPanel;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Font;

public class Menu extends JPanel implements GameObserver {

    private JButton startButton;
    private JButton settingsButton;
    private JButton quitButton;
    private JButton restartButton;
    private JButton toMenuButton;

    private final GameState gameState;

    private int btnWidth = 110;
    private int btnHeight = 40;

    public Menu (GameState gameState, int tileSize) {
        
        this.gameState = gameState;

        setBounds(0,0,gameState.getWidth() * tileSize, gameState.getHeight() * tileSize);
        setLayout(null);
        
        int x = (getWidth() - btnWidth) / 2;
        int y = (getHeight() - btnHeight) / 2;
        
        restartButton = new JButton("Play again");
        restartButton.setBounds(x, y, btnWidth, btnHeight);
        restartButton.setFocusable(false); // Preventing button from stealing keyboard focus!
        restartButton.setVisible(false);   // Hiding it initially
        
        startButton = new JButton("GO!");
        startButton.setFocusable(false);
        startButton.setVisible(false);
        startButton.setBounds(x, y - 50, btnWidth, btnHeight);
            
            
        settingsButton = new JButton("Settings");
        settingsButton.setBounds(x, y, btnWidth, btnHeight);
        settingsButton.setFocusable(false);
        settingsButton.setVisible(false);
        
        quitButton = new JButton("Quit");
        quitButton.setBounds(x, y + 50, btnWidth, btnHeight);
        quitButton.setFocusable(false);
        quitButton.setVisible(false);

        toMenuButton = new JButton("Quit to Menu");
        toMenuButton.setBounds(x, y + 50, btnWidth, btnHeight);
        toMenuButton.setFocusable(false);
        toMenuButton.setVisible(false);
        
        // Defining what happens when clicked
        restartButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                gameState.reset();
                restartButton.setVisible(false); // Hiding button again
                toMenuButton.setVisible(false); // Making sure keyboard still works
            }
        });

        toMenuButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                gameState.stop();
                updateState();
            }
        });
        
        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                gameState.start();
                updateState();
            }
        });

        settingsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
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
                gameState.stop();
                updateState();
            }
        });

        this.add(startButton);
        this.add(settingsButton);
        this.add(quitButton);
        this.add(restartButton);
        this.add(toMenuButton);

    }
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        g.setColor(Color.WHITE);

        if (gameState.isOnMenu()) {
            g.setColor(Color.WHITE);
            g.setFont(new Font("Comic Sans MS", Font.BOLD, 24));
            g.drawString("SNAKE", getWidth() / 2 - 42, getHeight() / 4 + 60);
        } 
        else if (gameState.isGameOver()) {
            g.drawString("Game Over", getWidth() / 2 - 30, getHeight() / 2 - 40);
        }
    }

    @Override
    public void onGameState(GameState gameState) {
        updateState();
    }

    @Override
    public void playSound(GameState gameState, int choice) {
    }

    public void updateState() {
        
        setButtonsInv();
        
        if(gameState.isOnMenu()) {
            
            setOpaque(true);
            startButton.setVisible(true);
            settingsButton.setVisible(true);
            quitButton.setVisible(true);

        } else if(gameState.isGameOver()) {

            restartButton.setVisible(true);
            toMenuButton.setVisible(true);

        }
        
        repaint();
        
    }

    private void setButtonsInv() {

        this.setBackground(Color.BLACK);
        setOpaque(false);
        startButton.setVisible(false);
        restartButton.setVisible(false);
        settingsButton.setVisible(false);
        quitButton.setVisible(false);
        toMenuButton.setVisible(false);

    }

}
