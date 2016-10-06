package io.sonicdeadlock.projectc.world.chunk;

import io.sonicdeadlock.projectc.util.JSONLoader;
import io.sonicdeadlock.projectc.util.PropertiesLoader;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

/**
 * Created by Alex on 10/5/2016.
 */
public class ChunkLoader {
    private static ChunkLoader ourInstance = new ChunkLoader();
    private static final Logger LOGGER = LogManager.getLogger(ChunkLoader.class);

    public static ChunkLoader getInstance() {
        return ourInstance;
    }

    private ChunkLoader() {
    }

    public Chunk loadChunk(int x,int y){

        BufferedReader br = null;

        JSONObject chunkData = null;
        try {
            chunkData =JSONLoader.loadJSONObject(Chunk.getSaveLocation(x,y));
        } catch (IOException e) {
            LOGGER.error(e);
        }
        Chunk chunk = new Chunk(x,y);
        chunk.load(chunkData);
        return chunk;
    }
}
