package io.sonicdeadlock.projectc.world;

import org.json.JSONObject;

/**
 * Created by Alex on 10/5/2016.
 */
public interface Loadable {
    void load(JSONObject saveObject);

    JSONObject getSaveObject();

    String getType();
}
