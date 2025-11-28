import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;

public class Playermanager {
    private static final String FILE_NAME = "players.txt";

    // Load player data from file
    public static PlayerData loadPlayer(int id) {
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_NAME))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length >= 4) {
                    int fileID = Integer.parseInt(parts[0]);
                    if (fileID == id) {
                        String name = parts[1];
                        int highScore = Integer.parseInt(parts[2]);
                        String dateTime = parts[3];
                        return new PlayerData(id, name, highScore, dateTime);
                    }
                }
            }
        } catch (FileNotFoundException e) {
            // File doesn't exist yet - that's okay
            return null;
        } catch (IOException e) {
            System.out.println("Error loading player data: " + e.getMessage());
        }
        return null;  // Player not found
    }

    public static void savePlayer(int id, String name, int score) {
        List<String> allPlayers = new ArrayList<>();
        boolean playerUpdated = false;

        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_NAME))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length >= 4) {
                    int fileID = Integer.parseInt(parts[0]);

                    if (fileID == id) {
                        // Found player - update if new score is better
                        int oldScore = Integer.parseInt(parts[2]);

                        if (score > oldScore) {  // if this is true , he will save overwrite the old data , other wise , he will just ingore them
                            // New high score! Update
                            String dateTime = getCurrentDateTime();
                            allPlayers.add(id + "," + name + "," + score + "," + dateTime);
                            System.out.println("New high score! " + score + " (previous: " + oldScore + ")");
                        } else {
                            // Keep old score (it's better)
                            allPlayers.add(line);
                            System.out.println("Score " + score + " not better than high score " + oldScore);
                        }

                        playerUpdated = true;
                    } else {
                        // Different player - keep as is
                        allPlayers.add(line);
                    }
                }
            }
        } catch (FileNotFoundException e) {
            // File doesn't exist - will create new one
        } catch (IOException e) {
            System.out.println("Error reading players file: " + e.getMessage());
        }

        if (!playerUpdated) {
            String dateTime = getCurrentDateTime();
            allPlayers.add(id + "," + name + "," + score + "," + dateTime);
            System.out.println("New player registered! ID: " + id);
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_NAME))) {
            for (String playerLine : allPlayers) {
                writer.write(playerLine);
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error saving players file: " + e.getMessage());
        }
    }

    public static List<PlayerData> getAllPlayers() {
        List<PlayerData> players = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_NAME))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length >= 4) {
                    int id = Integer.parseInt(parts[0]);
                    String name = parts[1];
                    int score = Integer.parseInt(parts[2]);
                    String dateTime = parts[3];
                    players.add(new PlayerData(id, name, score, dateTime));
                }
            }
        } catch (FileNotFoundException e) {
            return players;
        } catch (IOException e) {
            System.out.println("Error loading players: " + e.getMessage());
        }

        players.sort((p1, p2) -> Integer.compare(p2.score, p1.score));

        return players;
    }

    private static String getCurrentDateTime() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return sdf.format(new Date());
    }
}

class PlayerData {
    int id;
    String name;
    int score;
    String dateTime;

    public PlayerData(int id, String name, int score, String dateTime) {
        this.id = id;
        this.name = name;
        this.score = score;
        this.dateTime = dateTime;
    }

    @Override
    public String toString() {
        return "ID: " + id + " | " + name + " | Score: " + score + " | " + dateTime;
    }
}