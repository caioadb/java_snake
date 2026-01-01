package java_snake;

public class GameLoop implements Runnable {
    private final GameState gameState;
    private final GamePanel gamePanel;
    private boolean running = true;
    private final int targetTps;

    public GameLoop(GameState gameState, GamePanel gamePanel, int targetTps) {
        this.gameState = gameState;
        this.gamePanel = gamePanel;
        this.targetTps = targetTps;
    }

    public void stop() {
        running = false;
    }

    @Override
    public void run() {
        long nsPerTick = 1_000_000_000 / targetTps;
        long nextTickTime = System.nanoTime();
        
        while (running) {
            gameState.tick();
            gamePanel.repaint();
            
            nextTickTime += nsPerTick;
            long remaining = nextTickTime - System.nanoTime();
            
            // Sleep for most of the time, but wake up early to spin-wait for precision
            if (remaining > 1_000_000) { // If more than 1ms left
                try {
                    // Sleep until 1ms before target
                    Thread.sleep((remaining - 1_000_000) / 1_000_000);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
            
            // Busy-wait for the final precision
            while (System.nanoTime() < nextTickTime) {
                Thread.onSpinWait();
            }
        }
    }
}
