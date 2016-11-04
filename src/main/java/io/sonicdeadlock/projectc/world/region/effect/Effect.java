package io.sonicdeadlock.projectc.world.region.effect;

import io.sonicdeadlock.projectc.entity.Entity;
import io.sonicdeadlock.projectc.world.Loadable;

/**
 * Created by Alex on 10/10/2016.
 */
public abstract class Effect implements Loadable{
    public abstract void applyEffect(double intensity,Entity  e);
}
