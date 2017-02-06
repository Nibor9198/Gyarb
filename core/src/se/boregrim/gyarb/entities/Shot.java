package se.boregrim.gyarb.entities;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.Fixture;
import se.boregrim.gyarb.Interfaces.Entity;
import se.boregrim.gyarb.screens.GameScreen;
import se.boregrim.gyarb.utils.Constants;

import static se.boregrim.gyarb.utils.Constants.PPM;

/**
 * Created by Robin on 2017-01-22.
 */
public class Shot extends Actor {
    private long timestamp;

    public Shot (GameScreen gs, Vector2 pos, Vector2 velocity, boolean needPPM){
        this(gs,pos.x, pos.y,velocity, needPPM);

    }

    public Shot(GameScreen gs, float x, float y, Vector2 velocity, boolean needPPM) {
        super(gs);
        timestamp = System.currentTimeMillis();
        if(needPPM){
            x = (int) (x*PPM);
            y = (int) (y*PPM);

        }
        createBody(x, y, 0, 0);
        createFixture(new CircleShape(), 3, 100, Constants.CAT_SHOT | Constants.CAT_ENTITY ,Constants.CAT_WALL | Constants.CAT_ENTITY
        , 0);
        body.getFixtureList().get(0).setRestitution(0.2f);

        body.applyForceToCenter(velocity, true);

    }

    @Override
    public void update(float delta) {
        super.update(delta);
        if((System.currentTimeMillis()-timestamp) > 2000)
            die();
    }

    public void onHit(Fixture target){
        try {

            for (Entity e: gs.getEntities()) {
                if (e instanceof AiEntity) {
                    if (target.equals(((AiEntity) e).getBody().getFixtureList().first())) {
                        ((AiEntity) e).damage(2.5f * body.getLinearVelocity().len());
                         die();
                        break;
                    }
                }
            }
        } catch (NullPointerException e){

        }

    }
}
