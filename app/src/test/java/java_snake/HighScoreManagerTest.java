package java_snake;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.List;
import org.junit.jupiter.api.io.TempDir;
import java.nio.file.Path;

class HighScoreManagerTest {
    
    @TempDir
    Path tempDir;

    @Test
    void testMaintainsOnlyTopThreeScores() {
        
        Path tempFile = tempDir.resolve("test_highscores.txt");
        HighScoreManager manager = new HighScoreManager(tempFile.toString());
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