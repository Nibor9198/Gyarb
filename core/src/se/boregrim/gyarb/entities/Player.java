package se.boregrim.gyarb.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.ai.utils.Location;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g3d.environment.PointLight;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.*;
import se.boregrim.gyarb.screens.GameScreen;
import se.boregrim.gyarb.utils.Constants;
import se.boregrim.gyarb.utils.SteeringUtils;

import static se.boregrim.gyarb.utils.Constants.PPM;

/**
 * Created by Robin on 2016-11-09.
 */
public class Player extends Actor implements Entity, Location<Vector2> {
    public World world;
    public Body body;
    private GameScreen gs;
    private AssetManager manager;


    private float health;

    public Player(GameScreen gs, int x, int y){
        super(gs);
        this.gs = gs;
        world = gs.getWorld();
        manager = gs.getGame().getAssets().getAssetManager();


        createBody(x,y,3,0);
        createFixture(new CircleShape(),12,20,Constants.CAT_ENEMY ,Constants.CAT_WALL | Constants.CAT_EDGE, 1);
        createCollisionSensor(20,(float) (Math.PI /2));

        //setSprite("PlayerSprite.png",32,32);

        //basic(gs,x,y);
        body = super.body;

        setSprite("Player.png",32,32);
        //define(x,y);
    }

    // X and y describes a point towards which the player should be facing, this usually is the mouse
    public void playerFacing(int x, int y){
        //Since the player is always in the middle we can easily get the angle by basing x and y's origin in the center.

        x = x- Gdx.graphics.getWidth()/2;
        y = y- Gdx.graphics.getHeight()/2;
        //Calculating the angle
        float angle =  -MathUtils.atan2(y,x);
        //Transforming the Body
        body.setTransform(body.getPosition().x,body.getPosition().y,angle);
        //setRotation((float) ((angle *360/(2*Math.PI))+90));


    }

    @Override
    public void update(float delta) {

        //if(body.getFixtureList().get(1).
        //Putting sprite ontop of the physical body
        setCenter(body.getPosition().x,body.getPosition().y);
        //getSprite().setOrigin(getSprite().getWidth()/2,getSprite()getHeight()/2);
        super.update(delta);
        //setBounds(0,0,32/PPM, 32/PPM);
        playerFacing(Gdx.input.getX(),Gdx.input.getY());

        //body;
    }

    //Location
    @Override
    public Vector2 getPosition() {
        return body.getPosition();
    }

    @Override
    public float getOrientation() {
        return 0;
    }

    @Override
    public void setOrientation(float orientation) {

    }

    @Override
    public float vectorToAngle(Vector2 vector) {
        return SteeringUtils.vectorToAngle(vector);
    }

    @Override
    public Vector2 angleToVector(Vector2 outVector, float angle) {
        return SteeringUtils.angleToVector(outVector, angle);
    }

    @Override
    public Location<Vector2> newLocation() {
        return null;
    }

    @Override
    public void die() {
        //Loose the game or loose one life
    }
    @Override
    public void input(boolean paused){

        //Setting variables for player movement
        int speed = (int) (15 * PPM);
        float maxVel = 15;
        float vX = 0 ;
        float vY = 0 ;


        // Player Controls
        if(!paused) {
            Vector3 pos = gs.getViewport().getCamera().position;
            if (Gdx.input.isKeyPressed(Input.Keys.RIGHT) || Gdx.input.isKeyPressed(Input.Keys.D)/*&& player.body.getLinearVelocity().x <= 2f */) {
                vX += speed;

               // gs.getViewport().getCamera().position.x = gs.getViewport().getCamera().position.x + 2;
            }
            if (Gdx.input.isKeyPressed(Input.Keys.LEFT) || Gdx.input.isKeyPressed(Input.Keys.A) /*&& player.body.getLinearVelocity().x >= -2f*/) {
                vX += -speed;
                //gs.getViewport().getCamera().position.x = gs.getViewport().getCamera().position.x - 2;
            }
            if (Gdx.input.isKeyPressed(Input.Keys.UP) || Gdx.input.isKeyPressed(Input.Keys.W) /*&& player.body.getLinearVelocity().y <= 2f*/) {
                vY += speed;
                //gs.getViewport().getCamera().position.y = gs.getViewport().getCamera().position.y + 2;
            }
            if (Gdx.input.isKeyPressed(Input.Keys.DOWN) || Gdx.input.isKeyPressed(Input.Keys.S) /*&& player.body.getLinearVelocity().y >= -2f*/) {
                vY += -speed;
               //gs.getViewport().getCamera().position.y = gs.getViewport().getCamera().position.y - 2;
//                gs.getViewport().getCamera().position.y = getY();
//                gs.getViewport().getCamera().position.x = getX();
            }
            if(Gdx.input.isKeyJustPressed(Input.Keys.SHIFT_LEFT)){
                vX = vX * 10;
                vY = vY * 10;
            }
            //Moving the player
            Vector2 v = body.getLinearVelocity();
            maxVel = vX == 0 || vY == 0 ? maxVel: (float) Math.sqrt((Math.pow(maxVel,2))/2) ;
            //System.out.println(maxVel);
            //System.out.println((float) Math.sqrt((Math.pow(maxVel*PPM,2))/2));
            body.applyForceToCenter( v.x >=0 ? (v.x < maxVel ? vX : 0) : (v.x > -maxVel ? vX : 0) , v.y >=0 ? (v.y < maxVel ? vY : 0) : (v.y > -maxVel? vY : 0),true);
            //System.out.println(Math.sqrt(Math.pow(v.x,2) + Math.pow(v.y,2)));



        }
    }


    @Override
    public void render(float delta) {
        super.render(delta);
        SpriteBatch batch = gs.getBatch();
        batch.setProjectionMatrix(gs.getViewport().getCamera().combined);
        batch.begin();
        //batch.draw(getTexture(), body.getPosition().x, body.getPosition().y, 1, 1 );

        batch.end();
    }

    public float getHealth() {
        return health;
    }

    public void damage(float damage){
        health = health - damage;
    }
}
