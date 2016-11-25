package se.boregrim.gyarb.entities;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import se.boregrim.gyarb.screens.GameScreen;

import static se.boregrim.gyarb.utils.Constants.CAT_EDGE;
import static se.boregrim.gyarb.utils.Constants.CAT_ENEMY;

/**
 * Created by robin.boregrim on 2016-11-25.
 */
public class Enemy extends Sprite implements Entity {
    GameScreen gs;
    World world;
    Body body;
    public Enemy(GameScreen gs, int x, int y){
        this.gs = gs;
        //world = gs.getWorld();
        //body = gs.createEBody(0, x,y,CAT_ENEMY, CAT_EDGE);

    }
    @Override
    public void update(float delta) {

    }

    @Override
    public void render(float delta) {

    }
}
