package se.boregrim.gyarb.screens;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 * Created by robin.boregrim on 2016-10-21.
 */
public class MainMenu implements Screen {

    SpriteBatch batch;

    public MainMenu(SpriteBatch batch){
        this.batch = batch;
    }
    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        batch.begin();
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
