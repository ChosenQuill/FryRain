package com.suredroid.fryrain;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;

public class EndScreen implements Screen {
	
	final Main game;
	OrthographicCamera camera;
	static int highScore = 0;
	String displayText;
	Sound clapping;
	private GlyphLayout layout;
	
	public EndScreen(final Main game){
		this.game = game;
		
		camera = new OrthographicCamera();
		camera.setToOrtho(false, 1600, 900);
		
		clapping = Assets.manager.get(Assets.clapping);
		clapping.play();
		
		
		if(highScore < GameScreen.score) {
			layout = new GlyphLayout(game.mainFont, "Congradulations!");
			displayText = "You now have a record of " + GameScreen.score + "! It's " + (GameScreen.score - highScore) + " better than your previous record!";
			highScore = GameScreen.score;
			GameScreen.score = 0;
		}  else {
			layout = new GlyphLayout(game.mainFont, "Well Done!");
			displayText = "You got a score of " + GameScreen.score + "!";
			GameScreen.score = 0;
		}
	}
	
	//GameScreen.friesGathered;
	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(1, 0.647f, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		camera.update();
		game.batch.setProjectionMatrix(camera.combined);

		game.batch.begin();
		game.mainFont.draw(game.batch, layout, 1600/2-layout.width/2, 900/2+layout.height + 20);
		game.font1.draw(game.batch, displayText, 100, 150);
		game.font1.draw(game.batch, "Click anywhere to restart!", 100, 100);
		game.batch.end();

		if (Gdx.input.isTouched()) {
			game.setScreen(new GameScreen(game));
			dispose();
		}

	}
	
	@Override
	public void dispose() {
		clapping.dispose();
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

}
