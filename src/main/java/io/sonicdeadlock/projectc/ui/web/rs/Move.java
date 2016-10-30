package io.sonicdeadlock.projectc.ui.web.rs;

import io.sonicdeadlock.projectc.entity.Player;
import io.sonicdeadlock.projectc.ui.web.response.MoveResponse;
import io.sonicdeadlock.projectc.util.RayCaster;
import io.sonicdeadlock.projectc.world.World;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.MediaType;

/**
 * Created by Alex on 10/15/2016.
 */
@Path("move")
public class Move {
    private static final Logger LOGGER = LogManager.getLogger(Move.class);

    @Path("left")
    @GET()
    @Produces(MediaType.APPLICATION_JSON)
    public MoveResponse moveLeft(){
        World world = WorldHolder.getWorld();
        Player player = world.getPlayer();
        tryPlayerMove(player.getX()-1,player.getY());
        MoveResponse moveResponse = new MoveResponse(world, player);
        player.getEyeSight().incrementXP(moveResponse.getEntityCount());
        return  moveResponse;
    }

    @Path("right")
    @GET()
    @Produces(MediaType.APPLICATION_JSON)
    public MoveResponse moveRight(){
        World world = WorldHolder.getWorld();
        Player player = world.getPlayer();
        tryPlayerMove(player.getX()+1,player.getY());
        MoveResponse moveResponse = new MoveResponse(world, player);
        player.getEyeSight().incrementXP(moveResponse.getEntityCount());
        return  moveResponse;    }

    @Path("up")
    @GET()
    @Produces(MediaType.APPLICATION_JSON)
    public MoveResponse moveUp(){
        World world = WorldHolder.getWorld();
        Player player = world.getPlayer();
        tryPlayerMove(player.getX(),player.getY()-1);
        MoveResponse moveResponse = new MoveResponse(world, player);
        player.getEyeSight().incrementXP(moveResponse.getEntityCount());
        return  moveResponse;    }

    @Path("down")
    @GET()
    @Produces(MediaType.APPLICATION_JSON)
    public MoveResponse moveDown(){
        World world = WorldHolder.getWorld();
        Player player = world.getPlayer();
        tryPlayerMove(player.getX(),player.getY()+1);
        MoveResponse moveResponse = new MoveResponse(world, player);
        player.getEyeSight().incrementXP(moveResponse.getEntityCount());
        return  moveResponse;    }

    private void tryPlayerMove(int x,int y){
        World world = WorldHolder.getWorld();
        Player player = world.getPlayer();
        if(player.canMoveToPoint(x,y,world)){
            player.moveTo(x,y);
            LOGGER.debug("Moving to "+x+","+y);
        }
        world.save();

    }

    
}
