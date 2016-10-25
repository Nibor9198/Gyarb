package se.boregrim.gyarb.screens;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import se.boregrim.gyarb.Game;

/**
 * Created by robin.boregrim on 2016-10-24.
 */
public class LoadingScreen implements Screen {
    SpriteBatch batch;
    Texture img;
    Game game;
    AssetManager manager;
    BitmapFont font;
    public LoadingScreen(Game game){
        this.game = game;
        manager = game.assets.getAssetManager();
        this.batch = game.batch;
    }
    @Override
    public void show() {
        font = new BitmapFont();
        manager.finishLoadingAsset("harambe.png");


    }

    @Override
    public void render(float delta) {
        if (manager.update()) {
            game.setScreen(game.screens.get("main"));
        }
        String s = +(100 * manager.getProgress()) + "%";
        System.out.println(s);
        batch.begin();
        font.draw(batch, s, 20, 20);
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
