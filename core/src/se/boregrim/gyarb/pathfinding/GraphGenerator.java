package se.boregrim.gyarb.pathfinding;

import com.badlogic.gdx.ai.pfa.Graph;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.utils.Array;
import se.boregrim.gyarb.managers.MapManager;

/**
 * Created by Robin on 2016-12-08.
 */
public class GraphGenerator {
    public static Graph generateGraph(TiledMap map){
        //Array<Node> nodes = new Array<Node>();

        TiledMapTileLayer tiles = (TiledMapTileLayer)map.getLayers().get(0);
        int mapHeight = MapManager.mapTileHeight;
        int mapWidth = MapManager.mapTileWitdh;

        for (int y = 0; y < mapHeight; y++) {
            for (int x = 0; x < mapWidth; x++) {

                //https://github.com/libgdx/gdx-ai/wiki/Hierarchical-Pathfinding
                //https://www.youtube.com/watch?v=wu3vzR9k3QA
            }
        }





        return null;
    }
}
