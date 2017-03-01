package se.boregrim.gyarb.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.utils.viewport.FitViewport;
import se.boregrim.gyarb.Game;

/**
 * Created by robin.boregrim on 2017-02-26.
 */
public class GameOverScreen implements Screen {
    private GameScreen gs;

    Game game;

    private Stage stage;
    Button restart, startmenu, exit;
    Image gameOver;
    Label score, wave;



    FitViewport vp;
    public GameOverScreen(GameScreen gameScreen){
        gs = gameScreen;
        game = gs.getGame();

        vp = new FitViewport(Gdx.graphics.getWidth(),Gdx.graphics.getHeight());
        stage = new Stage(vp);
        stage.getBatch().setProjectionMatrix(vp.getCamera().combined);

        gameOver = new Image((Texture) game.getAssets().getAssetManager().get("Gameover.png"));
        score = new Label("Your score : " + gs.getScore() + "",gs.getGame().getSkin());
        wave = new Label("Wave: " + gs.getWaveCount() + "",gs.getGame().getSkin());

        stage.addActor(gameOver);
        stage.addActor(score);
        stage.addActor(wave);


    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {


        stage.act();
        stage.draw();
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
        stage.dispose();
    }
}
