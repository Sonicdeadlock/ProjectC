package io.sonicdeadlock.projectc.ui.web.rs;

import io.sonicdeadlock.projectc.entity.Entity;
import io.sonicdeadlock.projectc.entity.Player;
import io.sonicdeadlock.projectc.ui.web.vo.Point;
import io.sonicdeadlock.projectc.ui.web.vo.SelectItemVO;
import io.sonicdeadlock.projectc.world.World;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

/**
 * Created by Alex on 10/30/2016.
 */
@Path("select")
public class Select {
    private static final Logger LOGGER = LogManager.getLogger(Select.class);





    @POST
    @Path("/entity")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response select(Point p){
        World world = WorldHolder.getWorld();
        List<Entity> entitiesAtPoint = world.pointSearch(p.getX(),p.getY());
        if(entitiesAtPoint.size()==0){
            return Response.status(Response.Status.BAD_REQUEST).entity("No entity found at point").build();
        }
        Player player = world.getPlayer();
        player.setSelected(entitiesAtPoint.get(0));
        return Response.ok(player,MediaType.APPLICATION_JSON_TYPE).build();

    }

    @POST
    @Path("/item")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response selectItem(SelectItemVO selectItemVO){
        World world = WorldHolder.getWorld();
        Player player = world.getPlayer();
        if(player.getInventory().size()<=selectItemVO.getIndex()){
            return Response.status(Response.Status.BAD_REQUEST).entity("Invalid index for inventory item").build();
        }
        player.setSelected(player.getInventory().get(selectItemVO.getIndex()));
        return Response.ok(player,MediaType.APPLICATION_JSON_TYPE).build();


    }
}
