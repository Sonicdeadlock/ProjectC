package io.sonicdeadlock.projectc.entity.attribute;

import io.sonicdeadlock.projectc.util.PropertiesLoader;

/**
 * Created by Alex on 10/8/2016.
 */
public class Settings extends Attribute {
    public static final String TYPE = "Settings";
    private static final String PROPERTIES_DIR = "player.settings.default",
            LOOK_ENTITY_MAX_COUNT = "lookEntityMaxCount";

    private int lookEntityMaxCount = Integer.MIN_VALUE;

    public int getLookEntityMaxCount() {
        if (lookEntityMaxCount == Integer.MIN_VALUE)
            lookEntityMaxCount = Integer.parseInt(PropertiesLoader.getProperty(PROPERTIES_DIR, LOOK_ENTITY_MAX_COUNT));
        return lookEntityMaxCount;
    }


    public void setLookEntityMaxCount(int lookEntityMaxCount) {
        this.lookEntityMaxCount = lookEntityMaxCount;
    }

    public String getType() {
        return TYPE;
    }


}
