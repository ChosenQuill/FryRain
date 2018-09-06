package com.suredroid.fryrain.desktop;

import com.badlogic.gdx.Files.FileType;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.suredroid.fryrain.Main;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.title = "FryRain - SureDroid";
		config.width = getWidth(3);
		config.height = getHeight(3);
		//config.resizable = false;
		config.addIcon("images/icon.png", FileType.Internal);
		new LwjglApplication(new Main(), config);
	}
	
	//16:9 Ratio
	public static int getWidth(int reduction) {
		if(reduction == -1) return 1600; //Default
		int output, max;
		max = LwjglApplicationConfiguration.getDesktopDisplayMode().width-(LwjglApplicationConfiguration.getDesktopDisplayMode().width%16);
		output=max;
		for(int i = 0; i < reduction; i++) {
			output-= 0.1*max;
		}
		return output;
	}
	public static int getHeight(int reduction) {
		if(reduction == -1) return 900; //Default
		int output, max;
		max = LwjglApplicationConfiguration.getDesktopDisplayMode().height-(LwjglApplicationConfiguration.getDesktopDisplayMode().height%9);
		output=max;
		for(int i = 0; i < reduction; i++) {
			output-= 0.1*max;
		}
		return output;
	}
}

//Gdx.graphics.getDisplayMode()
//LwjglApplicationConfiguration.getDesktopDisplayMode()