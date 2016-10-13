package io.sonicdeadlock.projectc.util;

import io.sonicdeadlock.projectc.entity.Entity;
import io.sonicdeadlock.projectc.entity.attribute.Attribute;
import io.sonicdeadlock.projectc.item.Item;
import io.sonicdeadlock.projectc.world.Loadable;
import io.sonicdeadlock.projectc.world.region.Region;
import io.sonicdeadlock.projectc.world.region.effect.Effect;
import io.sonicdeadlock.projectc.world.region.falloff.FalloffFunction;
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
    private final  static LoaderFactory<Entity> entityLoaderFactoryInstance = new LoaderFactory<>();
    private final  static LoaderFactory<Attribute> attributeLoaderFactoryInstance = new LoaderFactory<>();
    private final  static LoaderFactory<Effect> effectLoaderFactoryInstance = new LoaderFactory<>();
    private final  static LoaderFactory<FalloffFunction> falloffFunctionLoaderFactoryInstance = new LoaderFactory<>();
    private final static LoaderFactory<Region> regionLoaderFactoryInstance = new LoaderFactory<>();
    private final static LoaderFactory<Item> itemLoaderFactoryInstance = new LoaderFactory<>();


    private LoaderFactory(){

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
        } catch (InstantiationException | IllegalAccessException e) {
            LOGGER.error("Error when creating Loadable: "+clazz.getName(),e);
        }
        return null;
    }

    public static LoaderFactory<Entity> getEntityLoaderFactoryInstance() {
        return entityLoaderFactoryInstance;
    }

    public static LoaderFactory<Attribute> getAttributeLoaderFactoryInstance() {
        return attributeLoaderFactoryInstance;
    }

    public static LoaderFactory<Effect> getEffectLoaderFactoryInstance() {
        return effectLoaderFactoryInstance;
    }

    public static LoaderFactory<FalloffFunction> getFalloffFunctionLoaderFactoryInstance() {
        return falloffFunctionLoaderFactoryInstance;
    }

    public static LoaderFactory<Region> getRegionLoaderFactoryInstance() {
        return regionLoaderFactoryInstance;
    }

    public static LoaderFactory<Item> getItemLoaderFactoryInstance() {
        return itemLoaderFactoryInstance;
    }
}
