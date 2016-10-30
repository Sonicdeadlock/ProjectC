package io.sonicdeadlock.projectc.ui.gui;

import io.sonicdeadlock.projectc.entity.Entity;
import io.sonicdeadlock.projectc.entity.Player;
import io.sonicdeadlock.projectc.util.UserOutputStream;
import io.sonicdeadlock.projectc.util.UserPrintStream;
import io.sonicdeadlock.projectc.world.World;

import java.util.List;

/**
 * Created by Alex on 10/8/2016.
 */
public class InputHandler {

    public static void handleInput(ExecuteEvent executeEvent,World world,Player player){
        String[] parts = executeEvent.getLine().split(" ");
        if(parts[0].isEmpty())
            UserPrintStream.getInstance().println( new GameResponse("No Input passed").getResponse());
        else
            UserPrintStream.getInstance().println(handleInput(parts,world,player).getResponse());
        UserOutputStream.getInstance().flush();
    }

    private static GameResponse handleInput(String[] inputParts,World world,Player player){
        String action = inputParts[0];
        if(action.equalsIgnoreCase("move") || action.equalsIgnoreCase("m")){
           return movePlayer(inputParts,world,player);
        }else if (action.equalsIgnoreCase("moveTo") || action.equalsIgnoreCase("mt")){
            return movePlayerTo(inputParts,world,player);
        }else if(action.equalsIgnoreCase("look") || action.equalsIgnoreCase("l")){
            return playerLook(inputParts,world,player);
        }else if(action.equalsIgnoreCase("self") || action.equalsIgnoreCase("s")){
            return examineSelf(player);
        }else if(action.equalsIgnoreCase("select")){
            return select(inputParts,world,player);
        }else if(action.equalsIgnoreCase("perform")){
           return perform(inputParts,world,player);
        }
        return new GameResponse("Unrecognized action: "+action);
    }

    private static GameResponse movePlayer(String[] inputParts,World world,Player player){
        if(inputParts.length>3){
            if(inputParts[1].equalsIgnoreCase("to"))
                return new GameResponse("Did you mean \"moveTo\" (no space)");
            return new GameResponse("Unrecognized field: "+inputParts[3]);
        }else if(inputParts.length<2){
            return new GameResponse("Input x and y required ");
        }
        else{
            int deltaX,deltaY;
            try{
                deltaX = Integer.parseInt(inputParts[1]);
                deltaY = Integer.parseInt(inputParts[2]);
                player.move(deltaX,deltaY);
                return new GameResponse("You are now at :"+player.getX()+","+player.getY());
            }catch (NumberFormatException nfe){
                return new GameResponse("Input x and y should be whole numbers ");
            }
        }

    }

    private static GameResponse movePlayerTo(String[] inputParts,World world,Player player){
        if(inputParts.length>3){
            return new GameResponse("Unrecognized field: "+inputParts[3]);
        }else if(inputParts.length<2){
            return new GameResponse("Input x and y required ");
        }
        else{
            int x,y;
            try{
                x = Integer.parseInt(inputParts[1]);
                y = Integer.parseInt(inputParts[2]);
                player.moveTo(x,y);
                return new GameResponse("You are now at :"+x+","+y);
            }catch (NumberFormatException nfe){
                return new GameResponse("Input x and y should be whole numbers ");
            }
        }

    }

    private static List<Entity> playerLookAround(World world,Player player){
        int x = player.getX();
        int y = player.getY();
        int radius = player.getEyeSight().getViewDistance();

        return world.radialSearch(x, y, radius);
    }

    private static GameResponse playerLook(String[] inputParts,World world, Player player){
        if(inputParts.length==1 || inputParts[1].equalsIgnoreCase("around")){
            List<Entity> entities = playerLookAround(world,player);
            player.getEyeSight().incrementXP(entities.size());
            int printCount = entities.size()>player.getSettings().getLookEntityMaxCount()?player.getSettings().getLookEntityMaxCount():entities.size();
            StringBuilder response = new StringBuilder();
            for (int i = 0; i < printCount; i++) {
                response.append(entities.get(i)).append("\n");
            }
            return new GameResponse(response.toString());
        }
        return new GameResponse("Unrecognized look modifier: "+inputParts[1]);
    }

    private static GameResponse examineSelf(Player player){
        return new GameResponse(player.toString());
    }

    private static GameResponse select(String[] inputParts,World world,Player player){
        if(inputParts.length!=3){
            return new GameResponse("Select type and index also required");
        }
        String selectType = inputParts[1];
        if(selectType.equalsIgnoreCase("inventory") || selectType.equalsIgnoreCase("i")){
            // TODO: 10/8/2016 add select from inventory
        }else if(selectType.equalsIgnoreCase("world") || selectType.equalsIgnoreCase("w")){
            int index;
            try {
                index = Integer.parseInt(inputParts[2])-1;
            }catch (NumberFormatException nfe){
                return new GameResponse("Invalid index. It should be a whole number");
            }
            player.setSelected(playerLookAround(world,player).get(index));
        }
        return  new GameResponse("");
    }

    private static GameResponse perform(String[] inputParts,World world,Player player){
        StringBuilder actionString = new StringBuilder();
        for (int i = 1; i < inputParts.length; i++) {
            actionString.append(inputParts[i]);
            player.getSelected().performAction(actionString.toString(),player);
        }
        return new GameResponse("");
    }
}
