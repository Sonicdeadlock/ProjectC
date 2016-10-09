package io.sonicdeadlock.projectc.ui.gui;/**
 * Created by Alex on 10/8/2016.
 */

import io.sonicdeadlock.projectc.entity.Player;
import io.sonicdeadlock.projectc.entity.Selectable;
import io.sonicdeadlock.projectc.runtime.PreInit;
import io.sonicdeadlock.projectc.util.UserOutputStream;
import io.sonicdeadlock.projectc.world.World;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
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
        HBox root = new HBox();
        VBox textGroup = new VBox();
        VBox rightSide = new VBox();
        textGroup.setPrefWidth(400);
        root.getChildren().add(textGroup);
        root.getChildren().add(rightSide);
        TextScene textScene = new TextScene(root);
        Text responseText = new Text(10,30,"");
        Text positionText = new Text(10,15,"");
        Text selectedText = new Text(10,30,"");
        responseText.setFont(Font.font ("Consolas"));
        positionText.setFont(Font.font ("Consolas"));
        selectedText.setFont(Font.font ("Consolas"));
        responseText.setStroke(Color.WHITE);
        positionText.setStroke(Color.WHITE);
        selectedText.setStroke(Color.WHITE);
        responseText.setFill(Color.WHITE);
        textScene.setOnExecute(event -> {
            Player player = loadedWorld.getPlayer();
            InputHandler.handleInput(event,loadedWorld,player);
            positionText.setText("("+player.getX()+","+player.getY()+")");
            selectedText.setText(getSelectedInformationText(player.getSelected()));
            loadedWorld.save();
        });
        rightSide.getChildren().add(positionText);
        rightSide.getChildren().add(selectedText);
        UserOutputStream.getInstance().setGuiText(responseText);

        textGroup.getChildren().add(textScene.getText());
        textGroup.getChildren().add(responseText);
        primaryStage.setScene(textScene);
        primaryStage.show();
    }

    private static String getSelectedInformationText(Selectable selectable){
        if (selectable == null) {
            return "";
        }
        StringBuilder sb = new StringBuilder();
        sb.append(selectable.toString()).append("\n");
        for (String s : selectable.getPerformableActions()) {
            sb.append("\t").append(s).append("\n");
        }
        return  sb.toString();
    }
}
