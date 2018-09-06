package com.suredroid.fryrain;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

public class Main extends Game {

	public SpriteBatch batch;
	public BitmapFont mainFont, font1, font2;

	public Viewport viewport;
	public OrthographicCamera camera;

	public int width, height;

	@Override
	public void create() {
		width = Gdx.graphics.getWidth();
		height = Gdx.graphics.getHeight();

		camera = new OrthographicCamera();
		// The camera is always on the center of the screen.Ex
		// OrthographicCamera(100,100) would have 50 for top and -50 for bottom.
		camera.setToOrtho(false, 1600, 900);
		// camera.translate(x, y); You can use this method to displace the camera.
		// camera.setToOrtho is a convenience method instead of doing this.
		viewport = new StretchViewport(1600, 900, camera);
		viewport.update(width, height);
		this.setScreen(new LoadingScreen(this));

		// Other Camera Stuff
		// Set Camera.zoom to set the zoom.
		// Camera.position.set sets the camera's position.
		// Whenever you change the camera position, you have to do camera.update();
		// batch.setprojectionmatrix creates the matrix to draw on.
	}

	@Override
	public void render() {
		super.render();
	}

	@Override
	public void dispose() {
		super.dispose();
		Assets.dispose();
	}

	@Override
	public void resize(int newWidth, int newHeight) {
		if (newWidth != 0 || newHeight != 0) {
			if (Math.abs(newWidth / 16 - width / 16) >= Math.abs(newHeight / 9 - height / 9)) {
				width = newWidth;
				height = (int) Math.round(Double.valueOf(newWidth) * (9.0 / 16.0));
			} else {
				height = newHeight;
				width = (int) Math.round(Double.valueOf(newHeight) * (16.0 / 9.0));
			}
		}
		
		Gdx.graphics.setWindowedMode(width, height);
		viewport.update(width, height);
	}

	/*
	 * Automatically disposes previous screen
	 * 
	 * @Override public void setScreen(Screen screen) { Screen oldScreen =
	 * this.screen; super.setScreen(screen); if(oldScreen != null)
	 * oldScreen.dispose(); }
	 */
}