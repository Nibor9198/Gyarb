package se.boregrim.gyarb.screens;

import box2dLight.PointLight;
import box2dLight.RayHandler;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.*;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.viewport.FitViewport;
import se.boregrim.gyarb.Game;
import se.boregrim.gyarb.entities.Box;
import se.boregrim.gyarb.entities.Entity;
import se.boregrim.gyarb.entities.Player;


import java.awt.*;
import java.awt.event.MouseListener;
import java.util.ArrayList;

import static se.boregrim.gyarb.utils.Constants.PPM;

import static com.badlogic.gdx.utils.JsonValue.ValueType.object;

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

    //Map rendering
    private TiledMap map;
    private String mapRef;
    private TmxMapLoader maploader;
    private OrthogonalTiledMapRenderer otmr;

    //Box2d
    private World world;
    private Box2DDebugRenderer b2dr;
    private Player player;

    //Lighting
    private RayHandler rayHandler;

    public GameScreen(Game game){
        this.game = game;
    }
    @Override
    public void show() {
        entities = new ArrayList<Entity>();
        paused = false;

        //Camera and Viewport
        cam = new OrthographicCamera();
        vp = new FitViewport(Gdx.graphics.getWidth() / PPM,Gdx.graphics.getHeight() / PPM, cam);
        cam.position.set(vp.getWorldWidth()/2 / PPM,vp.getWorldHeight()/2 / PPM,0);

        //Load map
        mapRef = "Maps/TestMap.tmx";
        maploader = new TmxMapLoader();
        map = maploader.load(mapRef);
        otmr = new OrthogonalTiledMapRenderer(map, 1 / PPM);

        //Box2d
        world = new World(new Vector2(),true);
        b2dr = new Box2DDebugRenderer();
        Body body;
        BodyDef bdef = new BodyDef();
        FixtureDef fdef = new FixtureDef();
        PolygonShape shape = new PolygonShape();

        player = new Player(world, (int)vp.getWorldWidth()/2,(int)vp.getWorldHeight()/2);
        entities.add(player);

        //Lighting
        rayHandler = new RayHandler(world);

        PointLight p = new PointLight(rayHandler, 2000, Color.CYAN, 8, 0, 0);
        p.attachToBody(player.body);

        Filter filter = new Filter();
        filter.groupIndex = 1;
        filter.categoryBits = 1;
        filter.maskBits = -1;
        p.setContactFilter(filter);



        // Create box2d objects from the map file (Collision)
        for (MapObject mapObject : map.getLayers().get(1).getObjects().getByType(RectangleMapObject.class)){
            Rectangle rect = ((RectangleMapObject) mapObject).getRectangle();
            bdef.type = BodyDef.BodyType.StaticBody;
            bdef.position.set((rect.getX()+ rect.getWidth()/ 2) / PPM ,(rect.getY() + rect.getHeight()/ 2) / PPM);
            body = world.createBody(bdef);
                //PPM = Pixels Per Meter
            shape.setAsBox(rect.getWidth() / 2/ PPM, rect.getHeight() / 2/ PPM);
            fdef.shape = shape;
            fdef.filter.groupIndex = 1;

            body.createFixture(fdef);
        }


    }

    public void update(float delta){


        world.step( 1/60f , 6, 2);

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

        //Render light
        rayHandler.setCombinedMatrix(cam);
        rayHandler.updateAndRender();
    }
    public void handleInput(float delta){

        player.body.setLinearDamping(4f);

        int speed = (int) (25 * PPM);
        int maxVel = 50;
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
            if(Gdx.input.isKeyJustPressed(Input.Keys.SPACE)){
                createBox( (int) (player.body.getPosition().x * PPM),(int)(player.body.getPosition().y *PPM));
            }
        }

        //Other input
        if(Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)){
            if(paused)
                resume();
            else
                pause();
        }
        //player.body.setLinearVelocity(vX,vY);
        Vector2 v = player.body.getLinearVelocity();

        player.body.applyForceToCenter( v.x >=0 ? (v.x < maxVel * PPM ? vX : 0) : (v.x > -maxVel * PPM ? vX : 0) , v.y >=0 ? (v.y < maxVel * PPM ? vY : 0) : (v.y > -maxVel * PPM ? vY : 0),true);
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
        map.dispose();
        otmr.dispose();

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
}
