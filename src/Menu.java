import javax.swing.*;

public class Menu {

    public static JMenuBar CreatingMenu(JFrame frame, Board board, Game game) {
        JMenuBar menuBar = new JMenuBar();

        JMenu fileMenu = new JMenu("File");
        // First > creating New Game
        JMenuItem NewGame = new JMenuItem("New Game");
        NewGame.addActionListener(e -> {
            int choice = JOptionPane.showConfirmDialog(
                    frame,
                    "Start a new game? Current progress will be lost.",
                    "New Game",
                    JOptionPane.YES_NO_OPTION
            );

            if (choice == JOptionPane.YES_OPTION) {
                game.restartingtheGame();
            }
        });
        JMenuItem Continue  = new JMenuItem("Continue");
        // implementing later >>> there will be two cases of what will be shown
        // first is continue the game if he paused , in case he is playing
        // second , continue , if if he is not playing yet >> this will load his game if he and existed player with saving data

         JMenuItem Exit = new JMenuItem ("Exit");
         // will be implemted later
        // there will be two options for saving and not savin  g

        fileMenu.add(NewGame);
        fileMenu.add(Continue);
        fileMenu.add(Exit);

        menuBar.add(fileMenu);
        return menuBar;
    }
}