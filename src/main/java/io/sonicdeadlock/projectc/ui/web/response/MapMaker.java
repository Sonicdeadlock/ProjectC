package io.sonicdeadlock.projectc.ui.web.response;

import io.sonicdeadlock.projectc.entity.Entity;
import io.sonicdeadlock.projectc.entity.Player;
import io.sonicdeadlock.projectc.world.World;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Alex on 10/15/2016.
 */
public class MapMaker {


    public static Entity[][] makeMap(Player player, World world){
        int visionDistance = player.getEyeSight().getViewDistance();
        Entity[][] map = new Entity[visionDistance*2+1][visionDistance*2+1];
        List<Entity> visibleEntities = world.radialSearch(player.getX(),player.getY(),visionDistance);
        for (Entity visibleEntity : visibleEntities) {
            int mapX = visibleEntity.getX()-player.getX()+visionDistance;
            int mapY = visibleEntity.getY()-player.getY()+visionDistance;
            map[mapY][mapX] = visibleEntity;
        }
        map[visionDistance][visionDistance] = player;
        return map;
    }
}
