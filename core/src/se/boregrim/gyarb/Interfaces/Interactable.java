package se.boregrim.gyarb.Interfaces;

/**
 * Created by Robin on 2017-01-04.
 */
public interface Interactable {
    public void render(float delta);
    public void update(float delta);
    public void input(boolean paused);
    public boolean isDead();
}
