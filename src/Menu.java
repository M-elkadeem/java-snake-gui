import javax.swing.*;

public class Menu {

    public static JMenuBar CreatingMenu(JFrame frame, Game game) {
        JMenuBar menuBar = new JMenuBar();

        JMenu fileMenu = new JMenu("File");
        // First > creating New Game
        JMenuItem NewGame = new JMenuItem("New Game");
        NewGame.addActionListener(e -> {
            game.pausingthegame(); // once he chooses that option , the game will stop automatically
            int choice = JOptionPane.showConfirmDialog(
                    frame,
                    "Start a new game? Current progress will be lost.",  // this is the message we will get once we click on new game option
                    "New Game",// this will be the header's name of the message we will get
                    JOptionPane.YES_NO_OPTION  // and in this message , there will be two options to choose
            );

            if (choice == JOptionPane.YES_OPTION) {
                game.restartingtheGame();
            }else {
                game.start(); // in case he choosed NO option , the game will start again automatically
            }
        });

         JMenuItem Exit = new JMenuItem ("Exit");
         Exit.addActionListener(e -> {
                     game.pausingthegame(); // once he choosed this option , the game will stop automatically
                     int choice = JOptionPane.showConfirmDialog(
                             frame,
                             "Are you sure , that you want to Exit", // this is the message we will get once we click on the exit option
                             "Exit",// this will be the header's name of the message we will get
                             JOptionPane.YES_NO_OPTION  // and in this message , there will be two options to choose
                     );


                     if (choice == JOptionPane.YES_OPTION) {
                         if ( game.isGameOver()){
                             System.exit(0);
                         }
                         int option = JOptionPane.showConfirmDialog(
                                 frame,
                                 "Do you want to save your Data", // this is the message we will get once we click on the exit option
                                 "Saving",// this will be the header's name of the message we will get
                                 JOptionPane.YES_NO_OPTION  // and in this message , there will be two options to choose
                         );
                         if (option == JOptionPane.YES_OPTION) {
                             // calling the save function in the next line , he will exit at once
                             game.savePlayerData();
                              game.savingdata();

                             System.exit(0);
                         }else {
                             // here he will just exit without saving any game

                             System.exit(0);
                         }
                     } else {
                         // here we will just continue the game , again , and that is all , cuz he already choosed NO
                         game.start();
                     }
                 });
        fileMenu.add(NewGame);
        fileMenu.add(Exit);

        menuBar.add(fileMenu);
        return menuBar;
    }
}