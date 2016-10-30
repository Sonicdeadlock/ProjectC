package io.sonicdeadlock.projectc.ui.web.rs;

import io.sonicdeadlock.projectc.entity.Player;
import io.sonicdeadlock.projectc.ui.web.vo.PerformActionVO;
import io.sonicdeadlock.projectc.world.World;

import javax.ws.rs.POST;
import javax.ws.rs.Path;

/**
 * Created by Alex on 10/30/2016.
 */
@Path("performAction")
public class PerformAction {

    @POST
    @Path("selected")
    public void performActionOnSelected(PerformActionVO performActionVO){
        World world = WorldHolder.getWorld();
        Player player= world.getPlayer();
        player.getSelected().performAction(performActionVO.getAction(),player);
        world.save();
    }
}
