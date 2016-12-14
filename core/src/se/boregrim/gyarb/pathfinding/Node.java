package se.boregrim.gyarb.pathfinding;

import com.badlogic.gdx.ai.pfa.Connection;
import com.badlogic.gdx.utils.Array;

/**
 * Created by Robin on 2016-12-13.
 */
public class Node {
    private Array<Connection<Node>> connections = new Array<Connection<Node>>();
    public int type;
    public int index;

    public Array<Connection<Node>> getConnections() {
        return connections;
    }

    public int getIndex() {
        return index;
    }

    public int getType() {
        return type;
    }

    public void createConnection(Node node, float cost){
        connections.add(new NodeConnection(this, node, cost));
    }
}
