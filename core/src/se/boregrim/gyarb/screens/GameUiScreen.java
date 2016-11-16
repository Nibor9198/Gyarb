package se.boregrim.gyarb.screens;


import com.badlogic.gdx.Screen;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.utils.viewport.FitViewport;
import se.boregrim.gyarb.Game;

/**
 * Created by robin.boregrim on 2016-11-16.
 */
public class GameUiScreen implements Screen {
    private GameScreen gs;
    private Stage stage;
    Game game;

    public GameUiScreen(GameScreen gameScreen){
        gs = gameScreen;
        game = gs.getGame();

        FitViewport vp = gs.getViewport();
        stage = new Stage(vp);
        Label label = new Label("Tjenare", game.getSkin());
        label.setBounds(vp.getScreenWidth()*0.5f - 200, vp.getScreenHeight()*0.5f,200f,50f);
        stage.addActor(label);

    }
    @Override
    public void show() {
        System.out.println("GameUiScreen show is working");
    }

    @Override
    public void render(float delta) {
        stage.act(delta);
        stage.draw();
    }
    public void update(float delta){

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
