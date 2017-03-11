package se.boregrim.gyarb.managers;

/**
 * Created by Robin on 2016-12-11.
 */
public class ThreadManager {

    // This file is meant for later managing the AI's pathfinding and limit them from calculating everything every single frame.
    // Instead their calculations will be time based and spread out over different frames, this will boost the games preformance slightly.
    // This feature is not necessary for this product to run there for implementation of this feature has not been prioritized.
    public class PathfindingThread implements Runnable {

        @Override
        public void run() {

        }
    }
}
