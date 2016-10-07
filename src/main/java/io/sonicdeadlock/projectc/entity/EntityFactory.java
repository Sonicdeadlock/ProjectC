package io.sonicdeadlock.projectc.entity;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Alex on 10/5/2016.
 */
public class EntityFactory {
    private static EntityFactory ourInstance = new EntityFactory();
    private Map<String,Class<? extends Entity>> entityTypes = new HashMap<String, Class<? extends Entity>>();
    public static EntityFactory getInstance() {
        return ourInstance;
    }
    private static final Logger LOGGER = LogManager.getLogger(EntityFactory.class);

    private EntityFactory() {
    }

    public void registerEntity(String type ,Class<? extends Entity> clazz){
        entityTypes.put(type,clazz);
    }

    public Entity getEntity(String type, JSONObject saveObject){
        if(!entityTypes.containsKey(type))
            throw new IllegalArgumentException("Unregistered Entity type: "+ type);
        Class<? extends Entity> clazz =entityTypes.get(type);
        try {
            Entity newEntity = clazz.newInstance();
            newEntity.load(saveObject);
            return newEntity;
        } catch (InstantiationException e) {
            LOGGER.error("Error when creating Entity: "+clazz.getName(),e);
        } catch (IllegalAccessException e) {
            LOGGER.error("Error when creating Entity: "+clazz.getName(),e);
        } 
        return null;
    }
}
