package io.sonicdeadlock.projectc.entity.skill;

import org.json.JSONObject;

/**
 * Created by Alex on 10/7/2016.
 */
public class Sprint extends Skill {
    public static final String TYPE= "Sprint";
    @Override
    public String getName() {
        return TYPE;
    }

    @Override
    public void load(JSONObject saveObject) {
        super.load(saveObject);
    }

    @Override
    public JSONObject getSaveObject() {
        return super.getSaveObject();
    }

    public String getType(){
        return TYPE;
    }
}
