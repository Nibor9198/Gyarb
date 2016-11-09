package se.boregrim.gyarb.entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.*;

import static se.boregrim.gyarb.utils.Constants.PPM;

/**
 * Created by Robin on 2016-11-09.
 */
public class Player extends Sprite implements Entity{
    public World world;
    public Body body;
    public Sprite legs;

    public Player(World world, int x, int y){
        this.world = world;

        define(x,y);

    }
    public void define(int x, int y){
        BodyDef bdef = new BodyDef();
        FixtureDef fdef = new FixtureDef();
        PolygonShape shape = new PolygonShape();

        bdef.position.set(x / PPM, y / PPM);
        bdef.type = BodyDef.BodyType.DynamicBody;
        body = world.createBody(bdef);

        //shape.setRadius(10 / PPM);
        shape.setAsBox(20/ PPM, 10 / PPM);
        fdef.shape = shape;
        body.createFixture(fdef);

        shape.setAsBox(10/ PPM, 10 / PPM);


    }

    @Override
    public void update(float delta) {
        setBounds(body.getPosition().x, body.getPosition().y,2,2);
        //body;
    }
}
