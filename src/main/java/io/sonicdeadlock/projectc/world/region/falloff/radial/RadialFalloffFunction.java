package io.sonicdeadlock.projectc.world.region.falloff.radial;

import io.sonicdeadlock.projectc.world.region.falloff.FalloffFunction;
import org.json.JSONObject;

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


    protected int getX() {
        return x;
    }

    protected int getY() {
        return y;
    }

    protected int getRadius() {
        return radius;
    }

    @Override
    public JSONObject getSaveObject() {
        JSONObject saveObject = new JSONObject();
        saveObject.put("x", x);
        saveObject.put("y", y);
        saveObject.put("radius", radius);
        return saveObject;
    }

    @Override
    public void load(JSONObject saveObject) {
        this.y = saveObject.getInt("y");
        this.x = saveObject.getInt("x");
        this.radius = saveObject.getInt("radius");
    }
}
