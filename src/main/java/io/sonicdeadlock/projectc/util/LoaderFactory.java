package io.sonicdeadlock.projectc.util;

import io.sonicdeadlock.projectc.entity.Entity;
import io.sonicdeadlock.projectc.entity.attribute.Attribute;
import io.sonicdeadlock.projectc.world.Loadable;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Alex on 10/10/2016.
 */
public class LoaderFactory<T extends Loadable> {

    private Map<String,Class<? extends T>> loadableTypes = new HashMap<>();
    private static final Logger LOGGER = LogManager.getLogger(LoaderFactory.class);
    private static LoaderFactory<Entity> entityLoaderFactoryInstance = new LoaderFactory<>();
    private static LoaderFactory<Attribute> attributeLoaderFactoryInstance = new LoaderFactory<>();

    private LoaderFactory(){

    }

    public static LoaderFactory<Entity> getEntityLoaderFactoryInstance() {
        return entityLoaderFactoryInstance;
    }

    public static LoaderFactory<Attribute> getAttributeLoaderFactoryInstance() {
        return attributeLoaderFactoryInstance;
    }

    public void registerLoadable(String type , Class<? extends T> clazz){
        loadableTypes.put(type,clazz);
    }

    public T getLoadable(String type, JSONObject saveObject){
        if(!loadableTypes.containsKey(type))
            throw new IllegalArgumentException("Unregistered Loadable type: "+ type);
        Class<? extends T> clazz =loadableTypes.get(type);
        try {
            T newLoadable = clazz.newInstance();
            newLoadable.load(saveObject);
            return newLoadable;
        } catch (InstantiationException e) {
            LOGGER.error("Error when creating Loadable: "+clazz.getName(),e);
        } catch (IllegalAccessException e) {
            LOGGER.error("Error when creating Loadable: "+clazz.getName(),e);
        }
        return null;
    }
}
