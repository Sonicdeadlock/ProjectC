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
        String action = parts[0];
        return new GameResponse("You want to perform this action "+action);
    }
}
