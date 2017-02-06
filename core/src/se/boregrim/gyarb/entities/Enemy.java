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
    long attackspeed, attackCooldown;


    public Enemy(GameScreen gs, float boundingRadius, Location<Vector2> target) {
        super(gs, boundingRadius, target);
        gs.getEnemies().add(this);
        damage = 2;
        attackspeed = 50;
        attackCooldown = 0;

    }

    @Override
    public void update(float delta) {
        super.update(delta);
        if(attacking && (System.currentTimeMillis() - attackCooldown) > attackspeed){
            gs.getPlayer().damage(damage);
            attackCooldown = System.currentTimeMillis();
        }

    }
    @Override
    public void die(){
        super.die();
        gs.getEnemies().remove(this);
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
