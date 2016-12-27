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
import se.boregrim.gyarb.Game;
import se.boregrim.gyarb.entities.*;
import se.boregrim.gyarb.managers.MapManager;
import se.boregrim.gyarb.pathfinding.Node;
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



    //Camera and Viewport
    private OrthographicCamera cam;
    private FitViewport vp;

    //Batch
    private SpriteBatch batch;


    //Map rendering
    private MapManager mapManager;
    private OrthogonalTiledMapRenderer otmr;

    //Box2d
    private World world;
    private Box2DDebugRenderer b2dr;
    private Player player;

    //Lighting
    private RayHandler rayHandler;
    public boolean lightsOn;


    Vector2 NodePos;

    //Ui
    GameUiScreen ui;
    public GameScreen(Game game){
        this.game = game;
    }
    @Override
    public void show() {
        entities = new ArrayList<Entity>();
        paused = false;

        NodePos = new Vector2(0,0);

        //Camera and Viewport
        cam = new OrthographicCamera();
        vp = new FitViewport(Gdx.graphics.getWidth() / PPM,Gdx.graphics.getHeight() / PPM, cam);
        cam.position.set(vp.getWorldWidth()/2 / PPM,vp.getWorldHeight()/2 / PPM,0);

        //Batch
        batch = new SpriteBatch();

        //Box2d
        world = new World(new Vector2(),true);
        b2dr = new Box2DDebugRenderer();

        //Load map
        MapManager mapManager = new MapManager();
        mapManager.loadMap("Maps/TestMap2.tmx", world);
        otmr = mapManager.otmr;




        player = new Player(this, (int)vp.getWorldWidth()/2,(int)vp.getWorldHeight()/2);
        entities.add(player);

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


        world.step( delta, 6, 2);

        updateEntities(delta);

        cam.position.set(player.body.getPosition().x,player.body.getPosition().y,0);
        //cam.lookAt(player.body.getPosition().x,player.body.getPosition().y,0);

        cam.update();
        otmr.setView(cam);


    }
    @Override
    public void render(float delta) {
        handleInput(delta);
        if(!paused)
            update(delta);



        //Render map
        otmr.render();



        //Render Box2d
        b2dr.render(world, cam.combined);

        //Entities
        renderEntities(delta);

        //Render light
        rayHandler.setCombinedMatrix(cam);
        if(getLightsOn())
            rayHandler.updateAndRender();

        // Node Debugrenderer



        batch.begin();

        for (Vector2 pos : MapManager.nonWalkablePos) {
            batch.draw(new Texture(Gdx.files.internal("pic2.png")),pos.x,pos.y, 10/PPM,10/PPM);

        }


        Texture texture = new Texture(Gdx.files.internal("pic.png"));
        batch.draw(texture,player.getX(),player.getY(),2,2);
        Array<Connection<Node>> a = MapManager.graph.getNodeByPos((int)NodePos.x, (int)NodePos.y).getConnections();
        //System.out.println("size: " + a.size);
        for (int i = 0; i < a.size; i++) {
            int index = mapManager.graph.getIndex(a.get(i).getToNode());
            int y = index / MapManager.mapTileWidth;
            int x = index % MapManager.mapTileWidth;
            //System.out.println(x + " " + y);
            batch.draw(texture,x,y, 10/PPM, 10/PPM);
        }
        //for (int y = 0; y < MapManager.mapTileHeight; y++) {
        //    for (int x = 0; x < MapManager.mapTileWidth; x++) {
        //        if((!MapManager.graph.getNodeByPos(x,y).equals(null))){
        //            batch.draw(texture,x,y, 10/PPM, 10/PPM);
        //        }
//
        //    }
        //}



        batch.end();



        //Ui
        if(!paused)
            ui.render(delta);

    }
    public void handleInput(float delta){


        //Setting variables for player movement
        int speed = (int) (15 * PPM);
        float maxVel = 15;
        float vX = 0 ;
        float vY = 0 ;


        // Player Controls
        if(!paused) {

            if (Gdx.input.isKeyPressed(Input.Keys.RIGHT) || Gdx.input.isKeyPressed(Input.Keys.D)/*&& player.body.getLinearVelocity().x <= 2f */) {
                vX += speed;
            }
            if (Gdx.input.isKeyPressed(Input.Keys.LEFT) || Gdx.input.isKeyPressed(Input.Keys.A) /*&& player.body.getLinearVelocity().x >= -2f*/) {
                vX += -speed;
            }
            if (Gdx.input.isKeyPressed(Input.Keys.UP) || Gdx.input.isKeyPressed(Input.Keys.W) /*&& player.body.getLinearVelocity().y <= 2f*/) {
                vY += speed;
            }
            if (Gdx.input.isKeyPressed(Input.Keys.DOWN) || Gdx.input.isKeyPressed(Input.Keys.S) /*&& player.body.getLinearVelocity().y >= -2f*/) {
                vY += -speed;
            }
            //Moving the player
            Vector2 v = player.body.getLinearVelocity();
            maxVel = vX == 0 || vY == 0 ? maxVel: (float) Math.sqrt((Math.pow(maxVel,2))/2) ;
            //System.out.println(maxVel);
            //System.out.println((float) Math.sqrt((Math.pow(maxVel*PPM,2))/2));
            player.body.applyForceToCenter( v.x >=0 ? (v.x < maxVel ? vX : 0) : (v.x > -maxVel ? vX : 0) , v.y >=0 ? (v.y < maxVel ? vY : 0) : (v.y > -maxVel? vY : 0),true);
            //System.out.println(Math.sqrt(Math.pow(v.x,2) + Math.pow(v.y,2)));

            if(Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
                createBox((int) (player.body.getPosition().x * PPM), (int) (player.body.getPosition().y * PPM));
            }
            if(Gdx.input.isKeyJustPressed(Input.Keys.O)){
                toggleLightsOn();
            }
            if(Gdx.input.isKeyPressed(Input.Keys.R)){
                player.body.setTransform(5/PPM, 5/PPM,player.body.getAngle());
            }if(Gdx.input.isKeyPressed(Input.Keys.P)){
                player.body.setTransform(-5/PPM, -5/PPM,player.body.getAngle());
            }

            if(Gdx.input.isKeyJustPressed(Input.Keys.K)){

                AiEntity e;
                addEntity(e = new TestEnemy(this, (int) (player.body.getPosition().x * PPM), (int) (player.body.getPosition().y * PPM), 25, player));

                //Temporary behaviour



            }

        }


        //Other input
        if(Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)){
            if(paused)
                resume();
            else
                pause();
        }
        if(Gdx.input.isKeyJustPressed(Input.Keys.UP)){
            NodePos.y++;
        }
        if(Gdx.input.isKeyJustPressed(Input.Keys.DOWN)){
            NodePos.y--;
        }
        if(Gdx.input.isKeyJustPressed(Input.Keys.LEFT)){
            NodePos.x--;
        }
        if(Gdx.input.isKeyJustPressed(Input.Keys.RIGHT)){
            NodePos.x++;
        }



}

    @Override
    public void resize(int width, int height) {
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
    }
    public void updateEntities(float delta){
        for (Entity e:entities) {
            e.update(delta);
        }
    }
    public void renderEntities(float delta){
        for (Entity e:entities) {
            e.render(delta);
        }
    }
    public void createBox(int x, int y){
        new Box(world,x,y);
    }

    //Getters and Setters
    public FitViewport getViewport(){
        return vp;
    }

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

    public void setLightsOn(boolean lightsOn) {
        this.lightsOn = lightsOn;
    }
    public void toggleLightsOn(){
        setLightsOn(getLightsOn()? false : true);
    }
    public boolean getLightsOn(){
        return lightsOn;
    }
}
