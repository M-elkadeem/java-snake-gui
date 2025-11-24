import javax.swing.*;
import java.awt.*;
import javax.swing.ImageIcon;


public class Main {
    public static void main(String[] args) {
        int boardWidth = 800;
        int boardHeight = 600;

        JFrame frame = new JFrame("Snake Game") ;
        frame.setSize(boardWidth,boardHeight);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);// to be able to close the Game from the X \
        frame.setLocationRelativeTo(null); // to open the Game at the middle of the screen
        frame.setResizable(false);
       // setting icon for the Game
        ImageIcon image = new ImageIcon("Snake image.png") ;
        frame.setIconImage(image.getImage());// this is just for changing the Icon image of the game
        frame.getContentPane().setBackground(Color.BLACK);

        JPanel scorePanel = new JPanel();
        scorePanel.setBackground(Color.DARK_GRAY);
        scorePanel.setPreferredSize(new Dimension(boardWidth, 60));  // 60px tall

        JLabel scoreLabel = new JLabel("Score: 0    Length: 1");
        scoreLabel.setForeground(Color.WHITE);
        scoreLabel.setFont(new Font("Arial", Font.BOLD, 20));
        scorePanel.add(scoreLabel);

        Board board = new Board(boardWidth, boardHeight);// once u call this , it will call all the function which are inside the constructor

        frame.setLayout(new BorderLayout());
        frame.add(scorePanel, BorderLayout.NORTH);  // Score on top
        frame.add(board, BorderLayout.CENTER);       // Game below

        frame.pack();
        frame.setVisible(true );// setting the board to be visible

        Game game = new Game(board,board.getSnk(),scoreLabel);
      board.setgame(game);
      game.start();

    }
}

