package com.suredroid.fryrain;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;

public class MenuScreen implements Screen {
	
	final Main game;
	
	// camera;
	
	private GlyphLayout layout;
	

	
	public MenuScreen(final Main game) {
		this.game = game;

		//camera = new OrthographicCamera();
		//camera.setToOrtho(false, 1600, 900);
		
		layout = new GlyphLayout(game.mainFont, "Welcome to FryRain!");
		
	}
	
	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0, 0, 0.2f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		// Tells camera to update its matrices.
		game.camera.update();

		// tell the SpriteBatch to render in the
		// coordinate system specified by the camera.
		game.batch.setProjectionMatrix(game.camera.combined);

		game.batch.begin();
		game.mainFont.draw(game.batch, layout, 1600/2-layout.width/2, 900/2+layout.height + 20);
		game.font1.draw(game.batch, "Prevent the fries from falling on the ground!", 100, 200);
		game.font1.draw(game.batch, "Controls: To move, use the left key and right key. Space is a speed boost powerup.", 100, 150);
		game.font1.draw(game.batch, "Click anywhere to begin!", 100, 100);
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
