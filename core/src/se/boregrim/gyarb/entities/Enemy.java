package se.boregrim.gyarb.entities;

import com.badlogic.gdx.ai.utils.Location;
import com.badlogic.gdx.math.Vector2;
import se.boregrim.gyarb.screens.GameScreen;

/**
 * Created by Robin on 2017-01-22.
 */
public class Enemy extends AiEntity {

    float damage;
    boolean attacking;
    long attackspeed, timestamp;


    public Enemy(GameScreen gs, float boundingRadius, Location<Vector2> target) {
        super(gs, boundingRadius, target);
        damage = 30;
        attackspeed = 2000;
        timestamp = 0;

    }

    @Override
    public void update(float delta) {
        super.update(delta);
        if(attacking && (System.currentTimeMillis() - timestamp) > attackspeed){
            gs.getPlayer().damage(damage);
            System.out.println("attack");
            timestamp = System.currentTimeMillis();
        }

    }
    public void setAttacking(boolean attacking) {
        this.attacking = attacking;
    }

    public void setDamage(float damage) {
        this.damage = damage;
    }

    public void setAttackspeed(long attackspeed) {
        this.attackspeed = attackspeed;
    }
}
