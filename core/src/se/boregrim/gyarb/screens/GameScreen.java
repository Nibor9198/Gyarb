package se.boregrim.gyarb.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.utils.viewport.FitViewport;
import se.boregrim.gyarb.Game;

/**
 * Created by Robin on 2016-11-08.
 */
public class GameScreen implements Screen {

    private OrthographicCamera cam;
    private FitViewport vp;
    private TiledMap map;
    private TmxMapLoader maploader;
    private OrthogonalTiledMapRenderer otmr;
    private Game game;
    public GameScreen(Game game){
        this.game = game;
    }
    @Override
    public void show() {
        cam = new OrthographicCamera();
        vp = new FitViewport(Gdx.graphics.getWidth(),Gdx.graphics.getHeight(), cam);
        maploader = new TmxMapLoader();
        map = maploader.load("Maps/TestMap.tmx");
        otmr = new OrthogonalTiledMapRenderer(map);
        cam.position.set(vp.getWorldWidth()/2,vp.getWorldHeight()/2,0);

    }


    @Override
    public void render(float delta) {
        handleInput();
        cam.update();
        otmr.setView(cam);
        otmr.render();
    }
    private void handleInput(){
        int speed = 4;

        if(Gdx.input.isKeyPressed(Input.Keys.RIGHT)){
            cam.position.x += speed;
        }
        if(Gdx.input.isKeyPressed(Input.Keys.LEFT)){
            cam.position.x += -speed;
        }
        if(Gdx.input.isKeyPressed(Input.Keys.UP)){
            cam.position.y += speed;
        }
        if(Gdx.input.isKeyPressed(Input.Keys.DOWN)){
            cam.position.y += -speed;
        }
}

    @Override
    public void resize(int width, int height) {
        vp.update(width, height);
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
