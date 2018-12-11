package view_layer.controllers;

import domain_layer.game_functionality.Command;
import domain_layer.game_functionality.CommandWord;
import domain_layer.game_functionality.Game;
import domain_layer.game_functionality.Player;
import domain_layer.game_locations.LocalVillage;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.animation.TranslateTransition;
import javafx.fxml.FXML;
import java.io.File;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;

/**
 *
 * @author michael
 */
public class LocalVillageController implements Initializable {

    @FXML
    private Label textArea;
    @FXML
    private AnchorPane anchorPane;
    @FXML
    private Button backBtn;
    @FXML
    private ImageView player, store, blacksmith, library, sun;
    private final Player humanPlayer = Game.getInstanceOfSelf().getHumanPlayer();
    private final LocalVillage gameVillage = (LocalVillage) Game.getInstanceOfSelf().getLocalVillage();
    private boolean running;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        running = true;
        backBtn.setDisable(true);
        transition();
        textArea.setText(gameVillage.roomEntrance(humanPlayer));
        player.setImage(new Image(humanPlayer.getCharacterModel().toURI().toString()));
        choosingRainScenario();
    }

    private void choosingRainScenario() {
        switch (getClimateScenario()) {
            case -1:
                rainDrops(50);
                break;
            case -2:
                rainDrops(100);
                break;
            case -3:
                rainDrops(150);
                break;
            case -4:
                rainDrops(200);
                break;
            case -5:
                rainDrops(250);
                break;
            default:
                sun.setVisible(true);
        }
    }

    private void rainDrops(int numOfRainDropsOnScreen) {
        ImageView[] rainDrops = new ImageView[numOfRainDropsOnScreen];
        for (int i = 0; i < rainDrops.length; i++) {
            rainDrops[i] = new ImageView(new Image(new File("src/pictures/rain.png").toURI().toString()));
            anchorPane.getChildren().add(rainDrops[i]);
            makeItRainBaby(rainDrops[i]);
        }
    }

    private int getClimateScenario() {
        int climatePoints = humanPlayer.getClimatePointsValue();
        if (climatePoints < 0 && climatePoints > -19) {
            return -1;
        } else if (climatePoints < -19 && climatePoints > -29) {
            return -2;
        } else if (climatePoints < -29 && climatePoints > -39) {
            return -3;
        } else if (climatePoints < -39 && climatePoints > -49) {
            return -4;
        } else if (climatePoints < -49 && climatePoints > -59) {
            return -5;
        }
        return 0;
    }

    private void makeItRainBaby(ImageView rain) {
        rain.setTranslateX((Math.random() * 600) + 1);
        rain.setTranslateY((Math.random() * 400) + 1);
        int timeToGoToBottom = (int) (400 - rain.getTranslateY()) * 18;
        final Timeline timeline = new Timeline();
        timeline.setCycleCount(1);
        timeline.getKeyFrames().add(new KeyFrame(Duration.millis(timeToGoToBottom), new KeyValue(rain.translateYProperty(), 400)));
        timeline.setOnFinished((ActionEvent event) -> {
            makeItRainBaby(rain);
        });
        timeline.play();
    }

    @FXML
    private void handleBackBtn(MouseEvent event) {
        backBtn.setDisable(true);
        textArea.setVisible(false);
        if (!running) {
            running = true;
            TranslateTransition backTransition = new TranslateTransition(Duration.seconds(1.5), player);

            switch (Game.getInstanceOfSelf().getDirection()) {
                case "goRight":
                    backTransition.setByX(-276);
                    backTransition.setOnFinished((ActionEvent) -> {
                        Command tester = new Command(CommandWord.GO, "trailer");
                        Game.getInstanceOfSelf().goRoom(tester, anchorPane);
                    });
                    backTransition.play();
                    Game.getInstanceOfSelf().setDirection("goLeft");
                    break;
                case "goStore":
                    running = true;
                    TranslateTransition transistionToStore = new TranslateTransition(Duration.seconds(1.5), player);
                    transistionToStore.setByX(store.getLayoutX() - 276);
                    transistionToStore.setByY(store.getLayoutY() - 170);
                    transistionToStore.setOnFinished((ActionEvent) -> {
                        Command tester = new Command(CommandWord.GO, "store");
                        Game.getInstanceOfSelf().goRoom(tester, anchorPane);
                    });
                    transistionToStore.play();
                    Game.getInstanceOfSelf().setDirection("goStore");
                    break;
                case "goBlacksmith":
                    running = true;
                    TranslateTransition transistionToBlacksmith = new TranslateTransition(Duration.seconds(1.5), player);
                    transistionToBlacksmith.setByX(blacksmith.getLayoutX() - 276);
                    transistionToBlacksmith.setByY(blacksmith.getLayoutY() - 170);
                    transistionToBlacksmith.setOnFinished((ActionEvent) -> {
                        Command tester = new Command(CommandWord.GO, "blacksmith");
                        Game.getInstanceOfSelf().goRoom(tester, anchorPane);
                    });
                    transistionToBlacksmith.play();
                    Game.getInstanceOfSelf().setDirection("goBlacksmith");
                    break;
                case "goLibrary":
                    running = true;
                    TranslateTransition transistionToLibrary = new TranslateTransition(Duration.seconds(1.5), player);
                    transistionToLibrary.setByX(library.getLayoutX() - 276);
                    transistionToLibrary.setByY(library.getLayoutY() - 170);
                    transistionToLibrary.setOnFinished((ActionEvent) -> {
                        Command tester = new Command(CommandWord.GO, "library");
                        Game.getInstanceOfSelf().goRoom(tester, anchorPane);
                    });
                    transistionToLibrary.play();
                    Game.getInstanceOfSelf().setDirection("goLibrary");
                    break;
            }
        }
    }

    @FXML
    private void handleGoToStore(MouseEvent event) {
        textArea.setVisible(false);
        backBtn.setDisable(true);
        if (!running) {
            running = true;
            TranslateTransition transistionToStore = new TranslateTransition(Duration.seconds(1.5), player);
            transistionToStore.setByX(store.getLayoutX() - 276);
            transistionToStore.setByY(store.getLayoutY() - 170);
            transistionToStore.setOnFinished((ActionEvent) -> {
                Command tester = new Command(CommandWord.GO, "store");
                Game.getInstanceOfSelf().goRoom(tester, anchorPane);
            });
            transistionToStore.play();
            Game.getInstanceOfSelf().setDirection("goStore");
        }
    }

    @FXML
    private void handleGoToBlacksmith(MouseEvent event) {
        textArea.setVisible(false);
        backBtn.setDisable(true);
        if (!running) {
            running = true;
            TranslateTransition transistionToBlacksmith = new TranslateTransition(Duration.seconds(1.5), player);
            transistionToBlacksmith.setByX(blacksmith.getLayoutX() - 276);
            transistionToBlacksmith.setByY(blacksmith.getLayoutY() - 170);
            transistionToBlacksmith.setOnFinished((ActionEvent) -> {
                Command tester = new Command(CommandWord.GO, "blacksmith");
                Game.getInstanceOfSelf().goRoom(tester, anchorPane);
            });
            transistionToBlacksmith.play();
            Game.getInstanceOfSelf().setDirection("goBlacksmith");
        }
    }

    @FXML
    private void handleGoToLibrary(MouseEvent event) {
        textArea.setVisible(false);
        backBtn.setDisable(true);
        if (!running) {
            running = true;
            TranslateTransition transistionToLibrary = new TranslateTransition(Duration.seconds(1.5), player);
            transistionToLibrary.setByX(library.getLayoutX() - 276);
            transistionToLibrary.setByY(library.getLayoutY() - 170);
            transistionToLibrary.setOnFinished((ActionEvent) -> {
                Command tester = new Command(CommandWord.GO, "library");
                Game.getInstanceOfSelf().goRoom(tester, anchorPane);
            });
            transistionToLibrary.play();
            Game.getInstanceOfSelf().setDirection("goLibrary");
        }
    }

    @FXML
    private void handleExits(KeyEvent event) {
        if (!running) {
            if (event.getCode().equals(KeyCode.LEFT) || event.getCode().equals(KeyCode.A)) {
                running = true;
                textArea.setVisible(false);
                TranslateTransition transistionToTrailer = new TranslateTransition(Duration.seconds(1.5), player);
                transistionToTrailer.setByX(-276);
                transistionToTrailer.setOnFinished((ActionEvent) -> {
                    Command tester = new Command(CommandWord.GO, "trailer");
                    Game.getInstanceOfSelf().goRoom(tester, anchorPane);
                });
                transistionToTrailer.play();
                Game.getInstanceOfSelf().setDirection("goLeft");
            } else {
                textArea.setText("There is no road!");
            }
        }
    }

    private void transition() {
        TranslateTransition roomTransition = new TranslateTransition(Duration.seconds(1.5), player);
        switch (Game.getInstanceOfSelf().getDirection()) {
            case "goRight":
                player.setLayoutX(0);
                roomTransition.setByX(276);
                roomTransition.setOnFinished((ActionEvent) -> {
                    textArea.setVisible(true);
                    running = false;
                    backBtn.setDisable(false);

                });
                roomTransition.play();
                break;
            case "goStore":
                player.setLayoutX(store.getLayoutX());
                player.setLayoutY(store.getLayoutY());
                roomTransition.setByX(276 - store.getLayoutX());
                roomTransition.setByY(170 - store.getLayoutY());
                roomTransition.setOnFinished((ActionEvent) -> {
                    textArea.setVisible(true);
                    running = false;
                    backBtn.setDisable(false);

                });
                roomTransition.play();
                break;
            case "goBlacksmith":
                player.setLayoutX(blacksmith.getLayoutX());
                player.setLayoutY(blacksmith.getLayoutY());
                roomTransition.setByX(276 - blacksmith.getLayoutX());
                roomTransition.setByY(170 - blacksmith.getLayoutY());
                roomTransition.setOnFinished((ActionEvent) -> {
                    textArea.setVisible(true);
                    running = false;
                    backBtn.setDisable(false);

                });
                roomTransition.play();
                break;
            case "goLibrary":
                player.setLayoutX(library.getLayoutX());
                player.setLayoutY(library.getLayoutY());
                roomTransition.setByX(276 - library.getLayoutX());
                roomTransition.setByY(170 - library.getLayoutY());
                roomTransition.setOnFinished((ActionEvent) -> {
                    textArea.setVisible(true);
                    running = false;
                    backBtn.setDisable(false);

                });
                roomTransition.play();
                break;
        }
    }
}
