package io.sonicdeadlock.projectc.entity.attribute;

import io.sonicdeadlock.projectc.util.PropertiesLoader;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Alex on 10/8/2016.
 */
public class Settings extends Attribute{
    private static final String TYPE= "Settings";
    private Map<String,String> settings = new HashMap<>();
    private static final String PROPTERTIES_DIR = "player.settings.default",
                                LOOK_ENTITY_MAX_COUNT="lookEntityMaxCount";

    public int getLookEntityMaxCount(){
        if(!settings.containsKey(LOOK_ENTITY_MAX_COUNT))
            settings.put(LOOK_ENTITY_MAX_COUNT, PropertiesLoader.getProperty(PROPTERTIES_DIR,LOOK_ENTITY_MAX_COUNT));
        return Integer.parseInt(settings.get(LOOK_ENTITY_MAX_COUNT));
    }


    @Override
    public void load(JSONObject saveObject) {
        JSONObject saveSettings =saveObject.getJSONObject("settings");
        saveSettings.keys().forEachRemaining(s -> settings.put(s,saveSettings.getString(s)));
    }

    @Override
    public JSONObject getSaveObject() {
        JSONObject saveObject = new JSONObject();
        saveObject.put("settings",settings);
        return saveObject;
    }

    public static String getType(){
        return TYPE;
    }


}
