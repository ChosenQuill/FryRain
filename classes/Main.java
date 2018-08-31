package com.suredroid.fryrain;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;

public class Main extends Game {
	
	public SpriteBatch batch;
	public BitmapFont mainFont, font1, font2;
	
	@Override
	public void create () {
		batch = new SpriteBatch();
		
		//Font Setup
		FreeTypeFontGenerator normalGenerator = new FreeTypeFontGenerator(Gdx.files.internal("normal.otf"));
		FreeTypeFontGenerator boxyGenerator = new FreeTypeFontGenerator(Gdx.files.internal("boxy.ttf"));
		FreeTypeFontParameter parameter = new FreeTypeFontParameter();
		parameter.size = 35;
		font1 = normalGenerator.generateFont(parameter); // font size 12 pixels
		parameter.size = 25;
		font2 = normalGenerator.generateFont(parameter);
		parameter.size = 100;
		mainFont = boxyGenerator.generateFont(parameter);
		normalGenerator.dispose();
		boxyGenerator.dispose();
		font2.getData().markupEnabled = true;
		
		this.setScreen(new MenuScreen(this));
	}

	@Override
	public void render () {
		super.render();
	}
	
	@Override
	public void dispose () {
		batch.dispose();
		font2.dispose();
		mainFont.dispose();
		font1.dispose();
		
		getScreen().dispose();
	}
}