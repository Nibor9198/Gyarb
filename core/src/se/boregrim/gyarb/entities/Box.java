package se.boregrim.gyarb.entities;

import com.badlogic.gdx.physics.box2d.*;

import static se.boregrim.gyarb.utils.Constants.PPM;

/**
 * Created by robin.boregrim on 2016-11-15.
 */
public class Box implements Entity {
    Body body;

    public Box(World world, int x, int y){
        BodyDef bdef = new BodyDef();
        FixtureDef fdef = new FixtureDef();
        PolygonShape shape = new PolygonShape();

        bdef.position.set(x / PPM, y / PPM);
        bdef.type = BodyDef.BodyType.DynamicBody;
        bdef.active = true;

        body = world.createBody(bdef);

        //shape.setRadius(10 / PPM);
        shape.setAsBox(10/ PPM, 20 / PPM);
        fdef.shape = shape;
        fdef.restitution = 0.01f;
        fdef.density = 20;
        fdef.filter.groupIndex= 1;


        body.createFixture(fdef);

        shape.setAsBox(10/ PPM, 10 / PPM);

        body.setLinearDamping(8);
        body.setAngularDamping(8);
    }
    @Override
    public void update(float delta) {

    }

    @Override
    public void render(float delta) {

    }
}
