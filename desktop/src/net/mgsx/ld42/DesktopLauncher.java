package net.mgsx.ld42;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.width = LD42.WINDOW_WIDTH;
		config.height = LD42.WINDOW_HEIGHT;
		new LwjglApplication(new LD42(), config);
	}
}
