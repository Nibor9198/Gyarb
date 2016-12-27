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

        int startY = startIndex / MapManager.mapTileWidth;
        int startX = startIndex % MapManager.mapTileWidth;

        int endY = endIndex / MapManager.mapTileWidth;
        int endX = endIndex % MapManager.mapTileWidth;

        float distance = (float) Math.sqrt(Math.pow(endX - startX, 2) + Math.pow(endY - startY, 2));

        return distance;
    }
}
