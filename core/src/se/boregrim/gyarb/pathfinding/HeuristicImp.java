package se.boregrim.gyarb.pathfinding;

import com.badlogic.gdx.ai.pfa.Heuristic;
import se.boregrim.gyarb.managers.MapManager;

/**
 * Created by Robin on 2016-12-26.
 */
public class HeuristicImp implements Heuristic<Node>{
    @Override
    public float estimate(Node node, Node endNode) {
        int startIndex = node.getIndex();
        int endIndex = endNode.getIndex();

        int startY = startIndex / MapManager.mapTileWitdh;
        int startX = startIndex % MapManager.mapTileWitdh;

        int endY = endIndex / MapManager.mapTileWitdh;
        int endX = endIndex % MapManager.mapTileWitdh;

        float distance = (float) Math.sqrt(Math.pow(endX - startX, 2) + Math.pow(endY - startY, 2));

        return distance;
    }
}
