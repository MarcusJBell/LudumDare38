package com.gmail.sintinium.ludumdare38.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.badlogic.gdx.tools.texturepacker.TexturePacker;
import com.gmail.sintinium.ludumdare38.Game;

public class DesktopLauncher {
	public static void main (String[] arg) {
		packImages();
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.width = 1280;
		config.height = 720;
		new LwjglApplication(new Game(), config);
	}

	public static void packImages() {
		TexturePacker.Settings settings = new TexturePacker.Settings();
		settings.stripWhitespaceX = true;
		settings.stripWhitespaceY = true;
		TexturePacker.process(settings, ".", "assets/packed", "textures");
	}

}
