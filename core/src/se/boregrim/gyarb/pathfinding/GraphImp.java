package se.boregrim.gyarb.pathfinding;

import com.badlogic.gdx.ai.pfa.Connection;
import com.badlogic.gdx.ai.pfa.indexed.IndexedGraph;
import com.badlogic.gdx.utils.Array;
import se.boregrim.gyarb.managers.MapManager;

import java.util.ArrayList;

/**
 * Created by Robin on 2016-12-08.
 */
public class GraphImp implements IndexedGraph<Node>{
    protected Array<Node> nodes = new Array<Node>();
    protected int capacity;

    public GraphImp(){
        super();
    }
    public GraphImp(int capacity){
        this.capacity = capacity;
    }
    public GraphImp(Array<Node> nodes) {
        this.nodes = nodes;

        for (int x = 0; x < nodes.size; ++x) {
            nodes.get(x).index = x;
        }
    }
    @Override
    public int getIndex(Node node) {
        return nodes.indexOf(node,true);
    }

    @Override
    public int getNodeCount() {
        return nodes.size;
    }

    @Override
    public Array<Connection<Node>> getConnections(Node fromNode) {
        return fromNode.getConnections();
    }

    public Array<Node> getNodes(){return nodes;}
    public Node getNodeByPos(int x, int y){
        return nodes.get(MapManager.mapTileWidth * y + x);

    }
}
