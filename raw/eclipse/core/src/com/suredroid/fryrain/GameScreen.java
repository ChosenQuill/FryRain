package com.suredroid.fryrain;

import java.util.Iterator;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;

public class GameScreen implements Screen {

	final Main game;
	Texture fryImage, bucketImage, grassImage, background;
	Sound[] crunchSound;
	Sound lifeLost;
	Music backgroundMusic;
	int lastSound;

	long timeLog;
	final int activeSet = 2, cooldownSet = 5;
	final double multiplierSet = 1.8;
	Boolean powerUp = false;
	String powerUpStatus;

	Rectangle bucket;
	Array<Rectangle> fries;
	long lastDropTime;
	public static int score;
	int lives = 3;
	double multiplier = 1;
	final int speed = 600;

	public GameScreen(final Main game) {
		this.game = game;
		
		//Loads the image
		fryImage = Assets.manager.get(Assets.fryImage);
		bucketImage = Assets.manager.get(Assets.bucketImage);
		grassImage = Assets.manager.get(Assets.grassImage);
		background = Assets.manager.get(Assets.background);
		
		// Load the crunch sounds
		crunchSound = new Sound[8];
		for (int i = 0; i < 8; i++) {
			crunchSound[i] = Assets.manager.get(Assets.crunch.get(i));
		}

		// Loads LifeLost sound
		lifeLost = Assets.manager.get(Assets.lifeLost);

		// Loads the Music
		backgroundMusic = Assets.manager.get(Assets.music);

		// Starts Playing the music.
		backgroundMusic.setLooping(true);
		backgroundMusic.setVolume(0.6f);
		backgroundMusic.play();

		// Creates bucket
		bucket = new Rectangle();
		bucket.x = 1600 / 2 + 166 / 2;
		bucket.y = 20;
		bucket.width = 166;
		bucket.height = 150; // Original 200

		// Creates Fry
		fries = new Array<Rectangle>();
		spawnFry();
		
		timeLog = System.currentTimeMillis() - 1000 * cooldownSet;
		powerUpStatus = "PowerUp [#008000]Available!";
	}

	@Override
	public void render(float delta) {
		// Sets screen orange
		Gdx.gl.glClearColor(1, 0.647f, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		// Tells camera to update its matrices.
		game.camera.update();

		// tell the SpriteBatch to render in the
		// coordinate system specified by the camera.
		game.batch.setProjectionMatrix(game.camera.combined);

		// begin a new batch and draw the bucket and
		// all fries
		game.batch.begin();
		game.batch.draw(background, 0, 0);
		game.batch.draw(bucketImage, bucket.x, bucket.y);
		for (Rectangle fry : fries) {
			game.batch.draw(fryImage, fry.x, fry.y);
		}
		game.batch.draw(grassImage, 0, 0);
		game.font2.draw(game.batch, "Fries Collected: " + score, 20, 900 - 20);
		game.font2.draw(game.batch, powerUpStatus, 1600 / 2 - 60, 900 - 20);
		game.font2.draw(game.batch, "Lives Left: " + lives, 1600 - 140, 900 - 20);
		game.batch.end();


		// Powerup Stuff
		if (powerUp == false && (System.currentTimeMillis() - timeLog) / 1000 >= cooldownSet) {

			powerUpStatus = "PowerUp [#008000]Available!";
			if (Gdx.input.isKeyPressed(Input.Keys.SPACE)) {
				powerUp = true;
				timeLog = System.currentTimeMillis();
			}
		} else if (powerUp == false) {
			powerUpStatus = "PowerUp [#000080]CoolDown[WHITE]: " + format(Double.valueOf(cooldownSet)
					- ((Long.valueOf(System.currentTimeMillis() - timeLog)).doubleValue() / 1000));
		}

		if (powerUp == true) {
			multiplier = multiplierSet;
			if ((System.currentTimeMillis() - timeLog) / 1000 >= activeSet) {
				multiplier = 1;
				powerUp = false;
				timeLog = System.currentTimeMillis();
			}
			powerUpStatus = "PowerUp [#8b0000]Active[WHITE]: " + format(Double.valueOf(activeSet)
					- ((Long.valueOf(System.currentTimeMillis() - timeLog)).doubleValue() / 1000));
		}
		
		/*
		if (Gdx.input.isTouched()) {
			Vector3 touchPos = new Vector3();
			touchPos.set(Gdx.input.getX(), Gdx.input.getY(), 0);
			camera.unproject(touchPos);
			System.out.println(touchPos.x + " " + (bucket.x + bucket.width/2));
			if(touchPos.x > bucket.x + bucket.width/2) {
				if(bucket.x + speed * multiplier * Gdx.graphics.getDeltaTime() >= touchPos.x)
					bucket.x = touchPos.x-bucket.width/2;
				else 
					bucket.x += speed * multiplier * Gdx.graphics.getDeltaTime();
			
			} else if(touchPos.x < bucket.x + bucket.width/2) {
				if(bucket.x - speed * multiplier * Gdx.graphics.getDeltaTime() <= touchPos.x)
					bucket.x = touchPos.x-bucket.width/2;
				else 
					bucket.x -= speed * multiplier * Gdx.graphics.getDeltaTime();
			}
		}
		*/
		
		// Keyboard Movement
		if (Gdx.input.isKeyPressed(Input.Keys.LEFT))
			bucket.x -= speed * multiplier * Gdx.graphics.getDeltaTime();
		if (Gdx.input.isKeyPressed(Input.Keys.RIGHT))
			bucket.x += speed * multiplier * Gdx.graphics.getDeltaTime();
		if (bucket.x < 0)
			bucket.x = 0;
		if (bucket.x > 1600 - 166)
			bucket.x = 1600 - 166;

		// Spawns fry every seconds.
		if (TimeUtils.nanoTime() - lastDropTime > 1000000000)
			spawnFry();

		// Moves Fry 200p/second
		for (Iterator<Rectangle> iter = fries.iterator(); iter.hasNext();) {
			Rectangle fry = iter.next();
			fry.y -= 200 * Gdx.graphics.getDeltaTime();
			if (fry.overlaps(bucket)) {
				score++;
				crunchSound[lastSound].stop();
				lastSound = MathUtils.random(0, 7);
				crunchSound[lastSound].play();
				iter.remove();
			} 
			else if (fry.y + 30 < 0) {
				iter.remove();
				lives--;
				lifeLost.stop();
				lifeLost.play(0.4f);
				if (lives <= 0) {
					exit(new EndScreen(game));
				}
			}
		}

	}

	private String format(double input) {
		String value = String.valueOf(input);
		StringBuilder sb = new StringBuilder();
		sb.append(value.substring(0, value.indexOf(".")));
		switch (value.length() - 2) {
		case 0:
			sb.append("00");
			break;
		case 1:
			sb.append(value.substring(value.indexOf("."), value.indexOf(".") + 1)+"0");
			break;
		default:
			sb.append(value.substring(value.indexOf("."), value.indexOf(".") + 2));
			break;
		}
		return sb.toString();

	}

	private void spawnFry() {
		Rectangle fry = new Rectangle();
		fry.x = MathUtils.random(0, 1600 - 64);
		fry.y = 900;
		fry.width = 20;
		fry.height = 180;
		fries.add(fry);
		lastDropTime = TimeUtils.nanoTime();
	}
	
	public void exit(Screen newScreen) {
		backgroundMusic.stop();
		game.setScreen(newScreen);
		dispose();
	}
	
	@Override
	public void dispose() {
		
	}

	@Override
	public void show() {
		// TODO Auto-generated method stub

	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub

	}

	@Override
	public void resize(int width, int height) {
		
	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub

	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub

	}

}