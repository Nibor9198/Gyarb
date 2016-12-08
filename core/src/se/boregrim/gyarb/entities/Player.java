package se.boregrim.gyarb.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ai.utils.Location;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g3d.environment.PointLight;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
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
    public Sprite legs;

    public Player(GameScreen gs, int x, int y){
        super(gs);
        this.gs = gs;
        world = gs.getWorld();
        manager = gs.getGame().getAssets().getAssetManager();


        createBody(x,y,3,0);
        createFixture(new CircleShape(),12,20,Constants.CAT_ENEMY ,Constants.CAT_WALL | Constants.CAT_EDGE, 1);
        createCollisionSensor(20,(float) (Math.PI /2));

        setSprite("PlayerSprite.png",32,32);

        //basic(gs,x,y);
        body = super.body;

        setSprite("PlayerSprite.png",32,32);
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

        setRotation((float) ((angle *360/(2*Math.PI))+90));

    }

    @Override
    public void update(float delta) {
        //Putting sprite ontop of the physical body
        //setCenter(body.getPosition().x,body.getPosition().y);
        //setOrigin(getWidth()/2,getHeight()/2);
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
}
