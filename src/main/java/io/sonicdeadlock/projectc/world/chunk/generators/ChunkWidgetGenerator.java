package io.sonicdeadlock.projectc.world.chunk.generators;

import io.sonicdeadlock.projectc.entity.Widget;

/**
 * Created by Alex on 10/5/2016.
 */
public class ChunkWidgetGenerator extends ChunkSimpleComponentGenerator<Widget> {
    private static final double GENERATION_CHANCE = .01;

    protected double getGenerationChance() {
        return GENERATION_CHANCE;
    }

    protected Widget getNewEntity(int x, int y) {
        return new Widget(x, y);
    }
}
