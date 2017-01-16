package io.sonicdeadlock.projectc.world.chunk;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.sonicdeadlock.projectc.entity.Entity;
import io.sonicdeadlock.projectc.util.PropertiesLoader;
import io.sonicdeadlock.projectc.util.SpacialUtils;
import io.sonicdeadlock.projectc.world.Loadable;
import io.sonicdeadlock.projectc.world.Searchable;
import io.sonicdeadlock.projectc.world.region.Region;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Alex on 10/5/2016.
 */
public class Chunk implements Loadable, Searchable {
    public static final int CHUNK_SIZE;
    public static final String TYPE = "Chunk";
    private static final Logger LOGGER = LogManager.getLogger(Chunk.class);

    static {
        CHUNK_SIZE = Integer.parseInt(PropertiesLoader.getProperty("chunk", "size"));
    }

    private List<Entity> entities;
    private List<Region> regions;
    private int x, y;

    public Chunk(int x, int y) {
        this(new ArrayList<>(), new ArrayList<>(), x, y);
    }

    public Chunk(List<Entity> entities, List<Region> regions, int x, int y) {
        this.entities = entities;
        this.regions = regions;
        this.x = x;
        this.y = y;
    }

    public Chunk() {

    }

    public static String getSaveLocation(int x, int y) {
        StringBuilder fileName = new StringBuilder();
        fileName.append(PropertiesLoader.getProperty("saveLocation"))
                .append(x)
                .append(PropertiesLoader.getProperty("chunk", "saveFileDeliminator"))
                .append(y)
                .append(".")
                .append(PropertiesLoader.getProperty("chunk", "saveFileType"));
        return fileName.toString();
    }

    @JsonIgnore
    public String getSaveLocation() {
        return getSaveLocation(getX(), getY());
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    @JsonIgnore
    public int getEastBound() {
        return (x + 1) * CHUNK_SIZE - 1;
    }

    @JsonIgnore
    public int getWestBound() {
        return x * CHUNK_SIZE;
    }

    @JsonIgnore
    public int getNorthBound() {
        return y * CHUNK_SIZE;
    }

    @JsonIgnore
    public int getSouthBound() {
        return (y + 1) * CHUNK_SIZE - 1;
    }

    public int getLocalX(int globalX) {
        if (globalX > getEastBound() || globalX < getWestBound())
            throw new IllegalArgumentException("Global X is outside of this chunk");
        return globalX - getWestBound();
    }

    public int getLocalY(int globalY) {
        if (globalY > getSouthBound() || globalY < getNorthBound())
            throw new IllegalArgumentException("Global Y is outside of this chunk");
        return globalY - getNorthBound();
    }

    public int getGlobalX(int localX) {
        return localX + getWestBound();
    }

    public int getGlobalY(int localY) {
        return localY + getNorthBound();
    }

    public void addEntity(Entity entity) {
        try {
            getLocalX(entity.getX());
            getLocalY(entity.getY());
        } catch (IllegalArgumentException ex) {
            throw new IllegalArgumentException("Entity does not belong in this chunk", ex);
        }
        entities.add(entity);
    }

    @JsonIgnore
    public int getEntityCount() {
        return entities.size();
    }

    public List<Entity> getEntities() {
        return entities;
    }

    public void setEntities(List<Entity> entities) {
        this.entities = entities;
    }

    public List<Region> getRegions() {
        return regions;
    }

    public void setRegions(List<Region> regions) {
        this.regions = regions;
    }

    @Override
    public String getType() {
        return TYPE;
    }

    public List<Entity> radialSearch(int x, int y, int radius) {
        return entities.stream().filter(entity -> SpacialUtils.getDistance(entity.getX(), entity.getY(), x, y) <= radius).collect(Collectors.toList());
    }

    @Override
    public List<Entity> squareSearch(int x, int y, int width, int height) {
        List<Entity> foundEntities = new ArrayList<>();
        for (Entity entity : entities) {
            int eXDelta = entity.getX() - x;
            int eYDelta = entity.getY() - y;
            if (eXDelta > width && eYDelta > height)
                foundEntities.add(entity);
        }
        return foundEntities;
    }

    @Override
    public List<Entity> pointSearch(int x, int y) {
        return entities.stream().filter(entity -> entity.getX() == x && entity.getY() == y).collect(Collectors.toList());
    }

    public void addRegion(Region region) {
        if (!(region.isInRange(getEastBound(), getNorthBound()) ||
                region.isInRange(getWestBound(), getSouthBound()) ||
                region.isInRange(getWestBound(), getNorthBound()) ||
                region.isInRange(getEastBound(), getSouthBound()))) {
            throw new IllegalArgumentException("The region passed does not fit inside of this Chunk");
        }
        this.regions.add(region);
    }

    public List<Region> getRegions(int x, int y) {
        return regions.stream().filter(region -> region.isInRange(x, y)).collect(Collectors.toList());
    }

    public void applyRegionEffects(Entity e) {
        for (Region region : getRegions(e.getX(), e.getY())) {
            region.applyEffect(e);
        }
    }
}
