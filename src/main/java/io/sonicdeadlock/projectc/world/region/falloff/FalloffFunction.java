package io.sonicdeadlock.projectc.world.region.falloff;

import io.sonicdeadlock.projectc.world.Loadable;

/**
 * Created by Alex on 10/10/2016.
 */
public interface FalloffFunction extends Loadable{
    /**
     *
     * @return the intensity at a point represented as a double between 0.0 and 1.0
     */
   double getIntensity(int x,int y);
}
