package se.boregrim.gyarb.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
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
    GlyphLayout layout;
    public Stage stage;
    public MainMenu(Game g) {
        this.batch = g.batch;
        stage = new Stage();
        FitViewport v = game.viewport;
        stage.setViewport(game.viewport);
        manager = g.assets.getAssetManager();
        game = g;
        layout = new GlyphLayout();

    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {


        float height = Gdx.graphics.getHeight();
        float width = Gdx.graphics.getWidth();
        batch.begin();
        //Texture t = manager.get("harambe2.png");
        //batch.draw(t,0,0,Gdx.graphics.getWidth(),Gdx.graphics.getHeight());
        layout = new GlyphLayout();
        String s = "Title";
        layout.setText(game.font,s);
        game.font.draw(batch, s,width*0.5f - layout.width/2, height*0.75f);

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
