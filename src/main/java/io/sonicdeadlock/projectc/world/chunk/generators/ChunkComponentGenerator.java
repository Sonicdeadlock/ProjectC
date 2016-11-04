package io.sonicdeadlock.projectc.world.chunk.generators;

import io.sonicdeadlock.projectc.world.chunk.Chunk;

/**
 * Created by Alex on 10/5/2016.
 */
public abstract class ChunkComponentGenerator {

    public ChunkComponentGenerator() {
    }

    public abstract void runGenerator(Chunk chunk);
}
