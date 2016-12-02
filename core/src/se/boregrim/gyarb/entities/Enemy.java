package se.boregrim.gyarb.entities;

import com.badlogic.gdx.ai.GdxAI;
import com.badlogic.gdx.physics.box2d.CircleShape;
import se.boregrim.gyarb.screens.GameScreen;
import se.boregrim.gyarb.utils.Constants;

/**
 * Created by robin.boregrim on 2016-12-01.
 */
public class Enemy extends Actor {
    public Enemy(GameScreen gs, int x, int y) {
        super(gs);

        createBody(x,y,3,1);
        createFixture(new CircleShape(),10,10, Constants.CAT_ENEMY | Constants.CAT_ENTITY ,Constants.CAT_EDGE| Constants.CAT_LIGHT,1);
        createCollisionSensor(18, (float) (Math.PI/2));
        
    }
}
