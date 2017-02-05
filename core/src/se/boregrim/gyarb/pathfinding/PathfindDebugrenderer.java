package se.boregrim.gyarb.pathfinding;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.ai.pfa.Connection;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import se.boregrim.gyarb.Interfaces.Interactable;
import se.boregrim.gyarb.entities.Player;
import se.boregrim.gyarb.managers.MapManager;
import se.boregrim.gyarb.screens.GameScreen;

import static se.boregrim.gyarb.utils.Constants.PPM;

/**
 * Created by Robin on 2017-01-04.
 */
public class PathfindDebugrenderer implements Interactable {
    GameScreen gs;
    SpriteBatch batch;
    Player player;
    Vector2 NodePos;
    public PathfindDebugrenderer(GameScreen gs){
        this.gs = gs;
        player = gs.getPlayer();
        batch = gs.getBatch();
        //batch.setProjectionMatrix(gs.getViewport().getCamera().combined);
        NodePos = new Vector2(0,0);
    }

    @Override
    public void render(float delta) {
        // Node Debugrenderer


        batch.setProjectionMatrix(gs.getViewport().getCamera().combined);
        batch.begin();

        for (Vector2 pos : MapManager.nonWalkablePos) {
            batch.draw(new Texture(Gdx.files.internal("pic2.png")),pos.x,pos.y, 10/PPM,10/PPM);

        }


        Texture texture = new Texture(Gdx.files.internal("pic.png"));
        //batch.draw(texture,player.getX(),player.getY(),2,2);
        Array<Connection<Node>> a = MapManager.graph.getNodeByPos((int)NodePos.x, (int)NodePos.y).getConnections();
        //System.out.println("size: " + a.size);
        for (int i = 0; i < a.size; i++) {
            int index = MapManager.graph.getIndex(a.get(i).getToNode());
            int y = index / MapManager.mapTileWidth;
            int x = index % MapManager.mapTileWidth;
            //System.out.println(x + " " + y);
            batch.draw(texture,x,y, 10/PPM, 10/PPM);
        }
        //for (int y = 0; y < MapManager.mapTileHeight; y++) {
        //    for (int x = 0; x < MapManager.mapTileWidth; x++) {
        //        if((!MapManager.graph.getNodeByPos(x,y).equals(null))){
        //            batch.draw(texture,x,y, 10/PPM, 10/PPM);
        //        }
//
        //    }
        //}



        batch.end();


    }

    @Override
    public void update(float delta) {

    }


    @Override
    public void input(boolean paused) {
        if(Gdx.input.isKeyJustPressed(Input.Keys.UP)){
            NodePos.y++;
        }
        if(Gdx.input.isKeyJustPressed(Input.Keys.DOWN)){
            NodePos.y--;
        }
        if(Gdx.input.isKeyJustPressed(Input.Keys.LEFT)){
            NodePos.x--;
        }
        if(Gdx.input.isKeyJustPressed(Input.Keys.RIGHT)){
            NodePos.x++;
        }
    }

    @Override
    public void dispose() {

    }

    @Override
    public boolean isDead() {
        return false;
    }
}
