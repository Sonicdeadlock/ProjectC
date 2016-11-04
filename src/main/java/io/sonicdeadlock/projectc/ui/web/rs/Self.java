package io.sonicdeadlock.projectc.ui.web.rs;

import io.sonicdeadlock.projectc.entity.Player;
import io.sonicdeadlock.projectc.ui.web.response.MoveResponse;
import io.sonicdeadlock.projectc.world.World;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 * Created by Alex on 10/19/2016.
 */
@Path("self")
public class Self {

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public MoveResponse getSelf(){
        World world= WorldHolder.getWorld();
        Player player = world.getPlayer();
        return new MoveResponse(world,player);
    }
}
