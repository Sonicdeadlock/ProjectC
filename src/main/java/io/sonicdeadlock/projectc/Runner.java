package io.sonicdeadlock.projectc;

import io.sonicdeadlock.projectc.runtime.PreInit;
import io.sonicdeadlock.projectc.world.World;

/**
 * Created by Alex on 10/5/2016.
 */
public class Runner {
    public static void main(String[] args) {
        PreInit.getInstance().preInit();
        new World();
    }
}
