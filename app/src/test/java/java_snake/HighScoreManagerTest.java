package java_snake;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.List;

class HighScoreManagerTest {
    @Test
    void testMaintainsOnlyTopThreeScores() {
        HighScoreManager manager = new HighScoreManager();
        manager.addScore("AAA", 100);
        manager.addScore("BBB", 500);
        manager.addScore("CCC", 300);
        manager.addScore("DDD", 50);

        List<ScoreRecord> scores = manager.getScores();
        
        assertEquals(3, scores.size());
        assertEquals(500, scores.get(0).score());
        assertEquals("BBB", scores.get(0).initials());
    }
}