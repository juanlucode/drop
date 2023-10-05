package com.badlogic.drop;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 The Game class is responsible for handling multiple screens and provides
 some helper methods for this purpose, alongside an implementation of
 ApplicationListener for you to use. Together, Screen and Game objects are
 used to create a simple and powerful structure for games.

 We will start by creating a Drop class, which extends Game and whose create()
 method will be the entry point to our game. Let’s take a look at some code:
 */

public class DropGame extends Game {
    public SpriteBatch batch;
    public BitmapFont font;


    @Override
    public void create() {
        batch = new SpriteBatch();
        font = new BitmapFont(); // use libGDX's default Arial font
        this.setScreen(new MainMenuScreen(this));
    }

    @Override
    public void render(){
        super.render();
    }

    @Override
    public void dispose(){
        batch.dispose();
        font.dispose();
    }
}
