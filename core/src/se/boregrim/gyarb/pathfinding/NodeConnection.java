package se.boregrim.gyarb.pathfinding;

import com.badlogic.gdx.ai.pfa.Connection;

/**
 * Created by Robin on 2016-12-13.
 */
public class NodeConnection implements Connection<Node> {
    private Node toNode;
    private Node fromNode;
    private float cost;
    public NodeConnection(Node fromNode, Node toNode, float cost){
        this.fromNode = fromNode;
        this.toNode = toNode;
        this.cost = cost;
    }

    @Override
    public float getCost() {
        return cost;
    }

    @Override
    public Node getFromNode() {
        return fromNode;
    }

    @Override
    public Node getToNode() {
        return toNode;
    }
}
