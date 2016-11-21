package se.boregrim.gyarb.screens;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ProgressBar;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import se.boregrim.gyarb.Game;
import se.boregrim.gyarb.entities.Entity;

import java.util.ArrayList;

/**
 * Created by robin.boregrim on 2016-10-24.
 */
public class LoadingScreen implements Screen {

    //Variables
    private Game game;
    private AssetManager manager;
    private Stage stage;
    private Skin skin;
    private ProgressBar pb;


    public LoadingScreen(Game game){
        this.game = game;

        //Making a refrence to the assetmanager (is used as input for progressbar)
        manager = game.getAssets().getAssetManager();

        // Creating the stage
        stage = new Stage();

        //Getting the skin for the progressbar
        skin = game.getSkin();

    }
    @Override
    public void show() {
        //Setting stage viewport
        stage.setViewport(game.getViewport());

        //Creating Progressbar
        pb = new ProgressBar(0f,1f,0.1f,false,skin);
        pb.setBounds(stage.getWidth() * 0.5f - 100f, stage.getHeight() * 0.5f,200,50);

        //Adding it to the stage
        stage.addActor(pb);



    }

    @Override
    public void render(float delta) {
        // If all the assets are loaded, set screen to main
        if (manager.update()) {
            game.setScreen(game.getScreens().get("main"));
        }
        //Update the Progressbar
        pb.setValue(manager.getProgress());

        //Draw the stage
        stage.act();
        stage.draw();

    }

    @Override
    public void resize(int width, int height) {
        //Update viewport on resize
        stage.getViewport().update(width,height);
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
