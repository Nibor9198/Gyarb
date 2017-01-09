package se.boregrim.gyarb.pathfinding;

import com.badlogic.gdx.ai.pfa.GraphPath;
import com.badlogic.gdx.utils.Array;

import java.util.Iterator;

/**
 * Created by robin.boregrim on 2016-12-16.
 */
public class Path implements GraphPath {
    Array<Node> nodes;

    public Path(){
        nodes = new Array<Node>();
    }

    @Override
    public int getCount() {
        return nodes.size;
    }

    @Override
    public Node get(int index) {
        return nodes.get(index);
    }

    @Override
    public void add(Object node) {
        nodes.add((Node)node);
    }

    @Override
    public void clear() {
        nodes.clear();
    }

    @Override
    public void reverse() {
        nodes.reverse();
    }

    @Override
    public Iterator iterator() {
        return nodes.iterator();
    }
}
