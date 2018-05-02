package game_engine.event.actions.macro;

import java.io.File;

import game_engine.event.Action;
import javafx.scene.media.AudioClip;
import java.net.URL;

/**
 * 
 * @author Andy Nguyen, Ben Hubsch The purpose of this class is to allow in-game music to be played.
 *         This action plays a music file for a particular duration of time. This action is
 *         defined/instantiated within the authoring environment.
 *
 */
public class PlayMusicAction implements Action {
	private String myMusicFile;
	private double myDuration;

	/**
	 * initializes a PlayMusicAction with a music file under the assumption that the entire music file
	 * will be played
	 * 
	 * @param musicFile
	 */
	public PlayMusicAction(String musicFile) {
		this(musicFile, -1);
	}

	/**
	 * initializes a PlayMusicAction with a music file with the duration of time the musicFile will be
	 * played for
	 * 
	 * @param musicFile
	 * @param duration
	 */
	public PlayMusicAction(String musicFile, double duration) {
		myMusicFile = musicFile;
		myDuration = duration;
	}

	/**
	 * plays the music file for a given duration of time specified by the initial conditions set in the
	 * constructor
	 */
	@Override
	public void execute() {
		System.out.println("CLIP NAME: " + myMusicFile);
//		URL musicPath = getClass().getResource(myMusicFile);
//		System.out.println("CLIP PATH: " + musicPath.toString());
		AudioClip myClip = new AudioClip(myMusicFile);
		myClip.play();
		System.out.println("playing: " + myClip.getSource());
	}

}
