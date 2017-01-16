package io.sonicdeadlock.projectc.world.chunk;

import io.sonicdeadlock.projectc.util.SaveUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;

/**
 * Created by Alex on 10/5/2016.
 */
public class ChunkLoader {
    private static final Logger LOGGER = LogManager.getLogger(ChunkLoader.class);
    private static ChunkLoader ourInstance = new ChunkLoader();

    private ChunkLoader() {
    }

    public static ChunkLoader getInstance() {
        return ourInstance;
    }

    public Chunk loadChunk(int x, int y) {
        try {
            return SaveUtils.load(Chunk.getSaveLocation(x, y), Chunk.class);
        } catch (IOException e) {
            LOGGER.error(e);
        }
        return null;
    }
}
