package se.boregrim.gyarb.screens;


import com.badlogic.gdx.Screen;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.utils.viewport.FitViewport;
import se.boregrim.gyarb.Game;
import static se.boregrim.gyarb.utils.Constants.PPM;

/**
 * Created by robin.boregrim on 2016-11-16.
 */
public class GameUiScreen implements Screen {
    private GameScreen gs;
    private Stage stage;
    FitViewport vp;
    Game game;

    public GameUiScreen(GameScreen gameScreen){
        gs = gameScreen;
        game = gs.getGame();

        vp = gs.getViewport();
        stage = new Stage(vp);
        Label label = new Label("Tjenare", game.getSkin());

        stage.addActor(label);
        Button b = new Button(game.getSkin());

        stage.addActor(b);

    }
    @Override
    public void show() {
        System.out.println("GameUiScreen show is working");
    }

    @Override
    public void render(float delta) {

        float x = vp.getScreenX();
        float y = vp.getScreenY();
        stage.getActors().get(0).setBounds(vp.getScreenWidth()*0.5f - 200, vp.getScreenHeight()*0.5f,200f,50f);
        stage.getActors().get(1).setBounds(x+2f,y+2f,2,2);
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
