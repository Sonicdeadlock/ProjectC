package io.sonicdeadlock.projectc.item;

import io.sonicdeadlock.projectc.entity.Player;
import io.sonicdeadlock.projectc.entity.Selectable;
import io.sonicdeadlock.projectc.util.UserPrintStream;
import io.sonicdeadlock.projectc.world.Loadable;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Alex on 10/13/2016.
 */
public abstract class Item implements Loadable,Selectable{
    private List<String> performableActions=new ArrayList<String>();
    protected static final String EXAMINE_ACTION = "Examine",REMOVE_ACTION="Remove";
    protected static String EXAMINE_TEXT = "Nothing interesting happens";

    public Item(){
        addPerformableAction(EXAMINE_ACTION);
        addPerformableAction(REMOVE_ACTION);
    }

    public boolean canStack(){
        return true;
    }

    public double getMass(){
        return 0;
    }
    protected void addPerformableAction(String performableAction){
        performableActions.add(performableAction);
    }

    @Override
    public List<String> getPerformableActions() {
        return performableActions;
    }

    public void performAction(String action, Player player){
        if(action.equalsIgnoreCase(EXAMINE_ACTION))
            UserPrintStream.getInstance().println(EXAMINE_TEXT);
        else if(action.equals(REMOVE_ACTION)){
            player.getInventory().remove(this);
        }
    }

    public JSONObject getSaveObject() {
        JSONObject saveObject = new JSONObject();
        return saveObject;
    }

    public void load(JSONObject saveObject) {
    }

}
