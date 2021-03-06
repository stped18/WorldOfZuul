package view_layer.controllers;

import domain_layer.game_elements.Axe;
import domain_layer.game_elements.AxeFactory;
import domain_layer.game_functionality.Command;
import domain_layer.game_functionality.CommandWord;
import domain_layer.game_functionality.Game;
import domain_layer.game_functionality.Player;
import domain_layer.game_locations.BlackSmith;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import view_layer.PlayerGraphics;
import view_layer.room_animations.GameAnimation;

/**
 *
 * @author steffen
 *
 * This controller has the responsibility for the conektions between the Blacksmith location class
 * and the blacksmith.fxml (view layer)
 */
public class BlacksmithController implements Initializable {

    @FXML
    private Label textArea;
    @FXML
    private AnchorPane anchorPane, Buypane;
    @FXML
    private Button backBtn;
    private final Player humanPlayer = Game.getInstanceOfSelf().getHumanPlayer();
    private final BlackSmith gameBlacksmith = (BlackSmith) Game.getInstanceOfSelf().getBlacksmith();
    private final GameAnimation animation = new GameAnimation(null);

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        Buypane.setVisible(false);
        animation.textAnimation(textArea, gameBlacksmith.roomEntrance(humanPlayer));
    }

    /**
     * Gets the information of the axe and tjek is the player have enough money to buy one
     *
     * @param humanPlayer
     * @param axe
     * @return
     */
    public String getAxeInfo(Player humanPlayer, Axe axe) {
        if (humanPlayer.getMoneyValue() >= axe.getPrice()) {
            humanPlayer.boughtAxe(axe);
            return "You just bought a " + axe.getDescription() + "!\n"
                + "It costs you " + axe.getPrice() + " gold coins"
                + "\nEnjoy it while it lasts!";
        } else {
            return "YOU NEED " + axe.getPrice() + " GOLD COINS TO BUY THIS AXE";
        }
    }

    /**
     * Tjeks if player have an axe. Tjeks if axe has taken damaget. Tjeks if player has money for
     * grinding the axe then fix players axe.
     *
     * @param event
     */
    @FXML
    private void handlerepair(MouseEvent event) {
        gameBlacksmith.getBlackSmithNPC();
        if (humanPlayer.getEquippedAxe() == null) {
            animation.textAnimation(textArea, gameBlacksmith.getBlackSmithNPC() + "You don't have an axe equipped");
        } else if (humanPlayer.getEquippedAxe().getDurability() == humanPlayer.getEquippedAxe().getStartDurability()) {
            animation.textAnimation(textArea, gameBlacksmith.getBlackSmithNPC() + "Your axe is fine! Come back if it ever gets dull");
        } else if (humanPlayer.getEquippedAxe().getDurability() < humanPlayer.getEquippedAxe().getStartDurability()) {
            if (humanPlayer.getMoneyValue() >= gameBlacksmith.fixAxePrice(humanPlayer)) {
                FixAxe();
            } else {
                animation.textAnimation(textArea, gameBlacksmith.getBlackSmithNPC() + "You do not have enough money");
            }

        }

    }

    /**
     * Grinding the axe Method.
     */
    public void FixAxe() {
        animation.textAnimation(textArea, gameBlacksmith.getBlackSmithNPC() + "I will grind your axe for you. Please wait");
        gameBlacksmith.grindSound();
        gameBlacksmith.grindAxe(humanPlayer.getEquippedAxe());
        humanPlayer.grindedAxe(gameBlacksmith.fixAxePrice(humanPlayer));
        animation.textAnimation(textArea, gameBlacksmith.getBlackSmithNPC() + "Your axe is done");
    }

    @FXML
    private void handleExits(KeyEvent event) {
        if (event.getCode().equals(KeyCode.DOWN) || event.getCode().equals(KeyCode.S)) {
            Command tester = new Command(CommandWord.GO, "back");
            Game.getInstanceOfSelf().goRoom(tester, anchorPane);
        } else {
            animation.textAnimation(textArea, "There is no road!");
        }
    }

    @FXML
    private void handleBackBtn(MouseEvent event) {
        backBtn.setDisable(true);
        Command tester = new Command(CommandWord.GO, "back");
        Game.getInstanceOfSelf().goRoom(tester, anchorPane);
    }

    @FXML
    private void BuyOnAction(ActionEvent event) {
        Buypane.setVisible(true);
        animation.textAnimation(textArea, "Which axe would you like to buy?");

    }

    @FXML
    private void HandlerIronAxe(MouseEvent event) {
        animation.textAnimation(textArea, getAxeInfo(humanPlayer, AxeFactory.createIronAxe()));
        PlayerGraphics.getInstanceOfSelf().setCharacterModel(false);
    }

    @FXML
    private void handlerSteelAxe(MouseEvent event) {
        animation.textAnimation(textArea, getAxeInfo(humanPlayer, AxeFactory.createSteelAxe()));
        PlayerGraphics.getInstanceOfSelf().setCharacterModel(false);
    }

    @FXML
    private void HandlerDiamondAxe(MouseEvent event) {
        animation.textAnimation(textArea, getAxeInfo(humanPlayer, AxeFactory.createDiamondAxe()));
        PlayerGraphics.getInstanceOfSelf().setCharacterModel(false);
    }

    @FXML
    private void handlerFireAxe(MouseEvent event) {
        animation.textAnimation(textArea, getAxeInfo(humanPlayer, AxeFactory.createFireAxe()));
        PlayerGraphics.getInstanceOfSelf().setCharacterModel(false);
    }

    @FXML
    private void handlerBack(MouseEvent event) {
        PlayerGraphics.getInstanceOfSelf().setCharacterModel(false);
    }

}
