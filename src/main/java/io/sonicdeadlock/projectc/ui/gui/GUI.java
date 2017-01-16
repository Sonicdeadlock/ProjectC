package io.sonicdeadlock.projectc.ui.gui;/**
 * Created by Alex on 10/8/2016.
 */

import io.sonicdeadlock.projectc.entity.Entity;
import io.sonicdeadlock.projectc.entity.Player;
import io.sonicdeadlock.projectc.entity.Selectable;
import io.sonicdeadlock.projectc.runtime.PreInit;
import io.sonicdeadlock.projectc.util.UserOutputStream;
import io.sonicdeadlock.projectc.world.World;
import javafx.application.Application;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

public class GUI extends Application {
    private static final Logger LOGGER = LogManager.getLogger(GUI.class);
    private World loadedWorld;

    public static void main(String[] args) {
        launch(args);
    }

    private static String getSelectedInformationText(Selectable selectable) {
        if (selectable == null) {
            return "";
        }
        StringBuilder sb = new StringBuilder();
        sb.append(selectable.toString()).append("\n");
        for (String s : selectable.getPerformableActions()) {
            sb.append("\t").append(s).append("\n");
        }
        return sb.toString();
    }

    private static String getMap(World world, Player player) {
        int x = player.getX();
        int y = player.getY();
        int radius = player.getEyeSight().getViewDistance();
        List<Entity> entities = world.radialSearch(x, y, radius);
        char[][] grid = new char[radius * 2 + 1][radius * 2 + 1];
        entities.forEach(entity -> {
            int[] difference = getDistanceFromPlayer(entity, player);
            grid[difference[0] + radius][difference[1] + radius] = entity.getMapCharacter();
        });
        grid[radius][radius] = 'P';
        StringBuilder map = new StringBuilder();
        for (char[] chars : grid) {
            for (char aChar : chars) {
                map.append(aChar);
            }
            map.append("\n");
        }
        return map.toString();

    }

    private static int[] getDistanceFromPlayer(Entity e, Player player) {
        int[] result = new int[2];
        result[1] = e.getX() - player.getX();
        result[0] = e.getY() - player.getY();
        return result;
    }

    @Override
    public void start(Stage primaryStage) {
        PreInit.getInstance();
        loadedWorld = new World();
        HBox root = new HBox();
        VBox textGroup = new VBox();
        VBox rightSide = new VBox();
        textGroup.setPrefWidth(400);
        root.getChildren().add(textGroup);
        root.getChildren().add(rightSide);
        TextScene textScene = new TextScene(root);
        Text responseText = new Text(10, 30, "");
        Text positionText = new Text(10, 15, "");
        Text selectedText = new Text(10, 30, "");
        Text mapText = new Text(10, 30, "");
        responseText.setFont(Font.font("Consolas"));
        positionText.setFont(Font.font("Consolas"));
        selectedText.setFont(Font.font("Consolas"));
        mapText.setFont(Font.font("Consolas"));
        responseText.setStroke(Color.WHITE);
        positionText.setStroke(Color.WHITE);
        selectedText.setStroke(Color.WHITE);
        mapText.setStroke(Color.WHITE);
        responseText.setFill(Color.WHITE);
        textScene.setOnExecute(event -> {
            Player player = loadedWorld.getPlayer();
            InputHandler.handleInput(event, loadedWorld, player);
            positionText.setText("(" + player.getX() + "," + player.getY() + ")");
            selectedText.setText(getSelectedInformationText(player.getSelected()));
            mapText.setText(getMap(loadedWorld, player));
            loadedWorld.save();
        });
        rightSide.getChildren().add(positionText);
        rightSide.getChildren().add(selectedText);
        UserOutputStream.getInstance().setGuiText(responseText);

        textGroup.getChildren().add(textScene.getText());
        textGroup.getChildren().add(responseText);
        textGroup.getChildren().add(mapText);
        primaryStage.setScene(textScene);
        primaryStage.show();
    }
}
