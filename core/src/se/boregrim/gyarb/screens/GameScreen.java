package se.boregrim.gyarb.screens;

import box2dLight.ConeLight;
import box2dLight.RayHandler;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.ai.pfa.Connection;
import com.badlogic.gdx.graphics.*;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import se.boregrim.gyarb.Game;
import se.boregrim.gyarb.Interfaces.Interactable;
import se.boregrim.gyarb.Listeners.B2dContactListener;
import se.boregrim.gyarb.entities.*;
import se.boregrim.gyarb.managers.MapManager;
import se.boregrim.gyarb.pathfinding.Node;
import se.boregrim.gyarb.pathfinding.PathfindDebugrenderer;
import se.boregrim.gyarb.utils.Constants;


import java.util.ArrayList;

import static se.boregrim.gyarb.utils.Constants.*;

/**
 * Created by Robin on 2016-11-08.
 */
public class GameScreen implements Screen {
    private Game game;
    private ArrayList<Entity> entities;
    private boolean paused;

    private ArrayList<Interactable> interactables;

    //Camera and Viewport
    private OrthographicCamera cam;
    //private FitViewport vp;
    private ScreenViewport vp;
    //Batch
    private SpriteBatch batch;


    //Map rendering
    private MapManager mapManager;
    private OrthogonalTiledMapRenderer otmr;

    //Box2d
    private World world;
    private B2dContactListener contactListener;
    private Box2DDebugRenderer b2dr;
    private Player player;


    //Lighting
    private RayHandler rayHandler;
    public boolean debugged;

    //Debug
    PathfindDebugrenderer pathfindDebugrenderer;


    //Ui
    GameUiScreen ui;

    public GameScreen(Game game){
        this.game = game;
    }
    @Override
    public void show() {
        entities = new ArrayList<Entity>();
        interactables = new ArrayList<Interactable>();
        paused = false;
        debugged = false;


        //Camera and Viewport
        cam = new OrthographicCamera();
        //vp = new FitViewport(Gdx.graphics.getWidth() / PPM,Gdx.graphics.getHeight() / PPM, cam);
        cam.zoom = 1/PPM;
        vp = new ScreenViewport(cam);

        cam.position.set(vp.getWorldWidth()/2 / PPM,vp.getWorldHeight()/2 / PPM,0);

        //Batch
        batch = game.getBatch();

        //Box2d
        world = new World(new Vector2(),true);
        world.setContactListener(contactListener = new B2dContactListener(this));
        b2dr = new Box2DDebugRenderer();

        //Load map
        MapManager mapManager = new MapManager();
        mapManager.loadMap("Maps/TestMap2.tmx", world);
        otmr = mapManager.otmr;


        //Add Player
        player = new Player(this, (int)vp.getWorldWidth()/2,(int)vp.getWorldHeight()/2);
        addEntity(player);

        //Pathfinddebugger
        pathfindDebugrenderer = new PathfindDebugrenderer(this);

        //Lighting
        rayHandler = new RayHandler(world, (int) (3*PPM), (int) (3*PPM));

        rayHandler.diffuseBlendFunc.set(GL20.GL_SRC_COLOR,GL20.GL_DST_COLOR);
        rayHandler.setAmbientLight(0.02f);
        //PointLight p = new PointLight(rayHandler, 2000, Color.CYAN, 8,0,0);
        ConeLight p = new ConeLight(rayHandler,1000,Color.CYAN,10,0,0,0, (float) (40));

        p.attachToBody(player.body);
        //p.setSoft(true);
        //p.setSoftnessLength(500000);

        Filter filter = new Filter();
        filter.categoryBits = Constants.CAT_LIGHT;
        filter.maskBits = Constants.CAT_WALL | CAT_ENTITY;
        filter.groupIndex = 1;
        p.setContactFilter(filter);





        //Ui
        ui = new GameUiScreen(this);
        ui.show();
    }

    public void update(float delta){


        world.step(delta, 6, 2);


        updateInteractables(delta);
            //Avkommentera!
        //cam.position.set(player.body.getPosition().x,player.body.getPosition().y,0);
        cam.position.set(player.getX(),player.getY(),0);


        //cam.lookAt(player.body.getPosition().x,player.body.getPosition().y,0);

        cam.update();
        otmr.setView(cam);


    }
    @Override
    public void render(float delta) {
        //cam.setToOrtho(false,getViewport().getScreenWidth(),getViewport().getScreenHeight());

        handleInput(delta);
        if(!paused)
            update(delta);


        //Render map
        otmr.render();



        //Render Box2d
        b2dr.render(world, cam.combined);

        //Interactables
        renderInteractables(delta);

        //Render light
        rayHandler.setCombinedMatrix(cam);
        if(!debugged) {
            rayHandler.updateAndRender();
        }
    //Ui
        if(!paused)
            ui.render(delta);

    }
    public void handleInput(float delta){

        for (Interactable i :interactables) {
            i.input(paused);
        }

        if(!paused){

            if(Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
                createBox((int) (player.body.getPosition().x * PPM), (int) (player.body.getPosition().y * PPM));
            }
            if(Gdx.input.isKeyJustPressed(Input.Keys.O)){
                toggleDebugged();
            }
            if(Gdx.input.isKeyPressed(Input.Keys.R)){
                player.body.setTransform(5/PPM, 5/PPM,player.body.getAngle());
            }if(Gdx.input.isKeyPressed(Input.Keys.P)){
                player.body.setTransform(-5/PPM, -5/PPM,player.body.getAngle());
            }
            if(Gdx.input.isKeyJustPressed(Input.Keys.M)){
                getViewport().setScreenSize(getViewport().getScreenWidth()/2,getViewport().getScreenHeight() /2);
            }
            if(Gdx.input.isKeyJustPressed(Input.Keys.MINUS)){
                getViewport().setScreenSize(getViewport().getScreenWidth()*2,getViewport().getScreenHeight() *2);
            }

            if(Gdx.input.isKeyJustPressed(Input.Keys.K)){
                AiEntity e;
                addEntity(e = new TestEnemy(this, (int) (player.body.getPosition().x * PPM), (int) (player.body.getPosition().y * PPM), 25, player));
            }
        }

        //Other input
        if(Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)){
            if(paused)
                resume();
            else
                pause();
        }




}

    @Override
    public void resize(int width, int height) {
        ui.resize(width,height);
        vp.update(width, height);
    }

    @Override
    public void pause() {
        paused = true;
    }

    @Override
    public void resume() {
        paused = false;
    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        rayHandler.dispose();
        mapManager.dispose();


    }
    public void addEntity(Entity e){
        entities.add(e);
        addInteractable(e);
    }
    public void removeEntity(Entity e){
        entities.remove(e);
        removeInteractable(e);
    }
    public void addInteractable(Interactable i){
        interactables.add(i);
    }
    public void removeInteractable(Interactable i){
        interactables.remove(i);
    }

    public void updateInteractables(float delta){
        for (Interactable i:interactables) {
            i.update(delta);
        }
    }
    public void renderInteractables(float delta){
        for (Interactable i:interactables) {
            i.render(delta);
        }
    }
    public void createBox(int x, int y){
        new Box(this,x,y);
    }

    //Getters and Setters
    //public FitViewport getViewport(){return vp;}
    public ScreenViewport getViewport(){return vp;}
    public Game getGame() {
        return game;
    }

    public World getWorld() {
        return world;
    }

    public SpriteBatch getBatch() {
        return batch;
    }
    public Player getPlayer(){return player;}
    public ArrayList<Interactable> getInteractables(){return interactables;}


    public void toggleDebugged(){
        debugged = !debugged;
        if(debugged){
            addInteractable(pathfindDebugrenderer);
        }else{
            removeInteractable(pathfindDebugrenderer);
        }
    }

}
