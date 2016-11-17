package se.boregrim.gyarb.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.scenes.scene2d.InputListener;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

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
        shape.setAsBox(10 / PPM, 20 / PPM);
        fdef.shape = shape;
        fdef.density = 20;
        fdef.restitution =2;
        body.createFixture(fdef);


        shape.setAsBox(10 / PPM, 10 / PPM);
        body.setLinearDamping(4f);

    }
    public void playerFacing(int x, int y){
        x = x- Gdx.graphics.getWidth()/2;
        y = y- Gdx.graphics.getHeight()/2;
        float angle =  -MathUtils.atan2(y,x);
        body.setTransform(getX(),getY(),angle);
        //System.out.println(angle);
    } //http://stackoverflow.com/questions/30963901/box2d-body-to-follow-mouse-movement
    //http://stackoverflow.com/questions/16381031/get-cursor-position-in-libgdx

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
