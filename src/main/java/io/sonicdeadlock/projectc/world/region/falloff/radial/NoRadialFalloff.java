package io.sonicdeadlock.projectc.world.region.falloff.radial;

/**
 * Created by Alex on 10/10/2016.
 */
public class NoRadialFalloff extends RadialFalloffFunction {
    public static final String TYPE = "NoRadialFalloff";

    public NoRadialFalloff(int x, int y, int radius) {
        super(x, y, radius);
    }

    @Override
    public double getIntensity(int x, int y) {
        return 1;
    }

    public String getType(){
        return TYPE;
    }



}
