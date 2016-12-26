package se.boregrim.gyarb.pathfinding;

import com.badlogic.gdx.ai.pfa.indexed.IndexedAStarPathFinder;
import com.badlogic.gdx.ai.pfa.indexed.IndexedGraph;

/**
 * Created by Robin on 2016-12-08.
 */
public class Pathfinder extends IndexedAStarPathFinder{
    public Pathfinder(IndexedGraph graph) {
        super(graph);
    }

}
