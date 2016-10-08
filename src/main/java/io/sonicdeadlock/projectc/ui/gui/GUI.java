package io.sonicdeadlock.projectc.ui.gui;/**
 * Created by Alex on 10/8/2016.
 */

import io.sonicdeadlock.projectc.runtime.PreInit;
import io.sonicdeadlock.projectc.world.World;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class GUI extends Application {
    private static final Logger LOGGER = LogManager.getLogger(GUI.class);
    private World loadedWorld;
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        PreInit.getInstance().preInit();
        loadedWorld = new World();
        Group root = new Group();
        TextScene textScene = new TextScene(root);
        Text responseText = new Text(10,25,"");
        responseText.setStroke(Color.WHITE);
        root.getChildren().add(responseText);
        textScene.setOnExecute(event -> {
            responseText.setText(InputHandler.handleInput(event,loadedWorld,loadedWorld.getPlayer()).getResponse());
        });
        root.getChildren().add(textScene.getText());
        primaryStage.setScene(textScene);
        primaryStage.show();
    }
}
