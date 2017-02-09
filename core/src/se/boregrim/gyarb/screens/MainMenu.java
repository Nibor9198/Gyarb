package se.boregrim.gyarb.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import se.boregrim.gyarb.Game;

/**
 * Created by robin.boregrim on 2016-10-21.
 */
public class MainMenu implements Screen {
    AssetManager manager;
    Game game;
    Stage stage;
    VerticalGroup group;

    Texture bg;
    Image title;
    Label label;
    TextButton start,options, exit;
    SpriteBatch batch;

    public MainMenu(Game g) {
        game = g;
    }


    @Override
    public void show() {
        stage = new Stage(game.getViewport());
        manager = game.getAssets().getAssetManager();
        Gdx.input.setInputProcessor(stage);


        // Initiating ui


        bg = (Texture) manager.get("menuBackground.png");
        title = new Image((Texture)manager.get("title.png"));
        //label = new Label("Epic Title",game.getSkin());
        //label.setAlignment(1);
        start = new TextButton("Start game", game.getSkin(), "default");
        options = new TextButton("Options", game.getSkin(), "default");
        exit = new TextButton("Exit game", game.getSkin(), "default");
        start.setTouchable(Touchable.enabled);
        //exit.setTouchable(Touchable.enabled);



        VerticalGroup group = new VerticalGroup();
        group.setFillParent(true);
        //Adding Actors to group
        //group.addActor(bg);
        group.addActor(title);
        group.addActor(start);
        group.addActor(options);
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
    private void setStageBounds(){
        //Referencing the size of the stage (Used to position Title and buttons)
        float width = stage.getWidth();
        float height = stage.getHeight();

        float w = 150;
        float h = 50;

        //


        //bg.setBounds(0,0, 2000,2000); //Gdx.graphics.getWidth()*2, Gdx.graphics.getHeight()*2);
        renderBackground();
        title.setBounds((width*0.5f-w),height*0.75f,w*2,h*2);
        start.setBounds((width-w)*0.5f,height*0.75f-2*h,w,h);
        exit.setBounds((width-w)*0.5f,height*0.75f-3*h,w,h);
    }
    private void renderBackground(){
        float windowWidth = Gdx.graphics.getWidth();
        float windowHeight = Gdx.graphics.getHeight();
        float bgsize = windowWidth > windowHeight ? windowWidth : windowHeight;

        SpriteBatch batch = game.getMenuBatch();
        batch.begin();
        batch.draw(bg,0,0, bgsize, bgsize);
        batch.end();
    }

    @Override
    public void render(float delta) {
        setStageBounds();

        //Draw the stage
        stage.act(delta);
        stage.draw();

    }

    @Override
    public void resize(int width, int height) {
        //Update on resize
        stage.getViewport().update(Gdx.graphics.getWidth(),Gdx.graphics.getHeight());
        stage.getCamera().update();
        setStageBounds();


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
