package se.boregrim.gyarb.utils;

import com.badlogic.gdx.math.Vector2;

/**
 * Created by robin.boregrim on 2016-12-02.
 */
public class SteeringUtils {

    public static float vectorToAngle(Vector2 v){
        return (float) Math.atan2(-v.x,v.y);
    }

    public static Vector2 angleToVector(Vector2 outVector, float angle){
        outVector.x = (float) -Math.sin(angle);
        outVector.y = (float) Math.cos(angle);
        return outVector;
    }
}
