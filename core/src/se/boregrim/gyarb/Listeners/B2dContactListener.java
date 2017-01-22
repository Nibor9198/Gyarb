package se.boregrim.gyarb.Listeners;

import com.badlogic.gdx.physics.box2d.*;
import se.boregrim.gyarb.entities.*;
import se.boregrim.gyarb.Interfaces.Entity;
import se.boregrim.gyarb.screens.GameScreen;

/**
 * Created by robin.boregrim on 2017-01-10.
 */
public class B2dContactListener implements ContactListener {
    //This is where the collisions of the box2d physics engine are handled

    private GameScreen gs;
    private int counter;
    public B2dContactListener(GameScreen gs){
        this.gs = gs;
    }
    @Override
    public void beginContact(Contact contact) {

        //Checking if one of the colliding fixtures are the players body
        //if(contact.getFixtureA().equals(gs.getPlayer().body.getFixtureList().get(0))){
        //    damagePlayer(contact.getFixtureB(), true);
        //}else if(contact.getFixtureB().equals(gs.getPlayer().body.getFixtureList().get(0))){
        //    damagePlayer(contact.getFixtureA(), true);
        //}

        Fixture f;
        if((f = checkContactFixture(contact,gs.getPlayer().body.getFixtureList().get(0))) != null){
            damagePlayer(f, true);
        }
        for (Entity e: gs.getEntities()) {
            if (e instanceof Shot){
                if((f = checkContactFixture(contact,((Shot) e).body.getFixtureList().get(0))) != null){
                    ((Shot) e).onHit(f);
                }
            }
        }




    }

    private Entity findEntity(Contact contact, Fixture outFixture ){
        for (Entity e: gs.getEntities()) {
                if((outFixture = checkContactFixture(contact,((Actor) e).body.getFixtureList().get(0))) != null){
                    return e;
                }

        }
        return null;
    }

    private Fixture checkContactFixture(Contact contact, Fixture fixture){
        if(contact.getFixtureA().equals(fixture)){
            return contact.getFixtureB();
        }
        else if(contact.getFixtureB().equals(fixture)){
            return contact.getFixtureA();
        }
        else {
            return null;
        }

    }

    private void damagePlayer(Fixture enemy, boolean attacking){
        //For each Entity
        for (Entity e: gs.getEntities()) {
                //That is a
                if(e instanceof Enemy) {
                if(((Enemy) e).getBody().getFixtureList().get(1).equals(enemy)) {
                    //System.out.println("1");
                    ((Enemy) e).setAttacking(attacking);
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
