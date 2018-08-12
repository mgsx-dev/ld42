package net.mgsx.ld42.client;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.backends.gwt.GwtApplication;
import com.badlogic.gdx.backends.gwt.GwtApplicationConfiguration;

import net.mgsx.ld42.LD42;

public class HtmlLauncher extends GwtApplication {

        @Override
        public GwtApplicationConfiguration getConfig () {
                return new GwtApplicationConfiguration(LD42.WINDOW_WIDTH, LD42.WINDOW_HEIGHT);
        }

        @Override
        public ApplicationListener createApplicationListener () {
                return new LD42();
        }
}