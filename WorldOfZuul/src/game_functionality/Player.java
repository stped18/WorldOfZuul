package game_functionality;

import game_elements.Axe;
import game_elements.BackPack;
import game_elements.BackPackFactory;
import game_elements.Tree;
import game_locations.Room;
import game_locations.Trailer;
import java.io.File;
import java.util.ArrayList;

/**
 *
 * @author oliver
 * @date 1/12/2018
 */
public class Player {

    private final static int MIN_CLIMATEPOINTS = -250;

    private int money;
    private int climatePoints;
    private boolean giftHasBeenGivenToday;
    private Room currentRoom = null;
    private Axe equippedAxe;
    private BackPack equippedBackPack;
    private int amountOfSaplingsCarrying;
    private int numChoppedTreesWithoutPlantingSaplings;
    private final Trailer trailer;
    private Room previousRoom;
    private boolean hasSlept;
    private String direction;
    private final File baseModelFile = new File("src/pictures/baseCharacter.png");
    private final File baseModelRightFile = new File("src/pictures/baseCharacterRight.png");
    private final File modelStarterAxeFile = new File("src/pictures/characterWithStarterAxe.png");
    private final File modelStarterAxeRightFile = new File("src/pictures/characterWithStarterAxeRight.png");
    private File characterModel;
    private final File modelIronAxeFile = new File("src/pictures/characterWithIronAxe.png");
    private final File modelIronAxeRightFile = new File("src/pictures/characterWithIronAxeRight.png");
    private final File modelSteelAxeFile = new File("src/pictures/characterWithSteelAxe.png");
    private final File modelSteelAxeRightFile = new File("src/pictures/characterWithSteelAxeRight.png");
    private final File modelDiamondAxeFile = new File("src/pictures/characterWithDiamondAxe.png");
    private final File modelDiamondAxeRightFile = new File("src/pictures/characterWithDiamondAxeRight.png");
    private final File modelFireAxeFile = new File("src/pictures/characterWithFireAxe.png");
    private final File modelFireAxeRightFile = new File("src/pictures/characterWithFireAxeRight.png");

    public Player(Trailer trailer) {
        this.equippedBackPack = BackPackFactory.createStarterBackPack();
        this.trailer = trailer;
        this.previousRoom = trailer;
        characterModel = baseModelFile;
    }

    public File getCharacterModel() {
        return characterModel;
    }

    public void setCharacterModel(boolean characterGoingRight) {
        if (!characterGoingRight) {
            if (equippedAxe == null) {
                characterModel = baseModelFile;
            } else if (equippedAxe.getDescription().equals("Starter Axe")) {
                characterModel = modelStarterAxeFile;
            } else if (equippedAxe.getDescription().equals("Iron Axe")) {
                characterModel = modelIronAxeFile;
            } else if (equippedAxe.getDescription().equals("Steel Axe")) {
                characterModel = modelSteelAxeFile;
            } else if (equippedAxe.getDescription().equals("Diamond Axe")) {
                characterModel = modelDiamondAxeFile;
            } else if (equippedAxe.getDescription().equals("Fire Axe")) {
                characterModel = modelFireAxeFile;
            }
        } else {
            if (equippedAxe == null) {
                characterModel = baseModelRightFile;
            } else if (equippedAxe.getDescription().equals("Starter Axe")) {
                characterModel = modelStarterAxeRightFile;
            } else if (equippedAxe.getDescription().equals("Iron Axe")) {
                characterModel = modelIronAxeRightFile;
            } else if (equippedAxe.getDescription().equals("Steel Axe")) {
                characterModel = modelSteelAxeRightFile;
            } else if (equippedAxe.getDescription().equals("Diamond Axe")) {
                characterModel = modelDiamondAxeRightFile;
            } else if (equippedAxe.getDescription().equals("Fire Axe")) {
                characterModel = modelFireAxeRightFile;
            }
        }
    }

    /**
     * Used to determine whether or not the game should end. If the climatepoints goes over this
     * threshold, the game is over, the world has been destroyed.
     *
     * @return int max_climatepoints
     */
    public static int getMIN_CLIMATEPOINTS() {
        return MIN_CLIMATEPOINTS;
    }

    public int getTotalValue() {
        int totalValueOfItems = money + equippedBackPack.getPrice();
        for (Tree tree : equippedBackPack.getLogsInBackPack()) {
            totalValueOfItems += tree.getTreePrice();
        }
        if (playerHasAnAxe()) {
            totalValueOfItems += equippedAxe.getPrice();
        }
        return totalValueOfItems;
    }

    public int getMoney() {
        return money;
    }

    public void addMoney(int moneyAmount) {
        this.money += moneyAmount;
    }

    public int getClimatePoints() {
        return climatePoints;
    }

    public void addClimatePoints(int treeClimatePoints) {
        climatePoints += treeClimatePoints;
    }

    /**
     * @return The room that the player currently resides in
     */
    public Room getCurrentRoom() {
        return this.currentRoom;
    }

    /**
     * Used to move the player around the rooms.
     *
     * @param newRoom: The room that the player is moving to
     */
    public void setCurrentRoom(Room newRoom) {
        if (currentRoom != null) {
            previousRoom = currentRoom;
        }
        currentRoom = newRoom;
    }

    public Room getPreviousRoom() {
        return previousRoom;
    }

    /**
     * Gives the player a new axe if the player buys a new one at the blacksmith
     *
     * @param newAxe The new Axe that is to be equipped
     */
    public void boughtAxe(Axe newAxe) {
        equippedAxe = newAxe;
        money -= newAxe.getPrice();
    }

    public void grindedAxe(int cost) {
        money -= cost;
    }

    public ArrayList<Tree> getLogsInStorage() {
        return trailer.getLogsInStorage();
    }

    /**
     * when the trees are sold at the store, the storage has to be emptied
     */
    public void loadOffLogsInStorage() {
        trailer.getLogsInStorage().clear();
    }

    public boolean isStorageFull() {
        return trailer.isStorageFull();
    }

    public String putPlayerInTrailer() {
        setCurrentRoom(trailer);
        return currentRoom.roomEntrance(this);
    }

    /**
     * @return boolean whether or not the player has an axe equipped
     */
    public boolean canUseAxe() {
        return equippedAxe != null;
    }

    /**
     * Used to reduce durability on the players currently equipped Axe
     *
     * @return the axe state. If 1 is returned the axe is is between high and half or low and half
     * durability. if 0.5 is returned the axe is at half durability. If 0 is returned the axe has
     * been destroyed
     */
    public double useAxe() {
        equippedAxe.reduceDurability();
        if (equippedAxe.getDurability() == (equippedAxe.getStartDurability() / 2)) {
            return 0.5;
        } else if (equippedAxe.getDurability() == 0) {
            equippedAxe = null;
            return 0;
        }
        return 1;
    }

    /**
     * Used to get access to currently equipped Axe.
     *
     * @return Axe that is equipped
     */
    public Axe getAxe() {
        return equippedAxe;
    }

    public boolean playerHasAnAxe() {
        return equippedAxe != null;
    }

    /**
     * Used to get access to currently equipped BackPack.
     *
     * @return BackPack that is currently equipped
     */
    public BackPack backPack() {
        return equippedBackPack;
    }

    public boolean boughtBackPack(BackPack newBackPack) {
        if (newBackPack.getPrice() <= money) {
            money -= newBackPack.getPrice();
            equippedBackPack = newBackPack;
            return true;
        } else {
            return false;
        }
    }

    public int getAmountOfSaplingsCarrying() {
        return amountOfSaplingsCarrying;
    }

    public boolean buySaplingBundle(int saplingAmount, int saplingCost) {
        if (saplingCost <= money) {
            money -= saplingCost;
            this.amountOfSaplingsCarrying += saplingAmount;
            return true;
        } else {
            return false;
        }
    }

    public int plantSeeds() {
        int startAmountOfChoppedTrees = numChoppedTreesWithoutPlantingSaplings;
        int saplingsPlanted = 0;
        for (int i = 0; i < startAmountOfChoppedTrees; i++) {
            if (amountOfSaplingsCarrying > 0) {
                amountOfSaplingsCarrying--;
                numChoppedTreesWithoutPlantingSaplings--;
                saplingsPlanted++;
            }
        }
        return saplingsPlanted;
    }

    public void addChoppedTreesInCertifiedForest() {
        numChoppedTreesWithoutPlantingSaplings++;
    }

    public int getNumChoppedTreesWithoutPlantingSaplings() {
        return numChoppedTreesWithoutPlantingSaplings;
    }

    /**
     * Resets all the things that the player can interact with during a day. Also checks if the
     * player has choppedTrees without replanting, if this is the case the player will recieve a
     * fine and a quiz to reduce the fine amount.
     *
     * @param fineAmount how much the fine will cost the player, if any
     */
    public void sleep(int fineAmount) {
        money -= fineAmount;
        numChoppedTreesWithoutPlantingSaplings = 0;
        giftHasBeenGivenToday = false;
        hasSlept = true;
    }

    public boolean isGiftHasBeenGivenToday() {
        return giftHasBeenGivenToday;
    }

    public void giftHasBeenGiven() {
        giftHasBeenGivenToday = true;
    }

    public boolean isHasSlept() {
        return hasSlept;
    }

    public void setHasSlept(boolean hasSlept) {
        this.hasSlept = hasSlept;
    }

    public String getDirection() {
        return direction;
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }

    public double getDamage() {
        if (equippedAxe != null) {
            return equippedAxe.getDamage();
        } else {
            return 1;
        }
    }
}
