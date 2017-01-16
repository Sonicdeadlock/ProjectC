package io.sonicdeadlock.projectc.world.region.falloff.radial;

import io.sonicdeadlock.projectc.world.region.falloff.FalloffFunction;

/**
 * Created by Alex on 10/10/2016.
 */
public abstract class RadialFalloffFunction implements FalloffFunction {
    private int x, y, radius;

    public RadialFalloffFunction(int x, int y, int radius) {
        this.x = x;
        this.y = y;
        this.radius = radius;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getRadius() {
        return radius;
    }

    public void setRadius(int radius) {
        this.radius = radius;
    }
}
