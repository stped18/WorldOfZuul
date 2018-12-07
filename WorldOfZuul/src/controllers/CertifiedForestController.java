package controllers;

import game_functionality.Command;
import game_functionality.CommandWord;
import game_functionality.Game;
import game_locations.CertifiedForest;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.util.Duration;

/**
 *
 * @author oliver
 */
public class CertifiedForestController extends ForestController implements Initializable {

    private final CertifiedForest gameForest = (CertifiedForest) Game.getInstanceOfSelf().getCertifiedForest();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        TranslateTransition down = new TranslateTransition(Duration.seconds(1.5), player);
        down.setFromY(-170);
        down.setByY(170);
        down.play();
        textArea.setText(gameForest.roomEntrance(humanPlayer));
        player.setImage(new Image(humanPlayer.getCharacterModel().toURI().toString()));
        smallTreeLabel.setText(Integer.toString(gameForest.countSmallTrees()));
        mediumTreeLabel.setText(Integer.toString(gameForest.countMediumTrees()));
        largeTreeLabel.setText(Integer.toString(gameForest.countLargeTrees()));
    }

    @FXML
    private void handleOption1(MouseEvent event) {
        if (!running) {
            if (gameForest.playerCanCarryMoreTree(humanPlayer) && gameForest.thereIsMoreTreesToCut()) {
                int number = gameForest.chopWood(humanPlayer);
                treeAnimationToLargeTree(number);
                humanPlayer.addChoppedTreesInCertifiedForest();
            } else if (gameForest.playerCanCarryMoreTree(humanPlayer) && !gameForest.thereIsMoreTreesToCut()) {
                textArea.setText("There is no more trees to fell right now!"
                        + "\nYou have to wait for the forest to regrow!");
            } else if (gameForest.thereIsMoreTreesToCut() && !gameForest.playerCanCarryMoreTree(humanPlayer)) {
                textArea.setText("You are carrying too much wood!\n"
                        + "Sell or store your logs!");
            } else {
                textArea.setText("There is no trees to fell and your backpack is full!");
            }

        } else {
//            do nothing
        }
    }

    @FXML
    private void handleOption2(MouseEvent event) {
        textArea.setText("There are " + gameForest.countFellableTrees() + " trees ready to be felled!");
    }

    @FXML
    private void handleOption3(MouseEvent event) {
        int amountOfSeedsPlanted = gameForest.replantTrees(humanPlayer);
        if (amountOfSeedsPlanted > 0) {
            textArea.setText("You just planted " + (amountOfSeedsPlanted > 1
                    ? amountOfSeedsPlanted + " saplings!" : "1 sapling!"));
            
        } else if (amountOfSeedsPlanted == 0) {
            textArea.setText("You don't have any saplings, go buy some!");
        } else {
            textArea.setText("You haven't chopped any trees today!");
        }
    }

    @FXML
    private void handleExits(KeyEvent event) {
        if (event.getCode().equals(KeyCode.UP) || event.getCode().equals(KeyCode.W)) {
            Game.getInstanceOfSelf().setDirection("goUp");
            Command tester = new Command(CommandWord.GO, "trailer");
            TranslateTransition up = new TranslateTransition(Duration.seconds(1.5), player);
            up.setByY(-170);
            up.setOnFinished((ActionEvent e) -> {
              Game.getInstanceOfSelf().goRoom(tester, anchorPane);
            });
            up.play();
        } else {
            textArea.setText("There is no road!");
        }
    }
}
