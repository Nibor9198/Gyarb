package se.boregrim.gyarb.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import se.boregrim.gyarb.screens.GameScreen;
import se.boregrim.gyarb.utils.Constants;

import static se.boregrim.gyarb.utils.Constants.PPM;

/**
 * Created by Robin on 2016-11-09.
 */
public class Player extends Sprite implements Entity{
    public World world;
    public Body body;
    private GameScreen gs;
    private AssetManager manager;
    public Sprite legs;

    public Player(GameScreen gs, int x, int y){
        super((Texture) gs.getGame().getAssets().getAssetManager().get("PlayerSprite.png"));
        this.gs = gs;
        world = gs.getWorld();
        manager = gs.getGame().getAssets().getAssetManager();




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
        fdef.filter.groupIndex = 1;
        body.createFixture(fdef).setUserData("Player");

        //Setting LinearDamping
        body.setLinearDamping(3f);

        //Creating Hit Sensor fixture
        PolygonShape shape2 = new PolygonShape();





        float radius = 20 / PPM;
        Vector2 verticles[] = new Vector2[7];
        verticles[0] = new Vector2(0/PPM,0/PPM);
        float angle = (float) (Math.PI /2);
        //System.out.println(verticles[1] = new Vector2((float) Math.cos(angle) * radius ,(float) Math.sin(angle) * radius));
        //verticles[2] = new Vector2((float) Math.cos(angle/2) * radius ,(float) Math.sin(angle/2) * radius);
        for (int i = 0; i < verticles.length -1; i++) {
            float part = angle * 2 / (verticles.length -2);
            System.out.println(part);
            System.out.println(verticles[i+1] = new Vector2((float) Math.cos(angle - part * i) * radius ,(float) Math.sin(angle - part * i) * radius));
        }


        //verticles[3] = new Vector2((float) Math.cos(-angle/2) * radius ,(float) Math.sin(-angle/2) * radius);
        //System.out.println(verticles[4] = new Vector2((float) Math.cos(-angle) * radius,(float) Math.sin(-angle) * radius));
        shape2.setRadius(radius);
        shape2.set(verticles);
        fdef.shape = shape2;
        fdef.density = 0;
        fdef.filter.groupIndex = -1;
        fdef.isSensor = true;

        body.createFixture(fdef).setUserData("hitSensor");


        //Set bounds
        setBounds(body.getPosition().x, body.getPosition().y,32/PPM,32/PPM);

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
        //Putting sprite
        setCenter(body.getPosition().x,body.getPosition().y);
        setOrigin(getWidth()/2,getHeight()/2);

        //setBounds(0,0,32/PPM, 32/PPM);
        playerFacing(Gdx.input.getX(),Gdx.input.getY());

        //body;
    }

    @Override
    public void render(float delta) {
        SpriteBatch batch = gs.getBatch();
        batch.setProjectionMatrix(gs.getViewport().getCamera().combined);
        batch.begin();
        draw(batch);
        batch.end();
    }


}
