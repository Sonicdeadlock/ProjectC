package io.sonicdeadlock.projectc.item;

import io.sonicdeadlock.projectc.world.Loadable;

/**
 * Created by Alex on 10/13/2016.
 */
public abstract class Item implements Loadable {

    public boolean canStack() {
        return true;
    }

    public double getMass() {
        return 0;
    }

}
