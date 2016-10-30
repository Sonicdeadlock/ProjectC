package io.sonicdeadlock.projectc.ui.web.rs;

import io.sonicdeadlock.projectc.runtime.PreInit;
import io.sonicdeadlock.projectc.world.World;

/**
 * Created by Alex on 10/15/2016.
 */
public class WorldHolder {
    private static final World world;
    static{
        PreInit.getInstance().preInit();
        world = new World();
    }
    public static World getWorld(){
        return world;
    }
}
