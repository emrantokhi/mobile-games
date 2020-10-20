package com.mycompany.a3;


import java.io.InputStream;

import com.codename1.media.Media;
import com.codename1.media.MediaManager;
import com.codename1.ui.Display;

public class Sound {
	private Media m;
	
	public Sound(String filename) {
		boolean find = true;
		//If the show() method has  not called yet return an error and close the app
		if(Display.getInstance().getCurrent() == null) {
			System.out.println("Error: Create sound objects after show() is called.");
			//Display.getInstance().exitApplication();
			System.exit(0);
		}
		/*//Try and find the file for media manager to create the playable sound file
		while(find) {
			try {
				InputStream is = Display.getInstance().getResourceAsStream(getClass(), "/" + filename);
				m = MediaManager.createMedia(is, "audio/mp3");
				find = false;
			} catch(Exception e) {
				e.printStackTrace();
				System.out.println("Sound error");
			}
		}*/
		InputStream is;
		try {
			while(m == null) {
				is = Display.getInstance().getResourceAsStream(getClass(), "/" + filename);
				m = MediaManager.createMedia(is, "audio/mp3");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void play(boolean sound) {
		if(sound) {
			//start playing the sound, start at 0 every time it's played
			m.setTime(0);
			m.play();
		}
	}

}
