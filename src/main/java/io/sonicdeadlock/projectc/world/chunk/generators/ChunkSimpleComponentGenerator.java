package io.sonicdeadlock.projectc.world.chunk.generators;

import io.sonicdeadlock.projectc.entity.Entity;
import io.sonicdeadlock.projectc.world.chunk.Chunk;

import java.util.Random;

/**
 * Created by Alex on 10/5/2016.
 */
public abstract class ChunkSimpleComponentGenerator<T extends Entity> extends ChunkComponentGenerator {
    public void runGenerator(Chunk chunk) {
        Random random = new Random();
        for (int x = 0; x < Chunk.CHUNK_SIZE; x++) {
            for (int y = 0; y < Chunk.CHUNK_SIZE; y++) {
                if(getGenerationChance()>=random.nextDouble()){
                    Entity entity = getNewEntity(chunk.getGlobalX(x),chunk.getGlobalY(y));
                    chunk.addEntity(entity);
                }
            }
        }
    }

    protected abstract double getGenerationChance();
    protected abstract T getNewEntity(int x,int y);

}
