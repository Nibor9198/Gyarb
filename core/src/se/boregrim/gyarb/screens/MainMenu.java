package se.boregrim.gyarb.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
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
    Stage stage;
    TextButton button;



    public MainMenu(Game g) {
        game = g;
        batch = game.batch;
        stage = new Stage(game.getViewport());
        Gdx.input.setInputProcessor(stage);
        layout = new GlyphLayout();
        manager = g.assets.getAssetManager();

        button = new TextButton("Start game", game.getSkin(), "default");
        stage.addActor(new Label("Hello",game.getSkin()));
        stage.addActor(button);

        stage.act();

    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {


        float height = stage.getHeight();// Gdx.graphics.getHeight();
        float width = stage.getWidth(); //Gdx.graphics.getWidth();
        stage.act(delta);
        stage.draw();
        batch.begin();
        //Texture t = manager.get("harambe2.png");
        //batch.draw(t,0,0,Gdx.graphics.getWidth(),Gdx.graphics.getHeight());
        layout = new GlyphLayout();
        String s = "Title";
        layout.setText(game.font,s);
        //game.font.draw(batch, s,width*0.5f - layout.width/2, height*0.75f);
        //button.draw(batch, 0.3f);
        batch.end();

    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width,height);
        stage.getCamera().update();
        //stage.getViewport().getCamera();
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
