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

/**
 * Created by robin.boregrim on 2016-10-24.
 */
public class LoadingScreen implements Screen {

    //Variables
    Game game;
    AssetManager manager;
    Stage stage;
    Skin skin;
    ProgressBar pb;

    public LoadingScreen(Game game){
        this.game = game;
        manager = game.getAssets().getAssetManager();

        stage = new Stage();
        skin = game.getSkin();

    }
    @Override
    public void show() {

        stage.setViewport(game.getViewport());

        pb = new ProgressBar(0f,1f,0.1f,false,game.getSkin());

        pb.setBounds(stage.getWidth() * 0.5f -100f, stage.getHeight() * 0.5f,200,50);
        stage.addActor(pb);

        //manager.finishLoadingAsset("harambe.png");


    }

    @Override
    public void render(float delta) {
        if (manager.update()) {
            game.setScreen(game.getScreens().get("main"));
        }

        pb.setValue(manager.getProgress());
        stage.act();
        stage.draw();

    }

    @Override
    public void resize(int width, int height) {
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
