package se.boregrim.gyarb.Listeners;

import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Manifold;
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

    }

    @Override
    public void endContact(Contact contact) {

    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {

    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {

    }
}
