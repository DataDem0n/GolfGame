package com.mygdx.game.main;

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import com.mygdx.game.main.MainGame;
import physics.sytem.EulerSolver;
import physics.sytem.GameEngine;

import javax.xml.crypto.Data;
import java.util.function.BiFunction;

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
		config.setWindowedMode(900,900);
		config.setResizable(false);
		if(useGUI){new WindowMain();}

		new Lwjgl3Application(new MainGame(), config);
	}
}
