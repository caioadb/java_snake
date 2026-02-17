package java_snake;

public interface GameObserver {
    void onGameState(GameState state);
    void playSound(GameState state, int choice);
}
