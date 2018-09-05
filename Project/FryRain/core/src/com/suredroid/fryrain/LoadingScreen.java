package com.suredroid.fryrain;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
	
public class LoadingScreen implements Screen {
	
	final Main game;

	public LoadingScreen(final Main game) {
		this.game = game;
		game.batch = new SpriteBatch();
		Assets.load();
		Assets.manager.finishLoadingAsset("fonts/boxy.fnt");
		if(Assets.manager.isLoaded("fonts/boxy.fnt")) {
			System.out.println("Font Loaded");
			System.out.println(Assets.mainFont + "");
		}
		game.mainFont = Assets.manager.get(Assets.mainFont);
		game.mainFont.setColor(Color.BLACK);
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(1, 1, 1, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		game.batch.begin();
		game.mainFont.draw(game.batch, "Loading...", 100, 200);
		game.batch.end();
		
		if(Assets.manager.update()){
			exit();
		}
		
	}
	
	public void exit() {
		game.mainFont.setColor(Color.WHITE);
		game.font1 = Assets.manager.get(Assets.font1);
		game.font2 = Assets.manager.get(Assets.font2);
		game.font2.getData().markupEnabled = true;
		game.setScreen(new MenuScreen(game));
		dispose();
	}
	
	@Override
	public void show() {
		// TODO Auto-generated method stub

	}
	
	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub

	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub

	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub

	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub

	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub

	}

}
