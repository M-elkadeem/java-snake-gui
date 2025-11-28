import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class GameState implements Serializable {
    private static final long serialVersionUID = 1L;

    // Snake data
    public List<int[]> snakeBody;
    public String direction;

    // Food data
    public List<FoodData> foods;

    // Player data
    public int playerID;
    public String playerName;
    public int score;

    // Board dimensions
    public int boardWidth;
    public int boardHeight;
    public int tileSize;

    // Save date/time
    public String saveDateTime;

    public GameState() {
        snakeBody = new ArrayList<>();
        foods = new ArrayList<>();
    }
}

// Helper class for food data
class FoodData implements Serializable {
    private static final long serialVersionUID = 1L;

    public int x;
    public int y;
    public String type;  // the type of the foods
    public int pointValue;

    public FoodData(int x, int y, String type, int pointValue) {
        this.x = x;
        this.y = y;
        this.type = type;
        this.pointValue = pointValue;
    }
}