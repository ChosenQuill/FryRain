package com.suredroid.fryrain;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
	
public class LoadingScreen implements Screen {
	
	final Main game;

	public LoadingScreen(final Main game) {
		this.game = game;
		game.batch = new SpriteBatch();
		Assets.load();
		game.mainFont = Assets.manager.get(Assets.mainFont);
		game.font1 = Assets.manager.get(Assets.font1);
		game.font2 = Assets.manager.get(Assets.font2);
		game.font2.getData().markupEnabled = true;
		Assets.manager.finishLoadingAsset("fonts/boxy.fnt");
	}

	@Override
	public void show() {
		// TODO Auto-generated method stub

	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(1, 1, 1, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

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
