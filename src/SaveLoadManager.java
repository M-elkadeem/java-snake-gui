import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class SaveLoadManager {
    private static final String SAVE_FILE = "savegame.dat";


    // Save current game state
    public static boolean saveGame(Board board, Direction heading) {
        int playerID = board.getPerson().getID();

        Map<Integer, GameState> allSaves = loadAllGames();

        try {
            GameState state = new GameState();

            // Save snake body
            for (point segment : board.getSnk().getBody()) {
                state.snakeBody.add(new int[]{segment.getX(), segment.getY()});
            }
            // Save direction
            state.direction = heading.name();


               // saving the food where we will save the position and its type and its point value
            for (Food food : board.getFoods()) {
                point pos = food.getPosition();
                FoodData foodData = new FoodData(
                        pos.getX(),
                        pos.getY(),
                        food.gettype(),
                        food.getPointvalue()
                );
                state.foods.add(foodData);
            }


            state.playerID = board.getPerson().getID();
            state.playerName = board.getPerson().getName();
            state.score = board.getPerson().getScore();

            // Saving board dimensions
            state.boardWidth = board.getBoardWidth();
            state.boardHeight = board.getBoardHeight();
            state.tileSize = board.getTilesize();

            // Save timestamp
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            state.saveDateTime = sdf.format(new Date());

            allSaves.put(playerID, state);
            // Write to file
            try (ObjectOutputStream oos = new ObjectOutputStream(
                    new FileOutputStream(SAVE_FILE))) {
                oos.writeObject(allSaves);
            }

            System.out.println("Game saved successfully!");
            return true;

        } catch (IOException e) {
            System.out.println("Error saving game: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    // Load saved game state
    public static GameState loadGame(int playerID) {

        Map<Integer, GameState> allSaves = loadAllGames();

        if (allSaves.containsKey(playerID)) {
            GameState state = allSaves.get(playerID);
            System.out.println("Game loaded successfully for player " + playerID + "!");
            System.out.println("Save date: " + state.saveDateTime);
            return state;
        }
        System.out.println("No saved game found for player " + playerID + ".");
        return null;
    }
    private static Map<Integer, GameState> loadAllGames() {
        try (ObjectInputStream ois = new ObjectInputStream(
                new FileInputStream(SAVE_FILE))) {

            @SuppressWarnings("unchecked")
            Map<Integer, GameState> allSaves = (Map<Integer, GameState>) ois.readObject();
            return allSaves;

        } catch (FileNotFoundException e) {
            // No save file exists yet - return empty map
            return new HashMap<>();
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Error loading saves: " + e.getMessage());
            return new HashMap<>();
        }
    }
    // Check if save file exists
    public static boolean saveExists(int playerID) {
        Map<Integer, GameState> allSaves = loadAllGames();
        return allSaves.containsKey(playerID);
    }

    // Delete save file
    public static void deleteSave(int playerID) {
        Map<Integer, GameState> allSaves = loadAllGames();

        if (allSaves.remove(playerID) != null) {
            try (ObjectOutputStream oos = new ObjectOutputStream(
                    new FileOutputStream(SAVE_FILE))) {
                oos.writeObject(allSaves);
                System.out.println("Save deleted for player " + playerID + ".");
            } catch (IOException e) {
                System.out.println("Error deleting save: " + e.getMessage());
            }
        }
    }
}