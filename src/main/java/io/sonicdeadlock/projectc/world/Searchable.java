package io.sonicdeadlock.projectc.world;

import io.sonicdeadlock.projectc.entity.Entity;

import java.util.List;

/**
 * Created by Alex on 10/7/2016.
 */
public interface Searchable {
    List<Entity> radialSearch(int x,int y,int radius);
    List<Entity> squareSearch(int x,int y,int width,int height);
    List<Entity> pointSearch(int x,int y);
}
