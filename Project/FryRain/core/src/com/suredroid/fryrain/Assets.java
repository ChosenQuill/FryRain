package com.suredroid.fryrain;

import java.util.HashMap;
import java.util.Map;

import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;

public class Assets {
	public static final AssetManager manager = new AssetManager();
	
	//Creates Fonts
	public static final AssetDescriptor<BitmapFont> 
			mainFont = new AssetDescriptor<BitmapFont>("fonts/boxy.fnt",BitmapFont.class), 
			font1 = new AssetDescriptor<BitmapFont>("fonts/font1.fnt",BitmapFont.class),
			font2 = new AssetDescriptor<BitmapFont>("fonts/font2.fnt",BitmapFont.class);
	
	public static final AssetDescriptor<Texture>
			fryImage = new AssetDescriptor<Texture>("images/fry.png",Texture.class),
			bucketImage = new AssetDescriptor<Texture>("images/bucket.png",Texture.class),
			grassImage = new AssetDescriptor<Texture>("images/grass.png",Texture.class),
			background = new AssetDescriptor<Texture>("images/background.jpg",Texture.class);
	
	public static final Map<Integer,AssetDescriptor<Sound>> crunch = new HashMap<Integer,AssetDescriptor<Sound>>();
	public static final AssetDescriptor<Sound>
			lifeLost = new AssetDescriptor<Sound>("audio/wrong.mp3",Sound.class),
			clapping = new AssetDescriptor<Sound>("audio/clapping.mp3",Sound.class);

	
	public static final AssetDescriptor<Music> music = new AssetDescriptor<Music>("audio/game-music.mp3",Music.class);
			
			
			
	
	public static void load() {
		
		//Loads Fonts
		manager.load(mainFont);
		manager.load(font1);
		manager.load(font2);
		
		//Loads Images
		manager.load(fryImage);
		manager.load(bucketImage);
		manager.load(grassImage);
		manager.load(background);
		
		//Loads Sounds
		for(int i = 0; i < 8; i++) {
			crunch.put(i, new AssetDescriptor<Sound>("audio/crunch/"+(i+1)+".wav",Sound.class));
			manager.load(crunch.get(i));
		}
		manager.load(lifeLost);
		manager.load(clapping);
		
		//Load Music
		manager.load(music);
	}
	public static void dispose() {
		manager.dispose();
	}
}

/*

Map<String, AssetDescriptor>

for(String s:new String[]{"fry","bucket","grass"}){
map.put(s, new AssetWhatever("images/"+s+".png"));
}

*/

/*		Old Import Code

		mainFont = new BitmapFont(Gdx.files.internal("fonts/boxy.fnt"));
		font1 = new BitmapFont(Gdx.files.internal("fonts/font1.fnt"));
		font2 = new BitmapFont(Gdx.files.internal("fonts/font2.fnt"));
		font2.getData().markupEnabled = true;
		
		this.setScreen(new MenuScreen(this));
		
				// Load the images
		fryImage = new Texture(Gdx.files.internal("images/fry.png"));
		bucketImage = new Texture(Gdx.files.internal("images/bucket.png"));
		grassImage = new Texture(Gdx.files.internal("images/grass.png"));
		background = new Texture(Gdx.files.internal("images/background.jpg"));
		
		// Load the crunch sounds
		crunchSound = new Sound[8];
		for (int i = 0; i < 8; i++) {
			crunchSound[i] = Gdx.audio.newSound(Gdx.files.internal("audio/crunch/" + (i + 1) + ".wav"));
		}

		// Loads LifeLost sound
		lifeLost = Gdx.audio.newSound(Gdx.files.internal("audio/wrong.mp3"));
	
		clapping = Gdx.audio.newSound(Gdx.files.internal("audio/clapping.mp3"));
	
		// Loads the Music
		backgroundMusic = Gdx.audio.newMusic(Gdx.files.internal("audio/game-music.mp3"));
 */