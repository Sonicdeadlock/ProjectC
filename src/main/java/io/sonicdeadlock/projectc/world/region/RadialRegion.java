package io.sonicdeadlock.projectc.world.region;

import io.sonicdeadlock.projectc.util.SpacialUtils;
import io.sonicdeadlock.projectc.world.region.effect.Effect;
import io.sonicdeadlock.projectc.world.region.falloff.radial.NoRadialFalloff;
import io.sonicdeadlock.projectc.world.region.falloff.radial.RadialFalloffFunction;

/**
 * Created by Alex on 10/10/2016.
 */
public class RadialRegion extends Region {
    public static final String TYPE = "RadialRegion";
    private int x, y, radius;

    public RadialRegion(Effect effect, int x, int y, int radius) {
        this(effect, new NoRadialFalloff(x, y, radius), x, y, radius);
    }

    public RadialRegion(Effect effect, RadialFalloffFunction falloffFunction, int x, int y, int radius) {
        super(effect, falloffFunction);
        this.x = x;
        this.y = y;
        this.radius = radius;
    }

    @Override
    public boolean isInRange(int x, int y) {
        return SpacialUtils.getDistance(x, y, this.x, this.y) <= radius;
    }

    public String getType() {
        return TYPE;
    }
}
