package io.sonicdeadlock.projectc.world.region;

import io.sonicdeadlock.projectc.entity.Entity;
import io.sonicdeadlock.projectc.world.Loadable;
import io.sonicdeadlock.projectc.world.region.effect.Effect;
import io.sonicdeadlock.projectc.world.region.falloff.FalloffFunction;
import org.json.JSONObject;

/**
 * Created by Alex on 10/10/2016.
 */
public abstract class Region implements Loadable {
    private Effect effect;
    private FalloffFunction falloffFunction;

    public Region(Effect effect, FalloffFunction falloffFunction) {
        this.effect = effect;
        this.falloffFunction = falloffFunction;
    }

    protected Effect getEffect() {
        return this.effect;
    }

    protected FalloffFunction getFalloffFunction() {
        return falloffFunction;
    }

    public abstract boolean isInRange(int x, int y);

    public boolean isInRange(Entity entity) {
        return isInRange(entity.getX(), entity.getY());
    }

    public void applyEffect(Entity entity) {
        if (!isInRange(entity)) {
            throw new IllegalArgumentException("The entity is not inside of this region!");
        }
        effect.applyEffect(falloffFunction.getIntensity(entity.getX(), entity.getY()), entity);
    }

    public void applyEffect(Entity entity, int x, int y) {
        if (!isInRange(x, y)) {
            throw new IllegalArgumentException("The point (" + x + "," + y + ")" + " does not exist in this region!");
        }
        effect.applyEffect(falloffFunction.getIntensity(x, y), entity);
    }

    @Override
    public void load(JSONObject saveObject) {

    }

    @Override
    public JSONObject getSaveObject() {
        return null;
    }
}
