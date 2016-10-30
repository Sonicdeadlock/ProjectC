package io.sonicdeadlock.projectc.entity;

import io.sonicdeadlock.projectc.item.WidgetItem;
import io.sonicdeadlock.projectc.util.UserOutputStream;
import io.sonicdeadlock.projectc.util.UserPrintStream;
import org.json.JSONObject;

/**
 * Created by Alex on 10/5/2016.
 */
public class Widget extends Entity {
    private static int GLOBAL_COUNTER =0;
    private int index;
    public static final String TYPE = "Widget";

    public Widget(int x, int y) {
        super(x, y);
        index=GLOBAL_COUNTER;
        GLOBAL_COUNTER++;

    }

    public Widget(){
        super();
        addPerformableAction("doFoo");
        addPerformableAction("doBar");
        addPerformableAction("Add Item");
    }

    public JSONObject getSaveObject() {
        JSONObject saveObject = super.getSaveObject();
        saveObject.put("index",index);
        return saveObject;
    }

    public  String getType(){
        return TYPE;
    }

    @Override
    public void performAction(String action,Player player) {
        super.performAction(action,player);
        if(action.equals("doFoo")){
            UserPrintStream.getInstance().println("Foo Happened");
        }else if(action.equals("doBar")){
            UserPrintStream.getInstance().println("Bar Happened");
            UserPrintStream.getInstance().println("Which you might consider interesting!");
        }else if(action.equals("Add Item")){
            player.getInventory().add(new WidgetItem());
        }
    }

    public void load(JSONObject saveObject) {
        super.load(saveObject);
        this.index = saveObject.getInt("index");
    }

    public String toString(){
        return "Hey, I'm a widget at "+getX()+","+getY();
    }

    @Override
    public char getMapCharacter() {
        return 'w';
    }
}
