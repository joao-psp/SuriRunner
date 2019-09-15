package com.cefetmg.surirunner.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.cefetmg.surirunner.SuriRunner;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
                config.width = 1260;
                config.height = 87;
                config.resizable = false;
		new LwjglApplication(new SuriRunner(), config);
	}
}
