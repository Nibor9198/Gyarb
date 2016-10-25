package se.boregrim.gyarb.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import se.boregrim.gyarb.Assets;
import se.boregrim.gyarb.Game;

import java.awt.*;

/**
 * Created by robin.boregrim on 2016-10-21.
 */
public class MainMenu implements Screen {

    SpriteBatch batch;
    AssetManager manager;
    Game game;

    public MainMenu(Game g) {
        this.batch = g.batch;
        manager = g.assets.getAssetManager();
        game = g;
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        float height = Gdx.graphics.getHeight();
        float width = Gdx.graphics.getWidth();
        batch.begin();
        //Texture t = manager.get("harambe2.png");
        //batch.draw(t,0,0,Gdx.graphics.getWidth(),Gdx.graphics.getHeight());

        String s = "Ninja something";
        game.font.draw(batch, s,width*0.5f - game.font.getSpaceWidth()* s.length(), height*0.75f);

        batch.end();

    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }
}
