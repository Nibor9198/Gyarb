package se.boregrim.gyarb;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import se.boregrim.gyarb.screens.MainMenu;

import java.util.HashMap;

public class Game extends com.badlogic.gdx.Game {
	public SpriteBatch batch;
	Texture img;
	HashMap<Screen,String>;
	@Override
	public void create () {
		batch = new SpriteBatch();
		MainMenu main = new MainMenu();

		setScreen(main);

			}

	@Override
	public void render () {

	}
	
	@Override
	public void dispose () {
		batch.dispose();
		img.dispose();
	}
}
