package resources;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

// TODO: Auto-generated Javadoc
/**
 * The Class BackgroundMusic.
 * @author BeMyPlayer2 Team 
 */
public class BackgroundMusic {
	
	/** The logger. */
	private static Logger logger = Logger.getLogger(BackgroundMusic.class.getName());
	/** The clip. */
	static Clip clip;
	/** The status. */
	static String status = "stop";
	/** The audio input stream. */
	static AudioInputStream audioInputStream;
	/** The instance. */
	private static BackgroundMusic instance;
	
	private static Map<String,String> songList = new HashMap<>();
	
	
	/**
	 * Gets the single instance of BackgroundMusic.
	 *
	 * @return single instance of BackgroundMusic
	 */
	public static BackgroundMusic getInstance(){
		if(instance == null) {
			instance = new BackgroundMusic();
			songList.put("loginpage.wav", "src/main/resources/audio/loginpage.wav");
		}
		return instance;
	}
	
	/**
	 * Instantiates a new background music.
	 */
	private BackgroundMusic() {}
	/**
	 * Play song.
	 */
	private void playSong(String name,boolean preloaded) {
		if(preloaded) {
			try {
				audioInputStream = AudioSystem.getAudioInputStream(new File("src/main/resources/audio/"+name+".wav"));
				clip = AudioSystem.getClip();
				clip.open(audioInputStream);
				clip.loop(Clip.LOOP_CONTINUOUSLY);
			}catch(Exception e) {
				logger.warning("Background Music FAILED to play");
			} 
		}
		else {
			try {
				System.out.println(songList.get(name));
				audioInputStream = AudioSystem.getAudioInputStream(new File(songList.get(name)));
				clip = AudioSystem.getClip();
				clip.open(audioInputStream);
				clip.loop(Clip.LOOP_CONTINUOUSLY);
			}catch(Exception e) {
				logger.warning("Background Music FAILED to play");
			} 
		}
		
	}
	public void addSong(String name,String path) {
		BackgroundMusic.songList.put(name,path);
	}
	public void switchSong(String name) {
		stopSong();
		status = "play";
		playSong(name,false);
	}
	/**
	 * Music.
	 */
	public void music() {
		if(status.contentEquals("play")) {
			logger.info("Background Music is stopped");
			stopSong();
			status = "stop";
		} else {
			logger.log(Level.FINE, "Background Music started to play");
			playSong("loginpage",true);
			status = "play";
		}
	}

	
	
	/**
	 * Stop song.
	 */
	private static void stopSong() {
		clip.stop();
		clip.close();
		status = "stop";
	}
	
	/**
	 * Checks if is playing.
	 *
	 * @return true, if is playing
	 */
	public static boolean isPlaying() {
		if(status.equals("play")) {
			return true;
		}else {
			return false;
		}
	}

	public static Map<String,String> getSongList() {
		return songList;
	}

	public static void setSongList(Map<String,String> songList) {
		BackgroundMusic.songList = songList;
	}
	
}
