package io.sonicdeadlock.projectc.ui.gui;

import io.sonicdeadlock.projectc.entity.Player;
import io.sonicdeadlock.projectc.world.World;

/**
 * Created by Alex on 10/8/2016.
 */
public class InputHandler {

    public static GameResponse handleInput(ExecuteEvent executeEvent,World world,Player player){
        String[] parts = executeEvent.getLine().split(" ");
        if(parts[0].isEmpty())
            return new GameResponse("No Input passed");
        return handleInput(parts,world,player);
    }

    private static GameResponse handleInput(String[] inputParts,World world,Player player){
        String action = inputParts[0];
        if(action.equalsIgnoreCase("move")){
           return movePlayer(inputParts,world,player);
        }else if (action.equalsIgnoreCase("moveTo")){
            return movePlayerTo(inputParts,world,player);
        }
        return new GameResponse("Unrecognized action: "+action);


    }

    private static GameResponse movePlayer(String[] inputParts,World world,Player player){
        if(inputParts.length>3){
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
}
