package java_snake;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

public class Sounds implements GameObserver {
    
    public Sounds(GameState gameState) {
    }

    @Override
    public void playSound(GameState gameState, int choice) {
        switch(choice) {
            case 0 -> playPoint();
            case 1 -> playConfirm();
            case 2 -> playDeath();
        }
    }

    public boolean playPoint()   { play("/sfx/point.wav"); return true;}
    public boolean playConfirm() { play("/sfx/confirm.wav"); return true;}
    public boolean playDeath()   { play("/sfx/death.wav"); return true;}

    public void play(String choice) {
        try {
            AudioInputStream confirm = 
            AudioSystem.getAudioInputStream(
            Sounds.class.getResource(choice)
            );
            Clip clip = AudioSystem.getClip();
            clip.open(confirm);
            clip.start();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

}
