package com.mygdx.game.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

import mygame.MyGame;


public class DesktopLauncher {
    public static void main(String[] arg) {
        LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();

        config.resizable = false;
        config.width = MyGame.WIDTH;
        config.height = MyGame.HEIGHT;

        new LwjglApplication(new MyGame(), config);
        //test
//		new LwjglApplication(new MyGame(), config);
    }
}
