package examples;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.IOException;
import java.net.URL;

/**
 * Simple sound helper that loads WAV resources from the classpath and plays
 * them.
 * Place `eat.wav` and `gameover.wav` in `src/main/resources` so they end up on
 * the classpath.
 */
public final class SoundEffects {
  private static Clip eatClip;
  private static Clip gameOverClip;

  static {
    eatClip = loadClip("/eat.wav");
    gameOverClip = loadClip("/gameover.wav");
  }

  private SoundEffects() {
    // utility
  }

  private static Clip loadClip(String resourcePath) {
    try {
      URL url = SoundEffects.class.getResource(resourcePath);
      if (url == null) {
        System.err.println("Sound resource not found: " + resourcePath);
        return null;
      }
      AudioInputStream ais = AudioSystem.getAudioInputStream(url);
      Clip clip = AudioSystem.getClip();
      clip.open(ais);
      return clip;
    } catch (UnsupportedAudioFileException e) {
      System.err.println("Unsupported audio file: " + resourcePath + " -> " + e.getMessage());
    } catch (LineUnavailableException e) {
      System.err.println("Audio line unavailable for: " + resourcePath + " -> " + e.getMessage());
    } catch (IOException e) {
      System.err.println("I/O error loading sound: " + resourcePath + " -> " + e.getMessage());
    } catch (Exception e) {
      System.err.println("Unexpected error loading sound: " + resourcePath + " -> " + e.getMessage());
    }
    return null;
  }

  private static void playClip(Clip clip) {
    if (clip == null)
      return;
    // Play asynchronously so we don't block the Swing thread
    new Thread(() -> {
      try {
        if (clip.isRunning()) {
          clip.stop();
        }
        clip.setFramePosition(0);
        clip.start();
      } catch (Exception e) {
        // swallow - sound is optional
      }
    }, "SoundPlayer").start();
  }

  public static void playEat() {
    playClip(eatClip);
  }

  public static void playGameOver() {
    playClip(gameOverClip);
  }
}
