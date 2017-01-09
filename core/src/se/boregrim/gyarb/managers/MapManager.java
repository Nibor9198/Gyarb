package se.boregrim.gyarb.managers;

import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import se.boregrim.gyarb.pathfinding.GraphGenerator;
import se.boregrim.gyarb.pathfinding.GraphImp;
import com.badlogic.gdx.physics.box2d.PolygonShape;

import java.util.ArrayList;

import static se.boregrim.gyarb.utils.Constants.*;
import static se.boregrim.gyarb.utils.Constants.CAT_ENEMY;
import static se.boregrim.gyarb.utils.Constants.CAT_ENTITY;

/**
 * Created by Robin on 2016-12-08.
 */
public class MapManager {
    public  TiledMap map;
    public  String mapRef;
    public  TmxMapLoader maploader;
    public  OrthogonalTiledMapRenderer otmr;

    public  static int mapTileWidth;
    public static int mapTileHeight;
    public static int pixelWidth;
    public static int pixelHeight;
    public static GraphImp graph;

    public static ArrayList<Vector2> nonWalkablePos;

    public void  loadMap(String mapRef, World world){
        this.mapRef = mapRef;
        maploader = new TmxMapLoader();
        map = maploader.load(mapRef);
        otmr = new OrthogonalTiledMapRenderer(map, 1 / PPM);
        nonWalkablePos = new ArrayList<Vector2>();

        Body body;
        BodyDef bdef = new BodyDef();
        FixtureDef fdef = new FixtureDef();


        PolygonShape shape = new PolygonShape();
        //GraphImp graph;


        MapProperties properties = map.getProperties();
        mapTileWidth = properties.get("width", Integer.class);
        mapTileHeight = properties.get("height", Integer.class);

        // Create box2d objects from the map file (Collision)

        for (MapObject mapObject : map.getLayers().get(1).getObjects().getByType(RectangleMapObject.class)){
            Rectangle rect = ((RectangleMapObject) mapObject).getRectangle();
            bdef.type = BodyDef.BodyType.StaticBody;
            bdef.position.set((rect.getX()+ rect.getWidth()/ 2) / PPM ,(rect.getY() + rect.getHeight()/ 2) / PPM);
            body = world.createBody(bdef);
            //PPM = Pixels Per Meter
            shape.setAsBox(rect.getWidth() / 2/ PPM, rect.getHeight() / 2/ PPM);
            fdef.shape = shape;
            fdef.filter.categoryBits = CAT_EDGE;
            fdef.filter.maskBits = CAT_PLAYER | CAT_ENEMY | CAT_ENTITY;
            fdef.filter.groupIndex = -1;
            body.createFixture(fdef);

            addNonWalkable(rect);
        }
        for (MapObject mapObject : map.getLayers().get(2).getObjects().getByType(RectangleMapObject.class)){
            Rectangle rect = ((RectangleMapObject) mapObject).getRectangle();
            bdef.type = BodyDef.BodyType.StaticBody;
            bdef.position.set((rect.getX()+ rect.getWidth()/ 2) / PPM ,(rect.getY() + rect.getHeight()/ 2) / PPM);
            body = world.createBody(bdef);
            //PPM = Pixels Per Meter
            shape.setAsBox(rect.getWidth() / 2/ PPM, rect.getHeight() / 2/ PPM);
            fdef.shape = shape;
            fdef.filter.categoryBits = CAT_WALL;
            fdef.filter.maskBits = CAT_PLAYER | CAT_ENEMY | CAT_ENTITY;
            fdef.filter.groupIndex = 1;

            body.createFixture(fdef);
            addNonWalkable(rect);
        }

        graph = GraphGenerator.generateGraph(map);

    }

    private void addNonWalkable(Rectangle rect){
        for (int y = 0; y < Math.round(rect.getHeight()/PPM); y++) {
            for (int x = 0; x < Math.round(rect.getWidth()/PPM); x++) {
                if(!(y < 2 && y > mapTileHeight-2 && x < 2 && x > mapTileWidth -2)) {
                    System.out.println(x + rect.getX() + " " + y + rect.getY());
                    nonWalkablePos.add(new Vector2(x + Math.round(rect.getX()/PPM), y +  Math.round(rect.getY()/PPM)));
                }
            }
        }
    }
    public void dispose(){
        map.dispose();
        otmr.dispose();
    }

    public String getMapRef() {
        return mapRef;
    }

    public void setMapRef(String mapRef) {
        this.mapRef = mapRef;
    }

    public TiledMap getMap() {
        return map;
    }

    public void setMap(TiledMap map) {
        this.map = map;
    }

    public TmxMapLoader getMaploader() {
        return maploader;
    }

    public void setMaploader(TmxMapLoader maploader) {
        this.maploader = maploader;
    }

    public OrthogonalTiledMapRenderer getOtmr() {
        return otmr;
    }

    public void setOtmr(OrthogonalTiledMapRenderer otmr) {
        this.otmr = otmr;
    }
}
