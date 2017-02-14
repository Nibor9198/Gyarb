package se.boregrim.gyarb.managers;

import com.badlogic.gdx.math.Vector2;
import se.boregrim.gyarb.Interfaces.Interactable;
import se.boregrim.gyarb.entities.AiEntity;
import se.boregrim.gyarb.entities.TestEnemy;
import se.boregrim.gyarb.screens.GameScreen;

import java.util.ArrayList;
import java.util.Random;

import static se.boregrim.gyarb.utils.Constants.PPM;

/**
 * Created by robin.boregrim on 2017-02-14.
 */
public class SpawnManager implements Interactable {
    GameScreen gs;
    long timestamp;
    boolean started, newWave;


    long diff;


    Random rand = new Random();

    public  SpawnManager(GameScreen gameScreen){
        gs = gameScreen;
        timestamp = System.currentTimeMillis();
        diff= 0;
        newWave = true;

    }
    @Override
    public void render(float delta) {

    }

    @Override
    public void update(float delta) {
        if(started || (System.currentTimeMillis()-timestamp) > 3000){
            if(!started) {
                gs.nexWave();
                started = true;
                newWave = true;
                timestamp = System.currentTimeMillis();

            }else if((System.currentTimeMillis()-timestamp) < 30000 && ((gs.getEnemies().size() > 0) || newWave)){

                if (((System.currentTimeMillis()-timestamp) < 10000 && rand.nextInt(100) <= gs.getWaveCount())|| newWave ){
                    spawnEnemy();
                }
                newWave = false;
            }else{
                gs.nexWave();
                newWave = true;
                timestamp = System.currentTimeMillis();
            }

        }
    }

    @Override
    public void input(boolean paused) {
        if(paused && diff == 0)
            diff = System.currentTimeMillis()- timestamp;
        else if (!paused && diff != 0){
            timestamp = System.currentTimeMillis() - diff;
            diff = 0;
        }
    }

    @Override
    public boolean isDead() {
        return false;
    }

    public void spawnEnemy(){
        Vector2 pos = MapManager.enemySpawn();
        new TestEnemy(gs, (int) (pos.x * PPM), (int) (pos.y * PPM), 25, gs.getPlayer());
    }
}
