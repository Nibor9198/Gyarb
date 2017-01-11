package se.boregrim.gyarb.pathfinding;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ai.btree.decorator.Random;
import com.badlogic.gdx.ai.steer.Steerable;
import com.badlogic.gdx.ai.steer.SteeringAcceleration;
import com.badlogic.gdx.ai.steer.SteeringBehavior;
import com.badlogic.gdx.ai.steer.behaviors.Arrive;
import com.badlogic.gdx.ai.utils.Location;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import se.boregrim.gyarb.entities.AiEntity;

import java.util.ArrayList;

import static se.boregrim.gyarb.utils.Constants.PPM;

/**
 * Created by Robin on 2017-01-06.
 */
public class PathSteering extends Arrive {
    float timestamp;
    ArrayList<NodeLocation> nodes;
    Location target, current;


    public PathSteering(Steerable owner, Location target) {
        super(owner);
        this.target = target;
        timestamp = System.currentTimeMillis();
        nodes = new ArrayList<NodeLocation>();
        nextPath();
    }

    private void nextPath() {
        Path path = ((AiEntity) owner).getOutpath();
        for (Node node : path.nodes) {
            nodes.add(new NodeLocation(node));
        }
        nextLocation();
    }

    private boolean nextLocation() {
        if (nodes.size() > 0) {
            current = nodes.remove(0);
            if (nodes.size() > 0) {
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }
    @Override
    protected SteeringAcceleration calculateRealSteering(SteeringAcceleration steering) {
        //System.out.println(owner.getPosition().dst(target.getPosition()));
            if ((owner.getPosition().dst(target.getPosition()) > 3)) {
                if ((System.currentTimeMillis() - timestamp) < 200) {
                    //System.out.println(owner.getPosition().dst(current.getPosition()));
                    if ((owner.getPosition().dst(current.getPosition()) > 1)) {
                        super.setTarget(current);
                        return super.calculateRealSteering(steering);
                    } else {
                        if (!nextLocation()) {
                            nextPath();
                        }
                    }

                } else {
                    timestamp = System.currentTimeMillis();
                    nextPath();
                }
            } else {
                super.setTarget(target);
                return super.calculateRealSteering(steering);
            }


        return null;
    }
}