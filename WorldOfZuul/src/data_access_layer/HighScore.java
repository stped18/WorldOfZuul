package data_access_layer;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Scanner;

/**
 *
 * @author oliver
 */
public class HighScore {

    private final File FILE = new File("highScores.txt");

    public void saveHighScoreToFile(String playerName, double playerHighScore) {
        try (PrintWriter writer = new PrintWriter(new FileOutputStream(FILE, true))) {
            writer.print(playerName + "," + playerHighScore + "\n");
        } catch (FileNotFoundException ex) {
            System.out.println("The file you want to open doesn't exist");
        }
    }

    public HashMap<String, Double> readHighScoresFromFile() {
        HashMap<String, Double> highScoresHashMap = new HashMap<>();
        try (Scanner scanner = new Scanner(FILE).useDelimiter(",|\n")) {
            while (scanner.hasNext()) {
                highScoresHashMap.put(scanner.next(), Double.parseDouble(scanner.next()));
            }
        } catch (FileNotFoundException ex) {
            System.out.println("The file you want to open doesn't exist");
        }
        return highScoresHashMap;
    }

    public boolean isPlayerNameTaken(String playerName) {
        return readHighScoresFromFile().containsKey(playerName);
    }
}
