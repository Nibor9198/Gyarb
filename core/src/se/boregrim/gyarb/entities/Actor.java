package se.boregrim.gyarb.entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
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
    public Body body;
    public Actor(GameScreen gs,int x, int y) {
        this.gs = gs;
        world = gs.getWorld();
        //this.body = body;
        //body = gs.createEBody(0, x,y,CAT_ENEMY, CAT_EDGE);

        createBody(x,y);
        createFixture(new CircleShape(),12,20,Constants.CAT_ENEMY ,Constants.CAT_WALL | Constants.CAT_EDGE, 1);
        createCollisionSensor((float) (Math.PI /2));

        //Setting LinearDamping

    }
    private void createBody(int x , int y) {
        //Creating player body
        BodyDef bdef = new BodyDef();

        bdef.position.set(x / PPM, y / PPM);
        bdef.type = BodyDef.BodyType.DynamicBody;
        body = world.createBody(bdef);
        body.setLinearDamping(3f);
        setBounds(body.getPosition().x, body.getPosition().y,32/PPM,32/PPM);
    }
    private void createFixture(CircleShape shape, float radius, float density, int catBits, int maskBits, int groupIndex) {
        //Creating Player fixture
        FixtureDef fdef = new FixtureDef();
        shape = new CircleShape();
        shape.setRadius(radius / PPM);
        fdef.shape = shape;
        fdef.density = density;
        fdef.filter.categoryBits = (short) catBits;
        fdef.filter.maskBits = (short) maskBits;
        fdef.filter.groupIndex = (short) groupIndex;
        body.createFixture(fdef).setUserData("Player");
    }
    private void createCollisionSensor(float angle){
        //Creating Hit Sensor fixture
        FixtureDef fdef = new FixtureDef();
        PolygonShape shape = new PolygonShape();

        float radius = 20 / PPM;
        Vector2 verticles[] = new Vector2[7];
        verticles[0] = new Vector2(0/PPM,0/PPM);
        //float angle = (float) (Math.PI /2);
        //System.out.println(verticles[1] = new Vector2((float) Math.cos(angle) * radius ,(float) Math.sin(angle) * radius));
        //verticles[2] = new Vector2((float) Math.cos(angle/2) * radius ,(float) Math.sin(angle/2) * radius);
        for (int i = 0; i < verticles.length -1; i++) {
            float part = angle * 2 / (verticles.length -2);
            System.out.println(part);
            System.out.println(verticles[i+1] = new Vector2((float) Math.cos(angle - part * i) * radius ,(float) Math.sin(angle - part * i) * radius));
        }


        //verticles[3] = new Vector2((float) Math.cos(-angle/2) * radius ,(float) Math.sin(-angle/2) * radius);
        //System.out.println(verticles[4] = new Vector2((float) Math.cos(-angle) * radius,(float) Math.sin(-angle) * radius));
        shape.setRadius(radius);
        shape.set(verticles);
        fdef.shape = shape;
        fdef.density = 0;
        fdef.filter.groupIndex = -1;
        fdef.isSensor = true;

        body.createFixture(fdef).setUserData("hitSensor");


        //Set bounds


    }

    public void setSprite(String name){
        setTexture((Texture) gs.getGame().getAssets().getAssetManager().get(name));
    }
    @Override
    public void update(float delta) {
        setCenter(body.getPosition().x,body.getPosition().y);
        setOrigin(getWidth()/2,getHeight()/2);
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
