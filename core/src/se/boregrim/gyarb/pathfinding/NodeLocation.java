package se.boregrim.gyarb.pathfinding;

import com.badlogic.gdx.ai.utils.Location;
import com.badlogic.gdx.math.Vector;
import com.badlogic.gdx.math.Vector2;
import se.boregrim.gyarb.managers.MapManager;
import se.boregrim.gyarb.utils.SteeringUtils;

/**
 * Created by Robin on 2017-01-06.
 */
public class NodeLocation implements com.badlogic.gdx.ai.utils.Location<Vector2> {
    Vector2 v;
    public NodeLocation(Node n){
        v = new Vector2(n.getIndex()% MapManager.mapTileWidth,n.getIndex()/ MapManager.mapTileWidth );
    }
    public void changeLocation(Node n){
        v = new Vector2(n.getIndex()% MapManager.mapTileWidth,n.getIndex()/ MapManager.mapTileWidth );
    }
    @Override
    public Vector2 getPosition() {
        return v;
    }

    @Override
    public float getOrientation() {
        return 0;
    }

    @Override
    public void setOrientation(float orientation) {

    }

    @Override
    public float vectorToAngle(Vector2 vector) {
        return SteeringUtils.vectorToAngle(vector);
    }

    @Override
    public Vector2 angleToVector(Vector2 outVector, float angle) {
        return SteeringUtils.angleToVector(outVector,angle);
    }

    @Override
    public com.badlogic.gdx.ai.utils.Location newLocation() {
        return null;
    }
}
