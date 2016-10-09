package io.sonicdeadlock.projectc.entity;

import org.json.JSONObject;

/**
 * Created by Alex on 10/5/2016.
 */
public class Widget extends Entity {
    private static int GLOBAL_COUNTER =0;
    private int index;
    private static final String TYPE = "Widget";

    public Widget(int x, int y) {
        super(x, y);
        index=GLOBAL_COUNTER;
        GLOBAL_COUNTER++;

    }

    Widget(){
        super();
        addPerformableAction("doFoo");
        addPerformableAction("doBar");
    }

    public JSONObject getSaveObject() {
        JSONObject saveObject = super.getSaveObject();
        saveObject.put("index",index);
        return saveObject;
    }

    public static String getType(){
        return TYPE;
    }

    @Override
    public void performAction(String action) {
        super.performAction(action);
    }

    public void load(JSONObject saveObject) {
        super.load(saveObject);
        this.index = saveObject.getInt("index");
    }

    public String toString(){
        return "Hey, I'm a widget at "+getX()+","+getY();
    }
}
