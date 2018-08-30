package com.suredroid.fryrain;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;

public class MenuScreen implements Screen {
	
	final Main game;
	
	OrthographicCamera camera;
	
	public MenuScreen(final Main game) {
		this.game = game;

		camera = new OrthographicCamera();
		camera.setToOrtho(false, 1600, 900);
		
		game.font.getData().setScale(2f);
		
	}
	
	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0, 0, 0.2f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		camera.update();
		game.batch.setProjectionMatrix(camera.combined);

		game.batch.begin();
		game.font.draw(game.batch, "Welcome to FryRain!", 100, 250);
		game.font.draw(game.batch, "Prevent the fries from falling on the ground!", 100, 200);
		game.font.draw(game.batch, "Controls: Left Arrow Key is left. Right Arrow Key is right.", 100, 150);
		game.font.draw(game.batch, "Click anywhere to begin!", 100, 100);
		game.batch.end();

		if (Gdx.input.isTouched()) {
			game.setScreen(new GameScreen(game));
			dispose();
		}

	}
	
	
	@Override
	public void show() {
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
		
	}

	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub
		
	}

}
