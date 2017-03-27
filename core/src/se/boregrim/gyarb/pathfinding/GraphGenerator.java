package se.boregrim.gyarb.pathfinding;

import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import se.boregrim.gyarb.managers.MapManager;

/**
 * Created by Robin on 2016-12-08.
 */
public class  GraphGenerator {
    public static GraphImp generateGraph(TiledMap map){
        Array<Node> nodes = new Array<Node>();

        TiledMapTileLayer tiles = (TiledMapTileLayer)map.getLayers().get(0);
        int mapHeight = MapManager.mapTileHeight;
        int mapWidth = MapManager.mapTileWidth;

        for (int y = 0; y < mapHeight; y++) {
            for (int x = 0; x < mapWidth; x++) {
                Node node = new Node();
                node.type = 1;
                nodes.add(node);
                //https://github.com/libgdx/gdx-ai/wiki/Hierarchical-Pathfinding
                //https://www.youtube.com/watch?v=wu3vzR9k3QA
            }
        }


        for (int y = 0; y < mapHeight; y++) {
            for (int x = 0; x < mapWidth; x++) {
                TiledMapTileLayer.Cell target = tiles.getCell(x, y);
                TiledMapTileLayer.Cell up = tiles.getCell(x, y+1);
                TiledMapTileLayer.Cell left = tiles.getCell(x-1, y);
                TiledMapTileLayer.Cell right = tiles.getCell(x+1, y);
                TiledMapTileLayer.Cell down = tiles.getCell(x, y-1);

                TiledMapTileLayer.Cell upRight = tiles.getCell(x+1, y+1);
                TiledMapTileLayer.Cell upLeft = tiles.getCell(x+1, y-1);
                TiledMapTileLayer.Cell downRight = tiles.getCell(x-1, y+1);
                TiledMapTileLayer.Cell downLeft = tiles.getCell(x-1, y-1);
                boolean walkable = true;

                for (Vector2 pos : MapManager.nonWalkablePos) {
                    if(x == pos.x && y == pos.y) {
                        walkable = false;
                        break;
                    }
                    if(x == pos.x && y+1 == pos.y)
                        up = null;
                    if(x+1 == pos.x && y == pos.y)
                        right = null;
                    if(x == pos.x && y-1 == pos.y)
                        down = null;
                    if(x-1 == pos.x && y == pos.y)
                        left = null;

                    if(x+1 == pos.x && y+1 == pos.y)
                        upRight = null;
                    if(x-1 == pos.x && y+1 == pos.y)
                        upLeft = null;
                    if(x+1 == pos.x && y-1 == pos.y)
                        downRight = null;
                    if(x-1 == pos.x && y-1 == pos.y)
                        downLeft = null;
                }

                Node targetNode = nodes.get(mapWidth * y + x);

                if (target != null && walkable) {
                    //System.out.println("x, y" + x + " " + y);
                    if (y != 0 && down != null) {
                        Node downNode = nodes.get(mapWidth * (y - 1) + x);
                        targetNode.createConnection(downNode, 10);
                    }
                    if (x != 0 && left != null) {
                        Node leftNode = nodes.get(mapWidth * y + x - 1);
                        targetNode.createConnection(leftNode, 10);
                    }
                    if (x != mapWidth - 1 && right != null) {
                        Node rightNode = nodes.get(mapWidth * y + x + 1);
                        targetNode.createConnection(rightNode, 10);
                    }
                    if (y != mapHeight - 1 && up != null) {
                        Node upNode = nodes.get(mapWidth * (y + 1) + x);
                        targetNode.createConnection(upNode, 10);
                    }

                    if (y != mapHeight - 1 && x != mapWidth - 1 && upRight != null) {
                        Node Node = nodes.get(mapWidth * (y + 1) + x + 1);
                        targetNode.createConnection(Node, 14);
                    }
                    if (y != mapHeight - 1 && x != 0 && upLeft != null) {
                        Node Node = nodes.get(mapWidth * (y + 1) + x - 1);
                        targetNode.createConnection(Node, 14);
                    }
                    if (y != 0 && x != mapWidth - 1 && downRight != null) {
                        Node Node = nodes.get(mapWidth * (y - 1) + x + 1);
                        targetNode.createConnection(Node, 14);
                    }
                    if (y != 0 && x != 0 && downLeft != null) {
                        Node Node = nodes.get(mapWidth * (y - 1) + x - 1);
                        targetNode.createConnection(Node, 14);
                    }

                }
            }
        }
        return new GraphImp(nodes);
    }
}
