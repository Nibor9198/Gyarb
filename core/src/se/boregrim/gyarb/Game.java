package se.boregrim.gyarb;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import se.boregrim.gyarb.screens.MainMenu;

import java.util.HashMap;

public class Game extends com.badlogic.gdx.Game {
	public SpriteBatch batch;
	Texture img;
	public HashMap<String, Screen> screens;
	public Assets assets;
	AssetManager manager;

	public BitmapFont font;

	@Override
	public void create () {
		//Initiating variables
		batch = new SpriteBatch();
		screens = new HashMap<String, Screen>();
		assets = new Assets();
		manager = assets.getAssetManager();
		font = new BitmapFont();


		manager.finishLoading();
		MainMenu main = new MainMenu(this);
		screens.put("main", main);
		setScreen(main);


			}

	@Override
	public void render () {
		super.render();
	}
	
	@Override
	public void dispose () {
		batch.dispose();
		assets.dispose();
	}
}
