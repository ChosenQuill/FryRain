package com.suredroid.fryrain;

import java.util.Iterator;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;

public class GameScreen implements Screen {
	
	final Main game;
	
	OrthographicCamera camera;
	Texture fryImage, bucketImage, grassImage;
	Sound[] crunchSound;
	Sound lifeLost;
	Music backgroundMusic;
	int lastSound;
	
	Rectangle bucket;
	Array<Rectangle> fries;
	long lastDropTime;
	public static int score;
	int lives = 3;
	
	public GameScreen(final Main game) {
		this.game = game;
		
		camera = new OrthographicCamera();
		camera.setToOrtho(false, 1600, 900);
		
		//Load the images
		fryImage = new Texture(Gdx.files.internal("fry.png"));
		bucketImage = new Texture(Gdx.files.internal("bucket.png"));
		grassImage = new Texture(Gdx.files.internal("grass.png"));
		
		//Load the crunch sounds
		crunchSound = new Sound[8];
		for(int i = 0; i < 8; i++){
			crunchSound[i] = Gdx.audio.newSound(Gdx.files.internal("crunch/"+(i+1)+".wav"));
		}
		
		//Loads LifeLost sound
		lifeLost = Gdx.audio.newSound(Gdx.files.internal("wrong.mp3"));
		
		//Loads the Music
		backgroundMusic = Gdx.audio.newMusic(Gdx.files.internal("game-music.mp3"));
		
		//Starts Playing the music.
		backgroundMusic.setLooping(true);
		backgroundMusic.setVolume(0.6f);
		backgroundMusic.play();
		
		//Creates bucket
		bucket = new Rectangle();
		bucket.x = 1600/2 + 166/2;
		bucket.y = 20;
		bucket.width = 166;
		bucket.height = 150; //Original 200
		
		//Creates Fry
		fries = new Array<Rectangle>();
		spawnFry();
		
		game.font.getData().setScale(1.5f);
	}

	@Override
	public void render(float delta) {
		//Sets screen orange
		Gdx.gl.glClearColor(1, 0.647f, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		//Tells camera to update its matrices.
		camera.update();
		
		// tell the SpriteBatch to render in the
		// coordinate system specified by the camera.
		game.batch.setProjectionMatrix(camera.combined);
		
		// begin a new batch and draw the bucket and
		// all fries
		game.batch.begin();
		game.batch.draw(bucketImage, bucket.x, bucket.y);
		for(Rectangle fry: fries) {
		      game.batch.draw(fryImage, fry.x, fry.y);
		   }
		game.batch.draw(grassImage,0,0);
		game.font.draw(game.batch, "Fries Collected: " + score, 20, 900-20);
		game.font.draw(game.batch, "Lives Left: " + lives, 1600-140, 900-20);
		game.batch.end();
		
		//Mouse Movement
		if(Gdx.input.isTouched()) {
		      Vector3 touchPos = new Vector3();
		      touchPos.set(Gdx.input.getX(), Gdx.input.getY(), 0);
		      camera.unproject(touchPos);
		      bucket.x = touchPos.x - 166 / 2;
		   }
		
		//Keyboard Movement
		if(Gdx.input.isKeyPressed(Input.Keys.LEFT)) bucket.x -= 600 * Gdx.graphics.getDeltaTime();
		if(Gdx.input.isKeyPressed(Input.Keys.RIGHT)) bucket.x += 600 * Gdx.graphics.getDeltaTime();
		if(bucket.x < 0) bucket.x = 0;
		if(bucket.x > 1600-166) bucket.x = 1600-166;
		
		//Spawns fry every seconds.
		if(TimeUtils.nanoTime() - lastDropTime > 1000000000) spawnFry();
		
		//Moves Raindrop 200p/second
		for (Iterator<Rectangle> iter = fries.iterator(); iter.hasNext(); ) {
		      Rectangle fry = iter.next();
		      fry.y -= 200 * Gdx.graphics.getDeltaTime();
		      if(fry.y + 30 < 0) {
		    	  iter.remove();
		    	  lives--;
		    	  lifeLost.stop();
		    	  lifeLost.play(0.4f);
		    	  if(lives <= 0 ) {
		    		  game.setScreen(new EndScreen(game));
		    		  dispose();
		    	  }
		      }
		      if(fry.overlaps(bucket)) {
		    	  score++;
		    	  crunchSound[lastSound].stop();
		    	  lastSound = MathUtils.random(0,7);
		          crunchSound[lastSound].play();
		          iter.remove();
		       }
		   }
		
	}
	
	private void spawnFry() {
	      Rectangle fry = new Rectangle();
	      fry.x = MathUtils.random(0, 1600-64);
	      fry.y = 900;
	      fry.width = 20;
	      fry.height = 180;
	      fries.add(fry);
	      lastDropTime = TimeUtils.nanoTime();
	   }

	@Override
	public void dispose () {
		fryImage.dispose();
		bucketImage.dispose();
		backgroundMusic.dispose();
		for(int i = 0; i < 8; i++){
			crunchSound[i].dispose();
		}
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

}
