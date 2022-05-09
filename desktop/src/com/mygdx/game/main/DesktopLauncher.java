package com.mygdx.game.main;

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;

// Please note that on macOS your application needs to be started with the -XstartOnFirstThread JVM argument
public class DesktopLauncher extends Thread {
	//this is teh LibGDX thread
	@Override
	public void run() {
		Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
		config.setForegroundFPS(60);
		config.setTitle("GolfSim");
		config.setWindowedMode(950,950);
		config.setResizable(false);


		new Lwjgl3Application(new MainGame(), config);
	}
}
