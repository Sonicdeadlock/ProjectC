package io.sonicdeadlock.projectc.entity;

import io.sonicdeadlock.projectc.util.UserOutputStream;
import io.sonicdeadlock.projectc.util.UserPrintStream;
import io.sonicdeadlock.projectc.world.Loadable;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Alex on 10/5/2016.
 */
public abstract class Entity implements Loadable,Selectable{
    private int x,y;
    private List<String> performableActions=new ArrayList<String>();
    protected static final String EXAMINE_ACTION = "Examine";
    protected static String EXAMINE_TEXT = "Nothing interesting happens";

    protected Entity(){
        addPerformableAction(EXAMINE_ACTION);
    }

    public Entity(int x, int y) {
        this();
        this.y = y;
        this.x = x;

    }

    protected void addPerformableAction(String performableAction){
        performableActions.add(performableAction);
    }

    public void performAction(String action){
        if(action.equalsIgnoreCase(EXAMINE_ACTION))
            UserPrintStream.getInstance().println(EXAMINE_TEXT);
    }

    public List<String> getPerformableActions() {
        return performableActions;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }


    public JSONObject getSaveObject() {
        JSONObject saveObject = new JSONObject();
        saveObject.put("x",x);
        saveObject.put("y",y);
        return saveObject;
    }

    public void load(JSONObject saveObject) {
        this.x = saveObject.getInt("x");
        this.y = saveObject.getInt("y");
    }

    public static String getType(){
        throw new UnsupportedOperationException();
    }

    public char getMapCharacter(){
        throw new UnsupportedOperationException();
    }
}
