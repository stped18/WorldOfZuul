package view_layer;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

/**
 *
 * @author Oliver
 */
public class WorldOfZuul extends Application {

    private Scene scene;
    private final HighScoreGraphics highScoreGraphics = new HighScoreGraphics();

    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("room_fxml/StartScene.fxml"));

        scene = new Scene(root);
        stage.setResizable(false);
        stage.setTitle("The LumberJack");
        stage.getIcons().add(new Image(this.getClass().getResource("/pictures/baseCharacter.png").toString()));

        stage.setScene(scene);
        stage.show();
//        Makes sure that the players highscore gets recorded even if they exit the game.
        stage.setOnCloseRequest((WindowEvent event) -> {
            highScoreGraphics.closeGame();
        });
    }

    public static void main(String[] args) {
        launch(args);
    }

}
