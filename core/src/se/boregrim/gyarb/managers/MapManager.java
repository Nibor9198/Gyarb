package se.boregrim.gyarb.managers;

import com.badlogic.gdx.ai.pfa.PathFinder;
import com.badlogic.gdx.ai.pfa.indexed.IndexedAStarPathFinder;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.GdxNativesLoader;
import se.boregrim.gyarb.pathfinding.GraphImp;
import com.badlogic.gdx.physics.box2d.PolygonShape;

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

    public  static int mapTileWitdh;
    public static int mapTileHeight;
    public static int pixelWidth;
    public static int pixelHeight;
    public static GraphImp graph;

    public void  loadMap(String mapRef, World world){

        this.mapRef = mapRef;
        maploader = new TmxMapLoader();
        map = maploader.load(mapRef);
        otmr = new OrthogonalTiledMapRenderer(map, 1 / PPM);

        Body body;
        BodyDef bdef = new BodyDef();
        FixtureDef fdef = new FixtureDef();


        PolygonShape shape = new PolygonShape();
        //GraphImp graph;


        MapProperties properties = map.getProperties();
        mapTileWitdh = properties.get("width", Integer.class);
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
            //fdef.filter.groupIndex = 1;

            body.createFixture(fdef);
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
