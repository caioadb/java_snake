package java_snake;

public record ScoreRecord(String initials, int score) implements Comparable<ScoreRecord> {
    @Override
    public int compareTo(ScoreRecord other) {
        return Integer.compare(other.score, this.score);
    }
}