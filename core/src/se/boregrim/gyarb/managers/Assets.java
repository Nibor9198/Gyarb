package se.boregrim.gyarb.managers;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;


import java.io.File;

/**
 * Created by robin.boregrim on 2016-10-24.
 */
public class Assets {
    AssetManager manager;
    public Assets(){
        //Create a new AssetManager
        manager = new AssetManager();

        //Textures
        manager.load("harambe.png", Texture.class);
        manager.load("menuBackground.png", Texture.class);
        manager.load("Player.png", Texture.class);
        manager.load("Enemy.png", Texture.class);
        //manager.load("PlayerSprite.png", Texture.class);
        manager.load("title.png" ,Texture.class);
        //manager.load("harambe2.png", Texture.class);
        //manager.load("harambe3.png", Texture.class);
        //manager.load("harambe4.png", Texture.class);
        //manager.load("harambe5.png", Texture.class);
        //manager.load("harambe6.png", Texture.class);
        //manager.load("harambe7.png", Texture.class);
        //manager.load("harambe8.png", Texture.class);
        //manager.load("harambe9.png", Texture.class);
        //manager.load("harambe10.png", Texture.class);
        //manager.load("harambe11.png", Texture.class);
        //manager.load("harambe12.png", Texture.class);
        update();


    }
    public AssetManager getAssetManager(){
        return manager;
    }

    public void update(){
        manager.update();
    }
    public void dispose(){
        manager.dispose();
    }
}
