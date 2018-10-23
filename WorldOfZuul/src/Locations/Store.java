package Locations;

import gameFunctionality.Player;
import gameFunctionality.Tree;
import java.util.ArrayList;

public class Store extends Room {
    private Trailer trailer; 

    public Store(String description, Player player, Trailer trailer) {
        super(description, player);
        this.trailer = trailer;
    }

    @Override
    public String getLongDescription() {
        return "You are standing " + getShortDescription() + 
            "Here you can sell your logs and purchase new equipment\n" +
            "Option 1 - Sell logs stored in your trailer\n" +
            "Option 2 - Buy a new axe\n" +
            "Option 3 - Increase storage space\n" +
            getExitString();
    }
    @Override
      public void option1() {
        if (trailer.getStorage().isEmpty()) {
            System.out.println("You have no logs in your storage to sell!");
            return;
        }
        for (Tree tree : trailer.getStorage()) {
            humanPlayer.addMoney(tree.getTreePrice());
        }
        trailer.loadOffStorage();
        System.out.println("You have sold all your logs, you now have " + humanPlayer.getMoney() + " gold");
    }
    
    

//    Skal kunne købe træerne fra spilleren
//    Spilleren skal kunne købe upgrades til: storage, treeCarry, økse
//    
    

    
}