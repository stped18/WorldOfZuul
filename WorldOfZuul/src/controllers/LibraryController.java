package controllers;

import game_functionality.Command;
import game_functionality.CommandWord;
import game_functionality.Game;
import game_functionality.Player;
import game_locations.Library;
import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

public class LibraryController implements Initializable {

    @FXML
    private Label textArea;
    @FXML
    private AnchorPane anchorPane;
    @FXML
    private Button option1, option2;
    @FXML
    private ImageView player, map;
    private final Player humanPlayer = Game.getInstanceOfSelf().getHumanPlayer();
    private final Library gameLibrary = (Library) Game.getInstanceOfSelf().getLibrary();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        textArea.setText(gameLibrary.roomEntrance(humanPlayer));
        File file = new File("src/pictures/baseCharacter.png");
        Image image = new Image(file.toURI().toString());
        player.setImage(image);
    }

    @FXML
    private void handleOption1(MouseEvent event) {
        textArea.setText(gameLibrary.option1(humanPlayer));
    }

    @FXML
    private void handleOption2(MouseEvent event) {
        textArea.setText(gameLibrary.option2(humanPlayer));
    }

    @FXML
    private void handleExits(KeyEvent event) {
        if (event.getCode().equals(KeyCode.DOWN) || event.getCode().equals(KeyCode.S)) {
            Command tester = new Command(CommandWord.GO, "back");
            Game.getInstanceOfSelf().goRoom(tester, anchorPane);
        } else {
            textArea.setText("There is no road!");
        }
    }
}