package io.sonicdeadlock.projectc.world.chunk;

import io.sonicdeadlock.projectc.entity.Entity;
import io.sonicdeadlock.projectc.util.LoaderFactory;
import io.sonicdeadlock.projectc.util.PropertiesLoader;
import io.sonicdeadlock.projectc.util.SpacialUtils;
import io.sonicdeadlock.projectc.world.Loadable;
import io.sonicdeadlock.projectc.world.Searchable;
import org.json.JSONArray;
import org.json.JSONObject;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Alex on 10/5/2016.
 */
public class Chunk implements Loadable,Searchable{
    public static final int CHUNK_SIZE;
    private List<Entity> entities;
    private int x,y;
    static{
        CHUNK_SIZE = Integer.parseInt(PropertiesLoader.getProperty("chunk","size"));
    }

    public Chunk(int x, int y) {
        this(new ArrayList<Entity>(),x,y);
    }

    public Chunk(List<Entity> entities, int x, int y) {
        this.entities = entities;
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getEastBound(){
        return (x+1)*CHUNK_SIZE-1;
    }

    public int getWestBound(){
        return x*CHUNK_SIZE;
    }

    public int getNorthBound(){
        return y*CHUNK_SIZE;
    }

    public int getSouthBound(){
        return (y+1)*CHUNK_SIZE-1;
    }

    public int getLocalX(int globalX){
        if(globalX>getEastBound() || globalX<getWestBound())
            throw new IllegalArgumentException("Global X is outside of this chunk");
        return globalX-getWestBound();
    }

    public int getLocalY(int globalY){
        if(globalY>getSouthBound() || globalY<getNorthBound())
            throw new IllegalArgumentException("Global Y is outside of this chunk");
        return globalY-getNorthBound();
    }

    public int getGlobalX(int localX){
        return localX+getWestBound();
    }
    public int getGlobalY(int localY){
        return localY+getNorthBound();
    }

    public void addEntity(Entity entity){
        try{
            getLocalX(entity.getX());
            getLocalY(entity.getY());
        }catch (IllegalArgumentException ex){
            throw new IllegalArgumentException("Entity does not belong in this chunk",ex);
        }
        entities.add(entity);
    }

    public int getEntityCount(){
        return entities.size();
    }

    public JSONObject getSaveObject() {
        JSONObject saveObject = new JSONObject();
        JSONArray entities = new JSONArray();
        for (Entity entity : this.entities) {
            JSONObject entityWrappedObject = new JSONObject();
            try {
                entityWrappedObject.put("type",entity.getClass().getMethod("getType").invoke(null));
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
            entityWrappedObject.put("entityData",entity.getSaveObject());
            entities.put(entityWrappedObject);
        }
        saveObject.put("entities",entities);
        saveObject.put("x",x);
        saveObject.put("y",y);
        saveObject.put("entityCount",getEntityCount());
        return saveObject;

    }

    public void load(JSONObject saveObject) {
        this.entities = new ArrayList<Entity>(saveObject.getInt("entityCount"));
        for (Object o : saveObject.getJSONArray("entities")) {
            if(o instanceof JSONObject){
                JSONObject entitySaveWrapperObject = (JSONObject)o;
                String type = entitySaveWrapperObject.getString("type");
                Entity entity =  LoaderFactory.getEntityLoaderFactoryInstance().getLoadable(type,entitySaveWrapperObject.getJSONObject("entityData"));
                this.entities.add(entity);
            }
        }
    }

    public static String getSaveLocation(int x,int y){
        StringBuilder fileName = new StringBuilder();
        fileName.append(PropertiesLoader.getProperty("saveLocation"))
                .append(x)
                .append(PropertiesLoader.getProperty("chunk","saveFileDeliminator"))
                .append(y)
                .append(".")
                .append(PropertiesLoader.getProperty("chunk","saveFileType"));
        return fileName.toString();
    }

    public List<Entity> radialSearch(int x,int y,int radius){
        return entities.stream().filter(entity -> SpacialUtils.getDistance(entity.getX(), entity.getY(), x, y) <= radius).collect(Collectors.toList());
    }

    @Override
    public List<Entity> squareSearch(int x, int y, int width, int height) {
        List<Entity> foundEntities = new ArrayList<>();
        for (Entity entity : entities) {
            int eXDelta = entity.getX()-x;
            int eYDelta = entity.getY()-y;
            if(eXDelta>width && eYDelta>height)
                foundEntities.add(entity);
        }
        return foundEntities;
    }
}
