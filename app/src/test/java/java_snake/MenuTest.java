package java_snake;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import javax.swing.JButton;

class MenuTest {

    @Test 
    void testMenuExists() {
        GameState game = new GameState(20, 20);

        assertTrue(game.isOnMenu());
    }

    @Test
    void testMenuStartWorks() {
        GameState game = new GameState(20, 20);
        JButton startButton = new JButton();

        assertTrue(game.isOnMenu());
        startButton.addActionListener(e -> game.start());
        assertTrue(game.isOnMenu());
        startButton.doClick();
        assertFalse(game.isOnMenu());
    }

    @Test
    void testMenuSettingsWorks() {
        GameState game = new GameState(20, 20);
        JButton settingsButton = new JButton();

        assertTrue(game.isOnMenu());
        settingsButton.addActionListener(e -> game.start());
        assertTrue(game.isOnMenu());
        settingsButton.doClick();
        assertFalse(game.isOnMenu());
        //assertTrue(game.isOnSettings());
    }
}