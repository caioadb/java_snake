package java_snake;

import javax.swing.SwingUtilities;
import java.util.concurrent.locks.LockSupport;

public class GameLoop implements Runnable {
    private final GameState gameState;
    private final GamePanel gamePanel;
    private volatile boolean running = true;
    private final long nsPerTick;
    private final int expectedMs;
    
    // Diagnostics
    private long lastTickTime = 0;
    private long[] tickIntervals = new long[100];
    private int tickIndex = 0;
    private int tickCount = 0;

    public GameLoop(GameState gameState, GamePanel gamePanel, int targetTps) {
        this.gameState = gameState;
        this.gamePanel = gamePanel;
        this.nsPerTick = 1_000_000_000L / targetTps;
        this.expectedMs = 1000 / targetTps;
    }

    public void start() {
        Thread t = new Thread(this, "GameLoop");
        t.setPriority(Thread.MAX_PRIORITY);
        t.start();
    }

    public void stop() {
        running = false;
    }

    @Override
    public void run() {
        System.out.println("Starting game loop with " + expectedMs + "ms interval...");
        System.out.println("Diagnostics will print every 50 ticks");
        
        long nextTickTime = System.nanoTime();
        
        while (running) {
            // Wait for next tick time using spin-wait for precision
            while (System.nanoTime() < nextTickTime) {
                long remaining = nextTickTime - System.nanoTime();
                if (remaining > 10_000_000) { // More than 10ms remaining
                    // Use parkNanos for coarse waiting
                    LockSupport.parkNanos(remaining - 10_000_000);
                } else if (remaining > 0) {
                    // Spin-wait for final 10ms for precision
                    Thread.onSpinWait();
                }
            }
            
            long now = System.nanoTime();
            
            // Record diagnostics
            if (lastTickTime != 0) {
                long intervalMs = (now - lastTickTime) / 1_000_000;
                tickIntervals[tickIndex] = intervalMs;
                tickIndex = (tickIndex + 1) % tickIntervals.length;
                tickCount++;
                
                if (tickCount % 50 == 0) {
                    printDiagnostics();
                }
            }
            lastTickTime = now;
            
            // Execute tick
            gameState.tick();
            SwingUtilities.invokeLater(gamePanel::repaint);
            nextTickTime += nsPerTick;
            
            // If we fell behind, reset
            if (nextTickTime < now) {
                nextTickTime = now + nsPerTick;
            }
        }
    }
    
    private void printDiagnostics() {
        int count = Math.min(tickCount, tickIntervals.length);
        long sum = 0;
        long min = Long.MAX_VALUE;
        long max = Long.MIN_VALUE;
        int outliers = 0;
        
        StringBuilder sb = new StringBuilder();
        sb.append("Last ").append(count).append(" intervals: [");
        
        for (int i = 0; i < count; i++) {
            long interval = tickIntervals[i];
            sum += interval;
            min = Math.min(min, interval);
            max = Math.max(max, interval);
            
            if (Math.abs(interval - expectedMs) > expectedMs * 0.5) {
                outliers++;
            }
            
            if (i < 20) {
                sb.append(interval);
                if (i < 19 && i < count - 1) sb.append(", ");
            }
        }
        if (count > 20) sb.append("...");
        sb.append("]");
        
        double avg = (double) sum / count;
        double variance = 0;
        for (int i = 0; i < count; i++) {
            variance += Math.pow(tickIntervals[i] - avg, 2);
        }
        double stdDev = Math.sqrt(variance / count);
        
        System.out.println("\n=== TICK DIAGNOSTICS ===");
        System.out.printf("Expected interval: %dms%n", expectedMs);
        System.out.printf("Actual: avg=%.1fms, min=%dms, max=%dms, stdDev=%.1fms%n", 
                          avg, min, max, stdDev);
        System.out.printf("Outliers (>50%% off): %d/%d (%.1f%%)%n", 
                          outliers, count, 100.0 * outliers / count);
        System.out.println(sb.toString());
        System.out.println("========================\n");
    }
}
