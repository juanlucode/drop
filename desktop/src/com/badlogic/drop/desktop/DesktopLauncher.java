package com.badlogic.drop.desktop;

import com.badlogic.drop.DropGame;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;

public class DesktopLauncher {


   /*
   Configuration the Starter Classes
   set 800 x 480 window and set title to "DropGame"
    */
    public static void main(String[] arg){
        Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
        config.setTitle("DropGame");
        config.setWindowedMode(800, 480);
        config.useVsync(true);
        config.setForegroundFPS(60);
        new Lwjgl3Application(new DropGame(), config);
    }
}
