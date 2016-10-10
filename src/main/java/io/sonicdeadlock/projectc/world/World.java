package io.sonicdeadlock.projectc.world;

import io.sonicdeadlock.projectc.entity.Entity;
import io.sonicdeadlock.projectc.entity.Player;
import io.sonicdeadlock.projectc.util.JSONLoader;
import io.sonicdeadlock.projectc.util.LoaderFactory;
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
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by Alex on 10/5/2016.
 */
public class World implements Searchable {
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
                return (Player) LoaderFactory.getEntityLoaderFactoryInstance().getLoadable(Player.TYPE,playerData);
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
                loadedChunks[index] = loadChunk(x+player.getChunkX(),y+player.getChunkY());
            }
        }
    }

    private Chunk loadChunk(int x,int y){
        if((new File(Chunk.getSaveLocation(x,y))).exists()){
            return ChunkLoader.getInstance().loadChunk(x,y);
        }else{
            Chunk c= ChunkGenerator.getInstance().generateChunk(x,y);
            saveChunk(c);
            return c;
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

    public Chunk getChunk(int x,int y){
        for (Chunk loadedChunk : loadedChunks) {
            if(loadedChunk.getX()==x && loadedChunk.getY()==y)
                return loadedChunk;
        }
        return loadChunk(x,y);
    }

    public void applyRegionEffects(Entity entity){
        getChunk(entity.getX(),entity.getY()).applyRegionEffects(entity);
    }

    public List<Entity> radialSearch(int x,int y,int radius){
        List<Entity> foundEntities = new ArrayList<>();
        int westBoundChunk = (x-radius)/Chunk.CHUNK_SIZE;
        int eastBoundChunk = (x+radius)/Chunk.CHUNK_SIZE;
        int northBoundChunk = (y-radius)/Chunk.CHUNK_SIZE;
        int southBoundChunk = (y+radius)/Chunk.CHUNK_SIZE;

        for (int chunkX = westBoundChunk; chunkX <= eastBoundChunk; chunkX++) {
            for (int chunkY = northBoundChunk; chunkY <= southBoundChunk; chunkY++) {
                getChunk(chunkX,chunkY).radialSearch(x,y,radius).forEach(foundEntities::add);
            }
        }
        return foundEntities;
    }

    @Override
    public List<Entity> squareSearch(int x, int y, int width, int height) {
        List<Entity> foundEntities = new ArrayList<>();
        int westBoundChunk = (x)/Chunk.CHUNK_SIZE;
        int eastBoundChunk = (x+width)/Chunk.CHUNK_SIZE;
        int northBoundChunk = (y)/Chunk.CHUNK_SIZE;
        int southBoundChunk = (y+height)/Chunk.CHUNK_SIZE;
        for (int chunkX = westBoundChunk; chunkX < eastBoundChunk; chunkX++) {
            for (int chunkY = northBoundChunk; chunkY < southBoundChunk; chunkY++) {
                Collections.copy(foundEntities,getChunk(chunkX,chunkY).squareSearch(x,y,width,height));
            }
        }
        return foundEntities;
    }

    public Player getPlayer() {
        return player;
    }
}
