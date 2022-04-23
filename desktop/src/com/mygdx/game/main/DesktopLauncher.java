package com.mygdx.game.main;

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;

// Please note that on macOS your application needs to be started with the -XstartOnFirstThread JVM argument
public class DesktopLauncher extends Thread {
	//this is teh LibGDX thread
	boolean useGUI;
	DesktopLauncher(Boolean useGUI){
			this.useGUI = useGUI;
	}

	@Override
	public void run() {
		Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
		config.setForegroundFPS(60);
		config.setTitle("GolfSim");
		config.setWindowedMode(923,923);
		config.setResizable(false);
		if(useGUI){new WindowMain();}

		new Lwjgl3Application(new MainGame(), config);
	}
}
