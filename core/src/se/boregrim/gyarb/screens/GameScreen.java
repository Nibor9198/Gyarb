package se.boregrim.gyarb.screens;

import box2dLight.ConeLight;
import box2dLight.RayHandler;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.ai.pfa.indexed.IndexedAStarPathFinder;
import com.badlogic.gdx.graphics.*;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import se.boregrim.gyarb.Game;
import se.boregrim.gyarb.Interfaces.Entity;
import se.boregrim.gyarb.Interfaces.Interactable;
import se.boregrim.gyarb.Listeners.B2dContactListener;
import se.boregrim.gyarb.entities.*;
import se.boregrim.gyarb.managers.MapManager;
import se.boregrim.gyarb.managers.SpawnManager;
import se.boregrim.gyarb.pathfinding.HeuristicImp;
import se.boregrim.gyarb.pathfinding.PathfindDebugrenderer;
import se.boregrim.gyarb.utils.Constants;


import java.util.ArrayList;

import static se.boregrim.gyarb.utils.Constants.*;

/**
 * Created by Robin on 2016-11-08.
 */
public class GameScreen implements Screen {
    private Game game;

    private boolean paused;
    int waveCount;
    int score;

    SpawnManager spawner;

    private ArrayList<Interactable> interactables;
    private ArrayList<Interactable> interactablestoDelete;
    private ArrayList<Interactable> interactablestoAdd;

    private ArrayList<Entity> entities;
    private ArrayList<Entity> entitiestoDelete;

    private ArrayList<Enemy> enemies;

    //Camera and Viewport
    private OrthographicCamera cam;
    //private FitViewport vp;
    private ScreenViewport vp;
    //Batch
    private SpriteBatch batch;


    //Map rendering
    private  MapManager mapManager;

    private OrthogonalTiledMapRenderer otmr;

    //Box2d
    private World world;
    private B2dContactListener contactListener;
    private Box2DDebugRenderer b2dr;
    private Player player;


    //Lighting
    private RayHandler rayHandler;
    public boolean debugged;

    //Pathfind
    HeuristicImp heuristic;
    IndexedAStarPathFinder pathFinder;
    PathfindDebugrenderer pathfindDebugrenderer;



    //Ui
    GameUiScreen ui;

    public GameScreen(Game game){
        this.game = game;
    }
    @Override
    public void show() {
        entities = new ArrayList<Entity>();
        entitiestoDelete = new ArrayList<Entity>();

        interactables = new ArrayList<Interactable>();
        interactablestoDelete = new ArrayList<Interactable>();
        interactablestoAdd = new ArrayList<Interactable>();
        paused = false;
        debugged = false;

        enemies = new ArrayList<Enemy>();

        spawner = new SpawnManager(this);
        addInteractable(spawner);
        waveCount = 0;
        score = 0;

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
        mapManager = new MapManager();
        mapManager.loadMap("Maps/TestMap3.tmx", world);
        otmr = mapManager.otmr;


        //Add Player
        player = spawner.spawnPlayer();



        //Pathfinding
        pathFinder = new IndexedAStarPathFinder(MapManager.graph,false);
        heuristic = new HeuristicImp();
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
        cam.position.set(player.body.getPosition().x,player.body.getPosition().y,0);
        //cam.position.set(player.getX(),player.getY(),0);


        //cam.lookAt(player.body.getPosition().x,player.body.getPosition().y,0);

        cam.update();
        otmr.setView(cam);

        addInteractables();
        deleteEntities();
        deleteInteractables();
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
            //if(!i.isDead())
                i.input(paused);
        }

        if(!paused){

            if(Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
                //createBox((int) (player.body.getPosition().x * PPM), (int) (player.body.getPosition().y * PPM));
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
        ui.dispose();
        world.dispose();
        rayHandler.dispose();
        mapManager.dispose();
        mapManager.dispose();
        //otmr.dispose();

    }
    public void addEntity(Entity e){
        entities.add(e);
        addInteractable(e);
    }

    public void nexWave(){
        waveCount++;
        ui.nextWave(waveCount);
    }

    public void gameOver(){
        pause();
        game.setScreen(new GameOverScreen(this));

    }
    public void removeEntity(Entity e){
        entitiestoDelete.add(e);
    }
    public ArrayList<Entity> getEntities(){
        return entities;
    }
    public void addInteractable(Interactable i){
        interactablestoAdd.add(i);
    }
    public void removeInteractable(Interactable i){

        interactablestoDelete.add(i);
    }

    public void updateInteractables(float delta){
        for (Interactable i:interactables) {
            i.update(delta);
        }
    }
    public void renderInteractables(float delta){
        for (Interactable i:interactables) {
            if (!i.isDead())
                i.render(delta);
        }
    }
    public void createBox(int x, int y){
        new Box(this,x,y);
    }

    public void newGame(){
        dispose();
        for (Entity e:entities
                ) {
            e.destroyBody();
        }
        show();
    }

    //Getters and Setters
    //public FitViewport getViewport(){return vp;}



    public void toggleDebugged(){
        debugged = !debugged;
        if(debugged){
            addInteractable(pathfindDebugrenderer);
        }else{
            removeInteractable(pathfindDebugrenderer);
        }
    }

    private void deleteEntities(){
        for (Entity e: entitiestoDelete) {
            entities.remove(e);
            interactables.remove(e);
            e.destroyBody();
        }
        entitiestoDelete.clear();
    }
    private void deleteInteractables(){
        for (Interactable i: interactablestoDelete) {
            interactables.remove(i);
        }
        interactablestoDelete.clear();

    }
    private void addInteractables(){
        for (Interactable i: interactablestoAdd) {
            interactables.add(i);
        }
        interactablestoAdd.clear();
    }
    public void addScore(int score){
        this.score += score;
    }

    //Getters and Setters
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
    public ArrayList<Enemy> getEnemies() {
        return enemies;
    }

    public int getWaveCount() {
        return waveCount;
    }

    public int getScore() {
        return score;
    }
    public HeuristicImp getHeuristic(){
        return heuristic;
    }

    public IndexedAStarPathFinder getPathFinder() {
        return pathFinder;
    }
}
