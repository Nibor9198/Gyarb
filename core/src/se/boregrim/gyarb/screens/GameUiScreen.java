package se.boregrim.gyarb.screens;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ProgressBar;
import com.badlogic.gdx.scenes.scene2d.ui.Slider;
import com.badlogic.gdx.utils.viewport.FitViewport;
import se.boregrim.gyarb.Game;

import java.util.Random;

import static se.boregrim.gyarb.utils.Constants.PPM;

/**
 * Created by robin.boregrim on 2016-11-16.
 */
public class GameUiScreen implements Screen {
    private GameScreen gs;
    private Stage stage;
    FitViewport vp;

    Game game;
    Label label, middleLabel;
    ProgressBar health;

    int wavecount;
    float scaleTemp;
    long waveStamp;

    public GameUiScreen(GameScreen gameScreen){
        gs = gameScreen;
        game = gs.getGame();

        //vp = gs.getViewport();
        vp = new FitViewport(Gdx.graphics.getWidth(),Gdx.graphics.getHeight());
        stage = new Stage(vp);
        label = new Label("0", game.getSkin());
        middleLabel = new Label("Wave 0", game.getSkin());
        health = new ProgressBar(0,100,1,false, game.getSkin());

        Button b = new Button(game.getSkin());

        stage.addActor(label);
        stage.addActor(middleLabel);
        stage.addActor(health);

        //stage.addActor(b);
        wavecount = 0;
        scaleTemp = 0.01f;
        waveStamp = System.currentTimeMillis();

    }
    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        update(delta);

        //stage.getActors().get(1).setBounds(x-2f,y-2f,200,200);





        stage.act(delta);
        stage.draw();

    }
        public void update(float delta){

            float x = vp.getScreenX();
            float y = vp.getScreenY();

            //label.setText(((GameScreen)game.getScreens().get("game")).getPlayer().getPosition().toString() + gs.getPlayer().getHealth());
            label.setText(gs.getEnemies().size()+"");
            label.setBounds(vp.getScreenWidth()*0.5f - 200, vp.getScreenHeight()*0.5f,200f/PPM,50f/PPM);
            health.setBounds(health.getWidth(),health.getHeight(), health.getWidth(),health.getHeight());
            health.setValue(gs.getPlayer().getHealth());

            middleLabel.setBounds(vp.getScreenWidth() * 0.5f - 2000/PPM, vp.getScreenHeight() * 0.75f,50/PPM, 200/PPM);
            if((System.currentTimeMillis()-waveStamp) < 4000)
                scaleTemp = scaleTemp * (3-(scaleTemp));
            else if (scaleTemp != 0.01f)
                scaleTemp = 0.01f;



            middleLabel.setFontScale(scaleTemp);
    }

    @Override
    public void resize(int width, int height) {

        vp.setWorldHeight(height);
        vp.setWorldWidth(width);
        vp.update(Gdx.graphics.getWidth(),Gdx.graphics.getHeight());
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

    public void nextWave(int wave){
        middleLabel. setText("Wave " + wave);
        wavecount = wave;
        waveStamp = System.currentTimeMillis();
    }
}
