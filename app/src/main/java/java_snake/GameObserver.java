package java_snake;

public interface GameObserver {
    default void onGameState(GameState state) {}
    default void playSound(GameState state, int choice) {}
    default void onGameOver(GameState state, int score) {}
}
