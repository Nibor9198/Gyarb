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
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import se.boregrim.gyarb.Assets;
import se.boregrim.gyarb.Game;

import java.awt.*;

/**
 * Created by robin.boregrim on 2016-10-21.
 */
public class MainMenu implements Screen {
    AssetManager manager;
    Game game;
    Stage stage;
    VerticalGroup group;

    Image bg;
    Label label;
    TextButton start, exit;


    public MainMenu(Game g) {
        game = g;
    }


    @Override
    public void show() {
        stage = new Stage(game.getViewport());
        manager = game.getAssets().getAssetManager();
        Gdx.input.setInputProcessor(stage);


        // Initiating ui


        bg = new Image((Texture) manager.get("menuBackground.png"));
        label = new Label("Epic Title",game.getSkin());
        start = new TextButton("Start game", game.getSkin(), "default");
        exit = new TextButton("Exit game", game.getSkin(), "default");
        start.setTouchable(Touchable.enabled);
        //exit.setTouchable(Touchable.enabled);



        VerticalGroup group = new VerticalGroup();
        group.setFillParent(true);
        //Adding Actors to group
        group.addActor(bg);
        group.addActor(label);
        group.addActor(start);
        group.addActor(exit);

        stage.addActor(group);

        //Listeners
        start.addListener( new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                dispose();
                game.setScreen(game.getScreens().get("game"));

            }
        });
        exit.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                System.exit(0);
            }
        });



    }

    @Override
    public void render(float delta) {
        //Referencing scale of the stage
        float width = stage.getWidth();
        float height = stage.getHeight();

        float w = 150;
        float h = 50;

        //This Arrays index is based the order actors where added to the stage
        bg.setBounds(0,0,width, height);
        label.setBounds((width-w)*0.5f,height*0.75f,w,h);
        start.setBounds((width-w)*0.5f,height*0.75f-h,w,h);
        exit.setBounds((width-w)*0.5f,height*0.75f-2*h,w,h);



        //Draw the stage
        stage.act(delta);
        stage.draw();

    }

    @Override
    public void resize(int width, int height) {
        //Update on resize
        stage.getViewport().update(Gdx.graphics.getWidth(),Gdx.graphics.getHeight());
        stage.getCamera().update();


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
