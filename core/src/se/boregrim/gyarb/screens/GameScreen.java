package se.boregrim.gyarb.screens;

import box2dLight.ConeLight;
import box2dLight.PointLight;
import box2dLight.RayHandler;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.*;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
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
import se.boregrim.gyarb.entities.*;
import se.boregrim.gyarb.utils.Constants;


import java.awt.*;
import java.awt.event.MouseListener;
import java.util.ArrayList;

import static com.badlogic.gdx.utils.JsonValue.ValueType.object;
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
    SpriteBatch batch;


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


    //Ui
    GameUiScreen ui;
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

        //Batch
        batch = new SpriteBatch();

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

        player = new Player(this, (int)vp.getWorldWidth()/2,(int)vp.getWorldHeight()/2);
        entities.add(player);

        //Lighting
        rayHandler = new RayHandler(world);
        rayHandler.diffuseBlendFunc.set(GL20.GL_SRC_COLOR,GL20.GL_DST_COLOR);
        rayHandler.setAmbientLight(0.04f);
        //PointLight p = new PointLight(rayHandler, 2000, Color.CYAN, 8,0,0);
        ConeLight p = new ConeLight(rayHandler,1000,Color.CYAN,8,0,0,0, (float) (75));

        p.attachToBody(player.body);
        //p.setSoft(true);
        //p.setSoftnessLength(500000);

        Filter filter = new Filter();
        filter.categoryBits = Constants.CAT_LIGHT;
        filter.maskBits = Constants.CAT_WALL | CAT_ENTITY;
        filter.groupIndex = 1;
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
            fdef.filter.categoryBits = CAT_EDGE;
            fdef.filter.maskBits = CAT_PLAYER | CAT_ENEMY | CAT_ENTITY;
            filter.groupIndex = -1;
            //fdef.filter.groupIndex = 1;

            body.createFixture(fdef);
        }

        //Ui
        ui = new GameUiScreen(this);
        ui.show();
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

        //Entities
        renderEntities(delta);

        //Render light
        rayHandler.setCombinedMatrix(cam);
        rayHandler.updateAndRender();



        //Ui
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
            if(Gdx.input.isKeyJustPressed(Input.Keys.SPACE)){
                createBox( (int) (player.body.getPosition().x * PPM),(int)(player.body.getPosition().y *PPM));
            }if(Gdx.input.isKeyJustPressed(Input.Keys.K)){
                addEntity(new Enemy(this, (int) (player.body.getPosition().x * PPM), (int) (player.body.getPosition().y * PPM),25));
            }
        }

        //Moving the player
        Vector2 v = player.body.getLinearVelocity();
        maxVel = vX == 0 || vY == 0 ? maxVel: (float) Math.sqrt((Math.pow(maxVel,2))/2) ;
        //System.out.println(maxVel);
        //System.out.println((float) Math.sqrt((Math.pow(maxVel*PPM,2))/2));
        player.body.applyForceToCenter( v.x >=0 ? (v.x < maxVel ? vX : 0) : (v.x > -maxVel ? vX : 0) , v.y >=0 ? (v.y < maxVel ? vY : 0) : (v.y > -maxVel? vY : 0),true);
        //System.out.println(Math.sqrt(Math.pow(v.x,2) + Math.pow(v.y,2)));


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
}
