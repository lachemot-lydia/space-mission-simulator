package engine.process;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Date;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

public class Music{
	
	private Clip clip;
	
	  public void play() {
	  
	       try {
	           File file=new File("src/ressources/musique.wav");
	           AudioInputStream audioIn=AudioSystem.getAudioInputStream(file);
	           clip=AudioSystem.getClip();
	           clip.open(audioIn);
	           clip.start();
	           clip.loop(clip.LOOP_CONTINUOUSLY);
	       }
		catch (LineUnavailableException | UnsupportedAudioFileException | IOException e) {
			throw new RuntimeException(e);
		}
	    }
	  
	  public void stop() {
	        if (clip!=null &&clip.isRunning()) {
	            clip.stop();
	            clip.close();
	        }
	    }
}
