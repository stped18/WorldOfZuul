package game_elements;

import java.util.ArrayList;

public class BackPack extends Item {

    private final ArrayList<Tree> logsInBackPack;
    private final int backpackCapacity;

    public BackPack(String name, int price, int backpackCapacity) {
        super(name, price);
        this.backpackCapacity = backpackCapacity;
        this.logsInBackPack = new ArrayList();
    }

    /**
     * @return max capacity of the backpack
     */
    public int getBackpackCapacity() {
        return backpackCapacity;
    }

    /**
     * @return ArrayList with Trees currently in the backpack
     */
    public ArrayList<Tree> getLogsInBackPack() {
        return logsInBackPack;
    }

    /**
     * @return Amount of trees in the backpack currently
     */
    public int getAmountOfLogsInBackPack() {
        return this.logsInBackPack.size();
    }

    /**
     * @param tree that is to be added to the backpack
     */
    public void addTreeToBackpack(Tree tree) {
        if (tree instanceof CertifiedTree) {
            this.logsInBackPack.add(new CertifiedTree(12));
        } else {
            this.logsInBackPack.add(new NonCertifiedTree(12));
        }
    }

    /**
     * Whenever the backpack's contents is sold.
     */
    public void emptyBackpack() {
        logsInBackPack.clear();
    }

    /**
     * Used to remove logs from backpack one by one
     */
    public void removeLogFromBackpack() {
        this.logsInBackPack.remove(0);
    }

    @Override
    public String toString() {
        return getDescription() + " with a capacity of " + getBackpackCapacity()
            + " | " + getPrice() + " gold coins";
    }

}
