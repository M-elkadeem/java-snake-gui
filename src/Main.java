import javax.swing.*;
import java.awt.*;
import javax.swing.ImageIcon;


public class Main {
    public static void main(String[] args) {
        int boardWidth = 800;
        int boardHeight = 600;

        String idInput = JOptionPane.showInputDialog(null,
                "Enter your Player ID:",
                "Player ID",
                JOptionPane.QUESTION_MESSAGE);

        // the next is a condition if the  player has exited before entering his data
        if (idInput == null || idInput.trim().isEmpty()) {
            System.exit(0);  // Exit if cancelled
        }

        int playerID = Integer.parseInt(idInput);

        String playerName = "";

        PlayerData existingData = Playermanager.loadPlayer(playerID);// for loading the data

        if (existingData != null) {
            // if the Players exist then we will Show their high score
            playerName = existingData.name;
            JOptionPane.showMessageDialog(null,
                    "Welcome back, " + existingData.name + "!\n" +
                            "Your high score: " + existingData.score,
                    "Welcome Back",
                    JOptionPane.INFORMATION_MESSAGE);

        } else {
            // the case of a new player
             playerName = JOptionPane.showInputDialog(null,
                    "Enter your Name:",
                    "Player Name",
                    JOptionPane.QUESTION_MESSAGE);

            if (playerName == null || playerName.trim().isEmpty()) {
            playerName = "Anonymous";  // this will be the default name
       }

            JOptionPane.showMessageDialog(null,
                    "Welcome new player " + playerName + "!",
                    "Welcome",
                    JOptionPane.INFORMATION_MESSAGE);
        }
        JFrame frame = new JFrame("Snake Game") ;
        frame.setSize(boardWidth,boardHeight);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);// to be able to close the Game from the X \
        frame.setLocationRelativeTo(null); // to open the Game at the middle of the screen
        frame.setResizable(true);
       // setting icon image for the Game
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
       // the next is to set the playe name and the id
        board.getPerson().setID(playerID);
        board.getPerson().setName(playerName);

        frame.setLayout(new BorderLayout());
        frame.add(scorePanel, BorderLayout.NORTH);  // Score on top
        frame.add(board, BorderLayout.CENTER);       // Game below
        frame.pack();

        Game game = new Game(board,board.getSnk(),scoreLabel);
      board.setgame
              (game);

        JMenuBar menuBar = Menu.CreatingMenu(frame, board, game);
        frame.setJMenuBar(menuBar);
        frame.setVisible(true );// setting the board to be visible

        game.start();

    }
}

