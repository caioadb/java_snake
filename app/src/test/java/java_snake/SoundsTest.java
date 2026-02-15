package java_snake;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.JButton;

public class SoundsTest extends SnakeTest {

    @Test
    void testMenuSounds() {

        GameState game = new GameState(20, 20);
        JButton startButton = new JButton();
        
        assertTrue(game.isOnMenu());
        startButton.addActionListener(e -> game.start());
        startButton.doClick();
        assertTrue(sounds.play());

    }

    @Test
    void testPointSound() {
        testGrow();
        assertTrue(sounds.point());
    }

    @Test
    void testGameOverSound() {
        testSelfCollision();
        assertTrue(sounds.death());
    }

}
