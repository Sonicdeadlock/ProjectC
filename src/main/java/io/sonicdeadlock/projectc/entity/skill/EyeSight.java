package io.sonicdeadlock.projectc.entity.skill;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * Created by Alex on 10/8/2016.
 */
public class EyeSight extends Skill {
    public static final String TYPE = "EyeSight";

    @Override
    public String getName() {
        return TYPE;
    }


    public String getType() {
        return TYPE;
    }

    @JsonIgnore
    public int getViewDistance() {
        return 5 + getCurrentLevel() / 3;
    }
}
