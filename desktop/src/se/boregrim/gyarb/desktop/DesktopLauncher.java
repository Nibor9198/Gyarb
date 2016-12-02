package se.boregrim.gyarb.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import se.boregrim.gyarb.Game;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.foregroundFPS = 60;
		config.backgroundFPS = 60;
		config.width = 1000;
		config.height = 800;
		config.resizable = true;

		new LwjglApplication(new Game(), config);
	}
}
