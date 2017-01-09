package se.boregrim.gyarb;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.viewport.FitViewport;
import se.boregrim.gyarb.managers.Assets;
import se.boregrim.gyarb.screens.GameScreen;
import se.boregrim.gyarb.screens.LoadingScreen;
import se.boregrim.gyarb.screens.MainMenu;

import java.util.HashMap;

public class Game extends com.badlogic.gdx.Game {
	public SpriteBatch batch, menuBatch;
	private HashMap<String, Screen> screens;
	private Assets assets;
	AssetManager manager;

	public BitmapFont font;
	Skin skin;
	//ScreenViewport viewport;
	FitViewport viewport;

	@Override
	public void create () {
		//Initiating variables
		batch = new SpriteBatch();
		menuBatch = new SpriteBatch();
		viewport = new FitViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		//viewport = new ScreenViewport();
		screens = new HashMap<String, Screen>();
		assets = new Assets();
		manager = assets.getAssetManager();
		font = new BitmapFont();
		skin = new Skin(Gdx.files.internal("clean-crispy/skin/clean-crispy-ui.json"));


		LoadingScreen load = new LoadingScreen(this);
		screens.put("load", load);
		MainMenu main = new MainMenu(this);
		screens.put("main", main);
		GameScreen gameScreen = new GameScreen(this);
		screens.put("game",gameScreen);
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
	public FitViewport getViewport(){
		return this.viewport;
	}

	//public ScreenViewport getViewport() {
	//	return viewport;
	//}

	public Skin getSkin() {
		return skin;
	}
	public HashMap<String, Screen> getScreens(){
		return screens;
	}

	public Assets getAssets() {
		return assets;
	}

	public SpriteBatch getBatch() {
		return batch;
	}
	public SpriteBatch getMenuBatch(){
		return menuBatch;
	}
}
