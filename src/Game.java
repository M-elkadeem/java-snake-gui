import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class Game {
    private boolean GameRunning;
    private boolean GameOver;
    private Timer timer;
    private Direction heading;
    private snake snk;
    private Board board;
    private JLabel scoreLabel;
    private int movedelay = 150;

    public Game (){}
    public Game(Board board,snake snk,JLabel scoreLabel) {
        this.snk = snk;
        this.board = board;
        this.heading = Direction.NONE;// this will be the initial position position of the snake
        this.scoreLabel = scoreLabel;
        GameRunning = true;
        GameOver = false;

        setupTimer();
        checkInput();
    }

    private void setupTimer() {// setting up the timer , where the game will be drawing each its period
        timer = new Timer(movedelay, e -> Playingthegame());

    }
    public void  Playingthegame(){

        if(GameOver){
            return;
        }

        if (heading != Direction.NONE) {// the function of moving the snake
            snk.move(heading,board);
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
         board.repaint();
     }
 }
 public void selfcollision (){
        point head = snk.gethead();
        for(int i =1;i < snk.getlength();i++){
            if (head.equals(snk.getBody().get(i))){
                GameOver = true;
                timer.stop();
                board.repaint();

            }
        }
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
        timer.start();
    }

    public void togglingpause(){// once he clicks on the pausing button , he will be directed to this method and then the following operation will happen
     if(GameRunning){
         GameRunning = false;
         timer.stop();
     }else {
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
    private void processing_the_keys(KeyEvent e)
    {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_W:
                if (heading != Direction.DOWN) {  // Can't go opposite direction
                    heading = Direction.UP;
                }
                break;

            case KeyEvent.VK_S:
                if (heading != Direction.UP) {
                    heading = Direction.DOWN;
                }
                break;

            case KeyEvent.VK_A:
                if (heading != Direction.RIGHT) {
                    heading = Direction.LEFT;
                }
                break;

            case KeyEvent.VK_D:
                if (heading != Direction.LEFT) {
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

    public boolean isGameRunning() {
        return GameRunning;
    }

    public boolean isGameOver() {
        return GameOver;
    }
}
