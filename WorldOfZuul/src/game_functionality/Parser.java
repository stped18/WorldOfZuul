package game_functionality;

import java.util.Scanner;

public class Parser {

    private final CommandWords commands;
    private final Scanner reader;
    private final Player humanPlayer;

    public Parser(Player humanPlayer) {
        commands = new CommandWords();
        reader = new Scanner(System.in);
        this.humanPlayer = humanPlayer;
    }

    public Command getCommand(String userInput) {
        String inputLine;
        String word1 = null;
        String word2 = null;
        
        inputLine = userInput;

        Scanner tokenizer = new Scanner(inputLine);
        if (tokenizer.hasNext()) {
            word1 = tokenizer.next().toLowerCase();
            if (tokenizer.hasNext()) {
                word2 = tokenizer.next().toLowerCase();
            }
        }
        /*
         * If the user has typed something that corresponds to one of the options in the room they
         * currently are in, that option command will be returned.
         */
        inputLine = inputLine.toLowerCase().replaceAll("\\s", "");
        if (humanPlayer.getCurrentRoom().getOptions(inputLine) != null) {
            return new Command(CommandWord.OPTION, humanPlayer.getCurrentRoom().getOptions(inputLine));
        }
        return new Command(commands.getCommandWord(word1), word2);
    }

    public void showCommands() {
        commands.showAll();
    }

}
