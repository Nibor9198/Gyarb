package se.boregrim.gyarb.Listeners;

import com.badlogic.gdx.physics.box2d.*;
import se.boregrim.gyarb.entities.AiEntity;
import se.boregrim.gyarb.entities.Entity;
import se.boregrim.gyarb.entities.TestEnemy;
import se.boregrim.gyarb.screens.GameScreen;

/**
 * Created by robin.boregrim on 2017-01-10.
 */
public class B2dContactListener implements ContactListener {
    private GameScreen gs;
    public B2dContactListener(GameScreen gs){
        this.gs = gs;
    }
    @Override
    public void beginContact(Contact contact) {
        if(contact.getFixtureA().equals(gs.getPlayer().body.getFixtureList().get(0))){
            damagePlayer(contact.getFixtureB(), true);
        }else if(contact.getFixtureB().equals(gs.getPlayer().body.getFixtureList().get(0))){
            damagePlayer(contact.getFixtureA(), true);
        }
    }

    private void damagePlayer(Fixture enemy, boolean attacking){
        for (Entity e: gs.getEntities()) {
            // Funkar inte
            if(e instanceof  TestEnemy && enemy.getUserData() instanceof AiEntity) {
                System.out.println("1");
                if (((AiEntity) e).equals((AiEntity) enemy.getUserData())) {
                    System.out.println("2");
                    ((AiEntity) e).setAttacking(attacking);
                }
            }
        }

    }

    @Override
    public void endContact(Contact contact) {
        if(contact.getFixtureA().equals(gs.getPlayer().body.getFixtureList().get(0))){
            damagePlayer(contact.getFixtureB(), false);
        }else if(contact.getFixtureB().equals(gs.getPlayer().body.getFixtureList().get(0))){
            damagePlayer(contact.getFixtureA(), false);
        }
    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {

    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {

    }
}
