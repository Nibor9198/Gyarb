package se.boregrim.gyarb;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import se.boregrim.gyarb.screens.LoadingScreen;
import se.boregrim.gyarb.screens.MainMenu;

import java.util.HashMap;

public class Game extends com.badlogic.gdx.Game {
	public SpriteBatch batch;
	Texture img;
	public HashMap<String, Screen> screens;
	public Assets assets;
	AssetManager manager;

	public BitmapFont font;
	public FitViewport viewport;


	@Override
	public void create () {
		//Initiating variables
		batch = new SpriteBatch();
		viewport = new FitViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		screens = new HashMap<String, Screen>();
		assets = new Assets();
		manager = assets.getAssetManager();
		font = new BitmapFont();




		MainMenu main = new MainMenu(this);
		screens.put("main", main);
		LoadingScreen load = new LoadingScreen(this);
		screens.put("load", load);
		setScreen(load);
			}

	@Override
	public void render () {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		super.render();
	}
	
	@Override
	public void dispose () {
		batch.dispose();
		assets.dispose();
	}
}
