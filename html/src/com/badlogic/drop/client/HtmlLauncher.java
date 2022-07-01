package com.badlogic.drop.client;

import com.badlogic.drop.GameScreen;
import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.backends.gwt.GwtApplication;
import com.badlogic.gdx.backends.gwt.GwtApplicationConfiguration;

public class HtmlLauncher extends GwtApplication {

        @Override
        public GwtApplicationConfiguration getConfig () {
                return new GwtApplicationConfiguration(800,480);
        }

        @Override
        public ApplicationListener createApplicationListener () {
                return new GameScreen();
        }
}