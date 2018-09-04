package com.suredroid.fryrain;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Main extends Game {
	
	public SpriteBatch batch;
	public BitmapFont mainFont, font1, font2;
	
	@Override
	public void create () {
		this.setScreen(new LoadingScreen(this));
		//this.setScreen(new MenuScreen(this));
	}

	@Override
	public void render () {
		super.render();
	}
	
	@Override
	public void dispose () {
		super.dispose();
		batch.dispose();
		font2.dispose();
		mainFont.dispose();
		font1.dispose();
		
		getScreen().dispose();
	}
}