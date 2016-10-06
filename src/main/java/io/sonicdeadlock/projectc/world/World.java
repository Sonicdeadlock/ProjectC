package io.sonicdeadlock.projectc.world;

import io.sonicdeadlock.projectc.entity.EntityFactory;
import io.sonicdeadlock.projectc.entity.Player;
import io.sonicdeadlock.projectc.util.JSONLoader;
import io.sonicdeadlock.projectc.util.PropertiesLoader;
import io.sonicdeadlock.projectc.world.chunk.Chunk;
import io.sonicdeadlock.projectc.world.chunk.ChunkGenerator;
import io.sonicdeadlock.projectc.world.chunk.ChunkLoader;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONObject;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Created by Alex on 10/5/2016.
 */
public class World {
    private static final Logger LOGGER = LogManager.getLogger(World.class);

//    The loaded chunks around the user
//    |0|1|2|
//    |3|4|5|
//    |6|7|8|
//    The user should be on index 4
    private Chunk[] loadedChunks =new Chunk[9];
    private Player player;

    public World(){
        this.player = loadPlayer();
        this.loadChunks();
        save();
    }

    private Player loadPlayer(){
        File playerSaveFile = new File(PropertiesLoader.getProperty("saveLocation") + PropertiesLoader.getProperty("player","saveFileName"));
        if(playerSaveFile.exists()){
            try {
                JSONObject playerData = JSONLoader.loadJSONObject(playerSaveFile);
                return (Player) EntityFactory.getInstance().getEntity(Player.getType(),playerData);
            } catch (IOException e) {
                LOGGER.error(e);
            }
        }else{
            return new Player(0,0);
        }
        return null;
    }

    private void loadChunks(){
        for (int x = -1 , index=0; x <= 1; x++) {
            for (int y = -1; y <= 1; y++,index++) {
                loadedChunks[index] = loadChunk(x+player.getChunkX(),y+player.getChuunkY());
            }
        }
    }

    private Chunk loadChunk(int x,int y){
        if((new File(Chunk.getSaveLocation(x,y))).exists()){
            return ChunkLoader.getInstance().loadChunk(x,y);
        }else{
            return ChunkGenerator.getInstance().generateChunk(x,y);
        }
    }

    public void save(){
        savePlayer();
        saveChunks();
    }

    private void savePlayer(){
        File playerSaveFile = new File(PropertiesLoader.getProperty("saveLocation") + PropertiesLoader.getProperty("player","saveFileName"));
        try {
            this.player.getSaveObject().write(new FileWriter(playerSaveFile)).flush();
        } catch (IOException e) {
            LOGGER.error("Error Saving Player",e);
        }
    }

    private void saveChunks(){
        for (Chunk loadedChunk : this.loadedChunks) {
            saveChunk(loadedChunk);
        }
    }

    private void saveChunk(Chunk chunk){
        try {
            chunk.getSaveObject().write(new FileWriter(new File(Chunk.getSaveLocation(chunk.getX(),chunk.getY())))).flush();
        } catch (IOException e) {
            LOGGER.error("Error Saving Chunk",e);
        }
    }

}
