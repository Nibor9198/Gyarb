package se.boregrim.gyarb.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.FitViewport;
import se.boregrim.gyarb.Game;

/**
 * Created by robin.boregrim on 2017-02-26.
 */
public class GameOverScreen implements Screen {
    private GameScreen gs;

    Game game;
    Skin skin;

    private Stage stage;
    TextButton restart, mainmenu, exit;
    Image gameOver;
    Label score, wave;



    FitViewport vp;
    public GameOverScreen(GameScreen gameScreen){
        gs = gameScreen;
        game = gs.getGame();
        skin = game.getSkin();

        vp = new FitViewport(Gdx.graphics.getWidth(),Gdx.graphics.getHeight());
        stage = new Stage(vp);
        Gdx.input.setInputProcessor(stage);
        stage.getBatch().setProjectionMatrix(vp.getCamera().combined);

        gameOver = new Image((Texture) game.getAssets().getAssetManager().get("Gameover.png"));
        score = new Label("Your score : " + gs.getScore() + "",gs.getGame().getSkin());
        wave = new Label("Wave: " + gs.getWaveCount() + "",gs.getGame().getSkin());

        restart = new TextButton("Restart", skin);
        mainmenu = new TextButton("MainMenu", skin);
        exit = new TextButton("Exit", skin);

        stage.addActor(gameOver);
        stage.addActor(score);
        stage.addActor(wave);
        stage.addActor(restart);
        stage.addActor(mainmenu);
        stage.addActor(exit);

        float w = vp.getScreenWidth();
        float h = vp.getScreenHeight();

        float t = h;
        t += -h*0.5f;
        gameOver.setBounds(w*0.25f,t,w*0.5f,h*0.5f);
        t += - score.getHeight();
        score.setPosition((w - score.getWidth())/2, t);
        t += - wave.getHeight();
        wave.setPosition((w - wave.getWidth())/2, t);
        t += - restart.getHeight() * 2;
        restart.setPosition((w*1/4) - restart.getWidth()/2, t);
        mainmenu.setPosition((w*2/4) - mainmenu.getWidth()/2, t);
        exit.setPosition((w*3/4) - exit.getWidth()/2, t);



        restart.setTouchable(Touchable.enabled);
        mainmenu.setTouchable(Touchable.enabled);
        exit.setTouchable(Touchable.disabled);

        //Listeners

        restart.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                gs.dispose();
                game.newGame();
                game.setScreen(game.getScreens().get("game"));
                dispose();
            }
        });
        mainmenu.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                gs.dispose();
                game.newGame();
                game.setScreen(game.getScreens().get("main"));
                dispose();
            }
        });
        exit.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                System.exit(0);
                System.out.println("Hej");
            }
        });

        //




        //for (int i = 1; i < stage.getActors().size; i++) {
         //   Actor a = stage.getActors().get(i);
         //   t += - a.getHeight();
         //   a.setPosition((w - a.getWidth())/2, t);
        //}

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
