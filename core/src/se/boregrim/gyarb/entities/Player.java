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

        playerFacing(Gdx.input.getX(),Gdx.input.getY());
        InputProcessor ip = new InputProcessor() {
            @Override
            public boolean mouseMoved(int screenX, int screenY) {
                Player.this.playerFacing(screenX,screenY);
                return true;
            }
            @Override public boolean keyDown(int keycode) {return false;}
            @Override public boolean keyUp(int keycode) {return false;}
            @Override public boolean keyTyped(char character) {return false;}
            @Override public boolean touchDown(int screenX, int screenY, int pointer, int button) {return false;}
            @Override public boolean touchUp(int screenX, int screenY, int pointer, int button) {return false;}
            @Override public boolean touchDragged(int screenX, int screenY, int pointer) {return false;}
            @Override public boolean scrolled(int amount) {return false;}
        };

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
    public void playerFacing(int x, int y){
        //float angle = MathUtils.radiansToDegrees * MathUtils.atan2()
    } //http://stackoverflow.com/questions/30963901/box2d-body-to-follow-mouse-movement
    //http://stackoverflow.com/questions/16381031/get-cursor-position-in-libgdx

    @Override
    public void update(float delta) {
        setBounds(body.getPosition().x, body.getPosition().y,2,2);
        //body;
    }

    @Override
    public void render(float delta) {

    }


}
