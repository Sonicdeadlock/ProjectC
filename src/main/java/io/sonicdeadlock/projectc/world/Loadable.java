package io.sonicdeadlock.projectc.world;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.json.JSONObject;

/**
 * Created by Alex on 10/5/2016.
 */
public interface Loadable {
    void load(JSONObject saveObject);
    @JsonIgnore
    JSONObject getSaveObject();
    String getType();
}
