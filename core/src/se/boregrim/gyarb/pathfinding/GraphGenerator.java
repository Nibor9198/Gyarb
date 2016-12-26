package se.boregrim.gyarb.pathfinding;

import com.badlogic.gdx.ai.pfa.Graph;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import se.boregrim.gyarb.managers.MapManager;

/**
 * Created by Robin on 2016-12-08.
 */
public class    GraphGenerator {
    public static GraphImp generateGraph(TiledMap map){
        Array<Node> nodes = new Array<Node>();

        TiledMapTileLayer tiles = (TiledMapTileLayer)map.getLayers().get(0);
        int mapHeight = MapManager.mapTileHeight;
        int mapWidth = MapManager.mapTileWitdh;

        for (int y = 0; y < mapHeight; y++) {
            for (int x = 0; x < mapWidth; x++) {
                Node node = new Node();
                node.type = 1;
                nodes.add(node);
                //https://github.com/libgdx/gdx-ai/wiki/Hierarchical-Pathfinding
                //https://www.youtube.com/watch?v=wu3vzR9k3QA
            }
        }

        outerloop:
        for (int y = 0; y < mapHeight; y++) {
            for (int x = 0; x < mapWidth; x++) {
                TiledMapTileLayer.Cell target = tiles.getCell(x, y);
                TiledMapTileLayer.Cell up = tiles.getCell(x, y+1);
                TiledMapTileLayer.Cell left = tiles.getCell(x-1, y);
                TiledMapTileLayer.Cell right = tiles.getCell(x+1, y);
                TiledMapTileLayer.Cell down = tiles.getCell(x, y-1);

                for (Vector2 pos:MapManager.nonWalkablePos) {
                    if(x == pos.x && y == pos.y) {
                        break outerloop;
                    }
                    if(x == pos.x && y+1 == pos.y)
                        up = null;
                    if(x+1 == pos.x && y == pos.y)
                        right = null;
                    if(x == pos.x && y-1 == pos.y)
                        down = null;
                    if(x-1 == pos.x && y == pos.y)
                        left = null;

                }

                Node targetNode = nodes.get(mapWidth * y + x);
                if (target == null) {
                    if (y != 0 && down != null) {
                        Node downNode = nodes.get(mapWidth * (y - 1) + x);
                        targetNode.createConnection(downNode, 1);
                    }
                    if (x != 0 && left != null) {
                        Node leftNode = nodes.get(mapWidth * y + x - 1);
                        targetNode.createConnection(leftNode, 1);
                    }
                    if (x != mapWidth - 1 && right != null) {
                        Node rightNode = nodes.get(mapWidth * y + x + 1);
                        targetNode.createConnection(rightNode, 1);
                    }
                    if (y != mapHeight - 1 && up != null) {
                        Node upNode = nodes.get(mapWidth * (y + 1) + x);
                        targetNode.createConnection(upNode, 1);
                    }
                }
            }
        }
        return new GraphImp(nodes
        );
    }
}
