package se.boregrim.gyarb.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import se.boregrim.gyarb.utils.Constants;

import static se.boregrim.gyarb.utils.Constants.PPM;

/**
 * Created by Robin on 2016-11-09.
 */
public class Player extends Sprite implements Entity{
    public World world;
    public Body body;
    public Body Sensor;
    public Sprite legs;

    public Player(World world, int x, int y){
        this.world = world;

        define(x,y);
    }
    public void define(int x, int y){

        //Creating player body
        BodyDef bdef = new BodyDef();

        bdef.position.set(x / PPM, y / PPM);
        bdef.type = BodyDef.BodyType.DynamicBody;
        body = world.createBody(bdef);

        //Creating Player fixture
        FixtureDef fdef = new FixtureDef();
        CircleShape shape = new CircleShape();
        shape.setRadius(12 / PPM);
        fdef.shape = shape;
        fdef.density = 20;
        fdef.filter.categoryBits = Constants.CAT_PLAYER;
        fdef.filter.maskBits = Constants.CAT_WALL | Constants.CAT_EDGE;
        body.createFixture(fdef).setUserData("Player");

        //Setting LinearDamping
        body.setLinearDamping(3f);

        //Creating Hit Sensor fixture
        PolygonShape shape2 = new PolygonShape();




        //ANVÄND BARA TRE PUNKTER
        float radius = 20 / PPM;
        Vector2 verticles[] = new Vector2[2];
        verticles[0] = new Vector2(0,0);
        for (int i = 0; i < 2; i++) {
            float angle = (float) ((i+1) * (Math.PI/(2*4))- 2 *(Math.PI/(2*4)));
            System.out.println(((i+1) * (Math.PI/(2*4))- 2 *(Math.PI/(2*4)))/ Math.PI);
            System.out.println(verticles[i+1] = new Vector2(radius * (float)Math.acos(angle),radius * (float)Math.asin(angle)));
        }
        shape2.set(verticles);
        shape2.setRadius(8);
        fdef.shape = shape2;
        fdef.isSensor = true;
        body.createFixture(fdef).setUserData("hitSensor");


    }

    // X and y describes a point towards which the player should be facing, this usually is the mouse
    public void playerFacing(int x, int y){
        //Since the player is always in the middle we can easily get the angle by basing x and y's origin in the center.

        x = x- Gdx.graphics.getWidth()/2;
        y = y- Gdx.graphics.getHeight()/2;
        //Calculating the angle
        float angle =  -MathUtils.atan2(y,x);
        //Transforming the Body
        body.setTransform(getX(),getY(),angle);

    }

    @Override
    public void update(float delta) {
        setBounds(body.getPosition().x, body.getPosition().y,2,2);
        playerFacing(Gdx.input.getX(),Gdx.input.getY());
        //body;
    }

    @Override
    public void render(float delta) {

    }


}
