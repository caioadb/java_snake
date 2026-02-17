package java_snake;

import java.io.*;
import java.nio.file.*;
import java.util.*;

public class HighScoreManager {
    private List<ScoreRecord> scores = new ArrayList<>();
    private final String FILE_NAME = "highscores.txt";

    public HighScoreManager() {
        load();
    }

    public boolean isHighScore(int currentScore) {
        if (scores.size() < 3) return true;
        return currentScore > scores.get(scores.size() - 1).score();
    }

    public void addScore(String name, int points) {
        String cleanName = name.length() > 3 ? name.substring(0, 3) : name;
        scores.add(new ScoreRecord(cleanName.toUpperCase(), points));
        Collections.sort(scores);
        if (scores.size() > 3) {
            scores = new ArrayList<>(scores.subList(0, 3));
        }
        save();
    }

    public List<ScoreRecord> getScores() {
        return scores;
    }

    private void save() {
        try (PrintWriter out = new PrintWriter(new FileWriter(FILE_NAME))) {
            for (ScoreRecord s : scores) {
                out.println(s.initials() + "," + s.score());
            }
        } catch (IOException ignored) {}
    }

    private void load() {
        scores.clear();
        try {
            Path path = Paths.get(FILE_NAME);
            if (Files.exists(path)) {
                List<String> lines = Files.readAllLines(path);
                for (String line : lines) {
                    String[] p = line.split(",");
                    if (p.length == 2) {
                        scores.add(new ScoreRecord(p[0], Integer.parseInt(p[1])));
                    }
                }
            }
        } catch (Exception ignored) {}

        while (scores.size() < 3) {
            scores.add(new ScoreRecord("---", 0));
        }
        Collections.sort(scores);
    }
}