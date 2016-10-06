package io.sonicdeadlock.projectc.world.chunk;

import io.sonicdeadlock.projectc.world.chunk.generators.ChunkComponentGenerator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Alex on 10/5/2016.
 */
public class ChunkGenerator {
    private static ChunkGenerator ourInstance = new ChunkGenerator();
    private List<Class<? extends ChunkComponentGenerator>> generators;
    private static final Logger LOGGER = LogManager.getLogger(ChunkGenerator.class);

    public static ChunkGenerator getInstance() {
        return ourInstance;
    }

    private ChunkGenerator() {
        generators = new ArrayList<Class<? extends ChunkComponentGenerator>>();
    }

    public void  registerGenerator(Class<? extends ChunkComponentGenerator> clazz){
        if(generators.indexOf(clazz)==-1)
            generators.add(clazz);
    }

    public Chunk generateChunk(int x,int y){
        Chunk chunk = new Chunk(x,y);
        for (Class<? extends ChunkComponentGenerator> generatorClass : generators) {
            try {
                ChunkComponentGenerator generator = generatorClass.getConstructor().newInstance();
                LOGGER.info("Running generator: "+generatorClass.getName());
                generator.runGenerator(chunk);
            } catch (InstantiationException e) {
                LOGGER.error("Error when creating ChunkComponentGenerator: "+generatorClass.getName(),e);
            } catch (IllegalAccessException e) {
                LOGGER.error("Error when creating ChunkComponentGenerator: "+generatorClass.getName(),e);
            } catch (InvocationTargetException e) {
                LOGGER.error("Error when creating ChunkComponentGenerator: "+generatorClass.getName(),e);
            } catch (NoSuchMethodException e) {
                LOGGER.error("Error when creating ChunkComponentGenerator: "+generatorClass.getName(),e);
            }
        }
        return chunk;
    }
}
