package io.sonicdeadlock.projectc.item;

import io.sonicdeadlock.projectc.entity.Player;
import io.sonicdeadlock.projectc.util.UserPrintStream;
import org.json.JSONObject;

import java.util.List;

/**
 * Created by Alex on 10/30/2016.
 */
public class WidgetItem extends Item {
    public static final String TYPE="Widget Item";
    private int count=0;

    public WidgetItem(){
        addPerformableAction("Increment");
        addPerformableAction("Get Count");
    }

    @Override
    public void load(JSONObject saveObject) {
        super.load(saveObject);
        this.count = saveObject.getInt("count");
    }

    @Override
    public JSONObject getSaveObject() {
        JSONObject saveObject = super.getSaveObject();
        saveObject.put("count",count);
        return saveObject;
    }

    @Override
    public String getType() {
        return TYPE;
    }

    @Override
    public List<String> getPerformableActions() {
        return super.getPerformableActions();
    }

    private void printCurrentCount(){
        UserPrintStream.getInstance().println("The current count is : "+count);
    }

    @Override
    public void performAction(String action, Player player) {
        super.performAction(action,player);
        if(action.equals("Increment")){
            count++;
            printCurrentCount();
        }else if(action.equals("Get Count")){
            printCurrentCount();
        }
    }
}
