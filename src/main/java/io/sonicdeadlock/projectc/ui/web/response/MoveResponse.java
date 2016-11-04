package io.sonicdeadlock.projectc.ui.web.response;

import io.sonicdeadlock.projectc.entity.Entity;
import io.sonicdeadlock.projectc.entity.Player;
import io.sonicdeadlock.projectc.world.World;

/**
 * Created by Alex on 10/15/2016.
 */
public class MoveResponse {
    private Entity[][] map;
    private Player player;

    public MoveResponse(World world, Player player){
        map = MapMaker.makeMap(player,world);
        this.player = player;
    }

    public Entity[][] getMap() {
        return map;
    }

    public Player getPlayer() {
        return player;
    }

    public int getEntityCount(){
        int entityCount=0;
        for (Entity[] entities : map) {
            for (Entity entity : entities) {
                if (entity != null && entity!=player) {
                    entityCount++;
                }
            }
        }
        return entityCount;
    }

}
