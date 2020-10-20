package com.mycompany.a3;

import java.io.InputStream;

import com.codename1.media.Media;
import com.codename1.media.MediaManager;
import com.codename1.ui.Display;

public class BGSound implements Runnable {
	private Media m;
	
	public BGSound(String filename) {
		boolean find = true;
		//If the show() method has  not called yet return an error and close the app
		if(Display.getInstance().getCurrent() == null) {
			System.out.println("Error: Create sound objects after show() is called.");
			Display.getInstance().exitApplication();
		}
		//Try and find the file for media manager to create the playable sound file
		while(find) {
			try {
				InputStream is = Display.getInstance().getResourceAsStream(getClass(), "/" + filename);
				m = MediaManager.createMedia(is, "audio/mp3", this);
				find = false;
			} catch(Exception e) {
				e.printStackTrace();
				System.out.print("BG sound error");
			}
		}
	}
	
	public void play(boolean sound) {
		try {
			if(sound)
				m.play();
			else
				m.pause();
		} catch(Exception e) {
			e.printStackTrace();
			System.out.println("Restart me. After a 2nd or 3rd restart should fix me. Didn't find file possibly?");
		}
	}
	
	public void run() {
		m.setTime(0);
		m.play();
	}

}
