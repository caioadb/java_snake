package java_snake;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

import javax.swing.JButton;

public class SoundsTest extends SnakeTest {

    @Test
    void testMenuSounds() {

        GameState game = new GameState(20, 20);
        JButton startButton = new JButton();
        Sounds sounds = new Sounds(game);

        assertTrue(game.isOnMenu());
        startButton.addActionListener(e -> game.start());
        startButton.doClick();
        assertTrue(sounds.play(1));

    }
    
    @Test
    void testPointSound() {
        GameState game = new GameState(20, 20);
        JButton startButton = new JButton();
        Sounds sounds = new Sounds(game);

        assertTrue(game.isOnMenu());
        startButton.addActionListener(e -> game.start());
        startButton.doClick();
        testGrow();
        assertTrue(sounds.play(0));
    }

    @Test
    void testGameOverSound() {
        GameState game = new GameState(20, 20);
        JButton startButton = new JButton();
        Sounds sounds = new Sounds(game);

        assertTrue(game.isOnMenu());
        startButton.addActionListener(e -> game.start());
        startButton.doClick();
        testSelfCollision();
        assertTrue(sounds.play(3));
    }
    
}
