package se.boregrim.gyarb.entities;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.CircleShape;
import se.boregrim.gyarb.screens.GameScreen;
import se.boregrim.gyarb.utils.Constants;

/**
 * Created by Robin on 2016-12-08.
 */
public class TestEnemy extends Enemy{

    public TestEnemy(GameScreen gs, int x, int y, float boundingRadius, Player player) {
        super(gs, boundingRadius, player);

        createBody(x,y,3,3);
        createFixture(new CircleShape(),12,10, Constants.CAT_ENEMY | Constants.CAT_ENTITY ,Constants.CAT_EDGE| Constants.CAT_LIGHT | Constants.CAT_SHOT,1);
        createCollisionSensor(18, (float) (Math.PI/2));

        setSprite("Enemy.png",28,28);
        defaultSteering();

    }

}
