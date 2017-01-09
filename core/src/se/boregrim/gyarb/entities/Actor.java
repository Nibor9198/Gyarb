package se.boregrim.gyarb.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.*;
import se.boregrim.gyarb.screens.GameScreen;
import se.boregrim.gyarb.utils.Constants;

import static se.boregrim.gyarb.utils.Constants.CAT_EDGE;
import static se.boregrim.gyarb.utils.Constants.CAT_ENEMY;
import static se.boregrim.gyarb.utils.Constants.PPM;

/**
 * Created by robin.boregrim on 2016-11-25.
 */
public class Actor extends Sprite implements Entity {
    GameScreen gs;
    World world;
    SpriteBatch batch;
    boolean hasBody;
    public Body body;
    public Actor(GameScreen gs) {
        super();
        this.gs = gs;
        world = gs.getWorld();
        batch = new SpriteBatch();
        hasBody = false;
        //this.body = body;
        //body = gs.createEBody(0, x,y,CAT_ENEMY, CAT_EDGE);

        //createBody(x,y);
        //createFixture(new CircleShape(),12,20,Constants.CAT_ENEMY ,Constants.CAT_WALL | Constants.CAT_EDGE, 1);
        //createCollisionSensor((float) (Math.PI /2));

    }
    public void createBody(int x, int y, float lDamping, float aDamping) {
        //Creating player body

        BodyDef bdef = new BodyDef();

        bdef.position.set(x / PPM, y / PPM);

        bdef.type = BodyDef.BodyType.DynamicBody;
        body = world.createBody(bdef);
        body.setLinearDamping(lDamping);
        body.setAngularDamping(aDamping);
        hasBody = true;
    }
    public void createFixture(CircleShape shape, float radius, float density, int catBits, int maskBits, int groupIndex) {
        //Creating Player fixture
        FixtureDef fdef = new FixtureDef();
        shape.setRadius(radius / PPM);
        fdef.shape = shape;
        fdef.density = density;
        fdef.filter.categoryBits = (short) catBits;
        fdef.filter.maskBits = (short) maskBits;
        fdef.filter.groupIndex = (short) groupIndex;
        body.createFixture(fdef).setUserData("Player");
    }
    public void createCollisionSensor(float radius,float angle){
        //Creating Hit Sensor fixture
        FixtureDef fdef = new FixtureDef();
        PolygonShape shape = new PolygonShape();

        radius = radius / PPM;
        Vector2 verticles[] = new Vector2[7];
        verticles[0] = new Vector2(0/PPM,0/PPM);
        for (int i = 0; i < verticles.length -1; i++) {
            float part = angle * 2 / (verticles.length -2);
            //.out.println(part);
            verticles[i+1] = new Vector2((float) Math.cos(angle - part * i) * radius ,(float) Math.sin(angle - part * i) * radius);
        }

        shape.setRadius(radius);
        shape.set(verticles);
        fdef.shape = shape;
        fdef.density = 0;
        fdef.filter.groupIndex = -1;
        fdef.isSensor = true;

        body.createFixture(fdef).setUserData("hitSensor");


        //Set bounds


    }
    public void facePosition(Vector2 position){
        facePosition(position.x, position.y);
    }
    public void facePosition(float x, float y){
        //Calculating delta
        x = x - body.getPosition().x;
        y = y - body.getPosition().y;
        //Calculating the angle
        float angle =  -MathUtils.atan2(y,x);
        //Rotating the Body
        body.setTransform(body.getPosition().x,body.getPosition().y,angle);
        //Rotating Sprite
        setRotation((float) ((angle *360/(2*Math.PI))+90));
    }
    //Set Sprite texture and size
    public void setSprite(String name, int  height, int  width){
        setTexture(gs.getGame().getAssets().getAssetManager().get(name, Texture.class));
        setBounds(body.getPosition().x, body.getPosition().y,height/PPM,width/PPM);
        setSize(width/PPM,height/PPM);
    }
    @Override
    public void update(float delta) {
        //if(hasBody && body !=null) {
            setCenter(body.getPosition().x, body.getPosition().y);

        //}
        //setCenter(0,0);
        //setOrigin(getWidth()/2,getHeight()/2);
        //Vector3 v = gs.getViewport().getCamera().position;
        //v.set(getX()- v.x,getY()-v.y,v.z);
    }

    @Override
    public void input(boolean paused) {

    }


    @Override
    public void render(float delta) {
        if(getTexture() != null) {
            batch.begin();
            batch.setProjectionMatrix(gs.getViewport().getCamera().combined);
            draw(batch);
            //batch.draw(getTexture(), body.getPosition().x - getWidth(), body.getPosition().y - getHeight(), getWidth(), getHeight());

            batch.end();
        }
    }

    @Override
    public void die() {
        world.destroyBody(body);
        gs.removeEntity(this);
    }
}
