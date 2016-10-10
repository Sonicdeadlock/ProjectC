package io.sonicdeadlock.projectc.entity.skill;

import org.json.JSONObject;

/**
 * Created by Alex on 10/8/2016.
 */
public class EyeSight extends Skill{
    public static final String TYPE= "EyeSight";
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

    public int getViewDistance(){
        return 5+getCurrentLevel()/3;
    }
}
