import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class Game {
    private boolean GameRunning;
    private boolean GameOver;
    private Timer timer;
    private Direction heading;
    private Direction prevheading;
    private final snake snk;
    private final Board board;
    private final JLabel scoreLabel;

    public Game(Board board,snake snk,JLabel scoreLabel) {
        this.snk = snk;
        this.board = board;
        this.heading = Direction.NONE;// this will be the initial position  of the snake
        this.prevheading = Direction.NONE;
        this.scoreLabel = scoreLabel;
        GameRunning = true;
        GameOver = false;

        setupTimer();
        checkInput();
    }

    private void setupTimer() {// setting up the timer , where the game will be drawing each its period
        // java will find out the type by itself
        var movedelay = 150;
        timer = new Timer(movedelay, e -> Playingthegame());

    }
    public void Playingthegame(){

        if(GameOver){
            return;
        }
        if (heading != Direction.NONE) {// the function of moving the snake
            snk.move(heading);
        }
        wallcollision();
        selfcollision ();
        foodcollision ();

        updateScoreDisplay();
        board.repaint();

    }
    private void updateScoreDisplay() {
        scoreLabel.setText("Score: " + board.getPerson().getScore() +
                "    Length: " + snk.getlength());
    }
 public void wallcollision(){
        point head = snk.gethead();
     if (head.getX() < 0 || head.getX()>=board.getBoardWidth() / board.getTilesize()
             ||head.getY()<0||head.getY()>=board.getBoardHeight()/ board.getTilesize())
     {
         GameOver = true ;
         timer.stop();

         savePlayerData();// saving the player data

       SaveLoadManager.deleteSave(board.getPerson().getID());// here cuz the player has lost so we need to delete his game from the file

         board.repaint();
     }
 }
 public void selfcollision (){
        point head = snk.gethead();
        for(int i =1;i < snk.getlength();i++){
            if (head.equals(snk.getBody().get(i))){
                GameOver = true;
                timer.stop();
                savePlayerData();// saving the player data
               SaveLoadManager.deleteSave(board.getPerson().getID());// here cuz the player has lost so we need to delete his game from the file
                board.repaint();
                return; // to not check the other segments , ( only check the one u touched )

            }
        }
 }

    public  void savePlayerData() {
        player p = board.getPerson();
        if (p.getName().equals("Anonymous")){
            return;
        }
        Playermanager.savePlayer(p.getID(), p.getName(), p.getScore());
    }
 public void foodcollision (){
        point head = snk.gethead();
        for (Food food : board.getFoods()){
            if (head.getX()==food.getPosition().getX()&&head.getY()==food.getPosition().getY()){
                board.eaatingfood(food);
                break ; // to allow him only to eat one fruit for each frame
            }
        }
 }

    public void start(){
        GameRunning = true;
        GameOver = false;
        board.getPerson().setIsplaying(true);
        timer.start();
    }

    public void pausingthegame (){
        GameRunning = false;
        timer.stop();
    }
    public void togglingpause(){// once he clicks on the pausing button , he will be directed to this method and then the following operation will happen
     if(GameRunning){
         prevheading = heading;
         GameRunning = false;
         timer.stop();
     }else {
         heading = prevheading;// the reason I created that here is when u stop the game and if u clicked on any other key , it will set the heading to the direction of that key , even though the game is paused
         // so we had to store the heading before we stop the game and then update it once the game starts again
         start();
     }
    }
    private void checkInput() {
        board.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                processing_the_keys(e);
            }
        });
        board.setFocusable(true);

        board.requestFocusInWindow();
    }
    public void restartingtheGame(){
        snk.getBody().clear();

        // the following is for recreating the snake again
        int midX = (board.getBoardWidth() / board.getTilesize()) / 2;
        int midY = (board.getBoardHeight() / board.getTilesize()) / 2;
        snk.getBody().add(new point(midX, midY));

        board.getPerson().setScore(0);
        board.getFoods().clear();
        heading = Direction.NONE;

        board.Creatingfood();
        start();
        board.repaint();
    }

    public  void savingdata (){
        if (board.getPerson().getName().equals("Anonymous")){
            return;
        }
        boolean success = SaveLoadManager.saveGame(board, heading);
        if (success) {
            JOptionPane.showMessageDialog(
                    null,
                    "Game saved successfully!",
                    "Save Game",
                    JOptionPane.INFORMATION_MESSAGE
            );
        } else {
            JOptionPane.showMessageDialog(
                    null,
                    "Failed to save game!",
                    "Save Game",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }
    public void loadingGame(int playerID) {
        GameState state = SaveLoadManager.loadGame(playerID);

        if (state == null) {
            JOptionPane.showMessageDialog(null,
                    "Failed to load saved game!",
                    "Load Error",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Clear current snake body
        snk.getBody().clear();

        // Restore snake body from saved state
        for (int[] segment : state.snakeBody) {
            snk.getBody().add(new point(segment[0], segment[1]));
        }

        // Restore direction
        try {
            heading = Direction.valueOf(state.direction);
            prevheading = heading;
        } catch (IllegalArgumentException e) {
            heading = Direction.NONE;
            prevheading = Direction.NONE;
        }

        // Restore player data
        board.getPerson().setScore(state.score);

        // Restore foods
        board.getFoods().clear();
        for (FoodData foodData : state.foods) {
            Food food = switch (foodData.type) {
                case "Normal" -> new Normalfood(Color.GREEN, foodData.pointValue);
                case "Golden" -> new Goldenfood(Color.ORANGE, foodData.pointValue);
                case "Poison" -> new Poisonfood(Color.MAGENTA, foodData.pointValue);
                default -> null;
            };

            if (food != null) {
                food.setPosition(new point(foodData.x, foodData.y));
                board.getFoods().add(food);
            }
        }

        updateScoreDisplay();
        board.repaint();

        System.out.println("Game loaded successfully!");
    }
    private void processing_the_keys(KeyEvent e)
    {

        switch (e.getKeyCode()) {

            case KeyEvent.VK_W:// up
                if (heading == Direction.LEFT || heading == Direction.RIGHT || heading == Direction.NONE ) {  // Can't go opposite direction
                    heading = Direction.UP;
                }
                break;

            case KeyEvent.VK_S:
                if (heading == Direction.LEFT || heading == Direction.RIGHT || heading == Direction.NONE ) {  // Can't go opposite direction
                    heading = Direction.DOWN;
                }
                break;

            case KeyEvent.VK_A:
                if (heading == Direction.DOWN || heading == Direction.UP || heading == Direction.NONE ) {  // Can't go opposite direction
                    heading = Direction.LEFT;
                }
                break;

            case KeyEvent.VK_D:
                if (heading == Direction.DOWN || heading == Direction.UP || heading == Direction.NONE ) {  // Can't go opposite direction
                    heading = Direction.RIGHT;
                }
                break;

            case KeyEvent.VK_SPACE:
                togglingpause();
                break;
            case KeyEvent.VK_ENTER:
                if(GameOver){
                    restartingtheGame();
                }
                break;

        }
    }



    public void setGameRunning(boolean gameRunning) {
        GameRunning = gameRunning;
    }

    public void setGameOver(boolean gameOver) {
        GameOver = gameOver;
    }

    public boolean isGameOver() {
        return GameOver;
    }



}
