package java_snake;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

public class Sounds implements GameObserver {
    
    public Sounds(GameState gameState) {
    }

    @Override
    public void onGameState(GameState gameState) {
    }

    @Override
    public void playSound(GameState gameState, int choice) {
        play(choice);
    }

    public boolean play(int choice) {
        if(choice == 0) {
            try {
                AudioInputStream confirm = 
                AudioSystem.getAudioInputStream(
                    Sounds.class.getResource("/sfx/point.wav")
                );
                Clip clip = AudioSystem.getClip();
                clip.open(confirm);
                clip.start();
            } catch (Exception ex) {
                ex.printStackTrace();
                return false;
            }
        }
        else if(choice == 1) {
            try {
                AudioInputStream gotPoint = 
                AudioSystem.getAudioInputStream(
                    Sounds.class.getResource("/sfx/confirm.wav")
                );
                Clip clip = AudioSystem.getClip();
                clip.open(gotPoint);
                clip.start();
            } catch (Exception ex) {
                ex.printStackTrace();
                return false;
            }
        }
        else if(choice == 2) {
            try {
                AudioInputStream death = 
                AudioSystem.getAudioInputStream(
                    Sounds.class.getResource("/sfx/death.wav")
                );
                Clip clip = AudioSystem.getClip();
                clip.open(death);
                clip.start();
            } catch (Exception ex) {
                ex.printStackTrace();
                return false;
            }
        }
        return true;
    }

}
