    import javax.swing.*;
    import java.awt.*;
    import java.util.ArrayList;

    public class Board extends JPanel {
    private final int boardWidth;
     private final int boardHeight;
     private final int tilesize = 20;
     private final ArrayList<Food> foods;
     private Game game;
     snake snk;
        private player person;

        public void setSnk(snake snk) {
            this.snk = snk;
        }

        public void setGame(Game game) {
            this.game = game;
        }

        public Board(int boardWidth, int boardHeight) {
            this.boardWidth = boardWidth;
            this.boardHeight = boardHeight;

            this.snk = new snake (boardWidth,boardHeight,tilesize) ;
            this.person = new player(0 , false );
            foods = new ArrayList<>();
            Creatingfood();


            setPreferredSize(new Dimension(boardWidth, boardHeight+28));// here i have added , 28 more , cuz the score panel , will cause one the tilelines to be removed and by that there will not be any need for shrinking
           setBackground(Color.BLACK);

        }

        @Override
        protected void paintComponent(Graphics g) {
            // paintComponent is triggered by making the JFrame visible, and all calls after that are managed by the repaint() method in the Playingthegame inside the Game class
            super.paintComponent(g);
            drawingBoard(g);  // Now you can pass g to your method!

            drawingSnake(g);  // drawing the snake body

            drawingfoods(g); // drawing the fruits

            if ( game.isGameOver()) {
                drawGameOver(g);
            }

        }

        public void drawingBoard (Graphics g ){
            // next is drawing the grid lines for the Game >> it clarify the game work
            for (int i =0 ; i<boardWidth/tilesize;i++){
                g.drawLine(i * tilesize, 0, i * tilesize, boardHeight);// drawing the vertical lines
                g.drawLine(0, i * tilesize, boardWidth, i * tilesize);// drawing the horizontal lines
            }
        }
        public void drawingSnake(Graphics g ){
            g.setColor(Color.BLUE);
            for(point segments :snk.getBody()){
                g.fillRect(segments.getX() * tilesize,
                        segments.getY() * tilesize,
                        tilesize,
                        tilesize);
            }
            // the folllowing to give the head a new color than the green one
            point head = snk.gethead();
            g.setColor(Color.YELLOW);  // right now , the head will be yellow and the rest of the body will be green
            g.fillRect(
                    head.getX() * tilesize,
                    head.getY() * tilesize,
                    tilesize,
                    tilesize
            );
        }

        public void Creatingfood(){
          // here we will generate the normal food and it will affect by one point on the score and growing one segment for the snake
            int num_normalfood = 0 ;
            for (Food normalfood : foods){
                if (normalfood.gettype().equals("Normal")){
                    num_normalfood++ ;
                }
            }
            if (num_normalfood==0) {
                Food normalfood = new Normalfood(Color.GREEN, 1);
                normalfood.generatingfood(boardWidth, boardHeight, tilesize, this);// this here will be the board object who called the creatingfood function or to be exact the object who called the eating food function
                foods.add(normalfood);
            }
           if (person.getScore()>0&& person.getScore() %5==0){
               Food goldenfood = new Goldenfood(Color.ORANGE,5);
               goldenfood.generatingfood(boardWidth,boardHeight,tilesize,this);
               foods.add(goldenfood);
           }

            if (person.getScore()>0&& person.getScore() %10 ==0){
                Food poisonfood = new Poisonfood(Color.MAGENTA, -1 * (person.getScore()/2));
                poisonfood.generatingfood(boardWidth,boardHeight,tilesize,this);
                foods.add(poisonfood);
            }
        }
        public void eaatingfood(Food food){
            // the next is one complete process of eating any fruit
            food.applyEffect(snk);
            person.update_score(food.getPointvalue());
            foods.remove(food);


                Creatingfood();// creating the new food

        }
        public boolean isFoodPositionInvalid(point newfruit_position){
            boolean Colliding = false;
                for(point segment : snk.getBody()){
                    if (newfruit_position.equals(segment)){
                        Colliding = true ;
                        break;
                    }
                }
            for(Food existingFood : foods){
                if(newfruit_position.equals(existingFood.getPosition())){
                    Colliding = true ;
                    break;
                }
            }
            return Colliding;
        }

        public void drawingfoods(Graphics g ){
            for (Food segment : foods ){
                point pos = segment.getPosition();
                 g.setColor(segment.getColor());
                 g.fillOval(pos.getX()*tilesize,pos.getY()*tilesize,tilesize,tilesize);
            }
        }
        public void setgame(Game game) { // using the setter method for the Game object , to extact all the data form the Game object of the game itself
            this.game = game;
        }
        private void drawGameOver(Graphics g) {
            // Dark overlay (semi-transparent black)
            g.setColor(new Color(0, 0, 0, 200));
            g.fillRect(0, 0, boardWidth, boardHeight);

            // drawing game over
            g.setFont(new Font("Courier New", Font.BOLD, 80));// this will be the size for tha word
            String gameOverText = "GAME OVER";

            // Calculate center position to let us draw it in the middle
            int textWidth = g.getFontMetrics().stringWidth(gameOverText);
            int x = (boardWidth - textWidth) / 2;
            int y = boardHeight / 2 - 50;

            // Draw shadow/outline
            g.setColor(new Color(0, 100, 0));  // Dark green
            g.drawString(gameOverText, x + 3, y + 3);

            // Draw main text
            g.setColor(new Color(0, 255, 0));  // Bright green
            g.drawString(gameOverText, x, y);

            // Final Score
            g.setFont(new Font("Courier New", Font.BOLD, 40));
            String scoreText = "Final Score: " + person.getScore();
            int scoreWidth = g.getFontMetrics().stringWidth(scoreText);
            int scoreX = (boardWidth - scoreWidth) / 2;

            g.setColor(Color.WHITE);
            g.drawString(scoreText, scoreX, y + 100);

            // Snake Length
            String lengthText = "Length: " + snk.getlength();
            int lengthWidth = g.getFontMetrics().stringWidth(lengthText);
            int lengthX = (boardWidth - lengthWidth) / 2;

            g.drawString(lengthText, lengthX, y + 150);

            // Restart instruction
            g.setFont(new Font("Courier New", Font.PLAIN, 25));
            String restartText = "Press ENTER to Restart";
            int restartWidth = g.getFontMetrics().stringWidth(restartText);
            int restartX = (boardWidth - restartWidth) / 2;

            g.setColor(new Color(180, 180, 180));  // Light gray
            g.drawString(restartText, restartX, y + 220);
        }

        public snake getSnk() {
            return snk;
        }

        public player getPerson() {
            return person;
        }

        public int getBoardWidth() {
            return boardWidth;
        }

        public ArrayList<Food> getFoods() {
            return foods;
        }

        public int getBoardHeight() {
            return boardHeight;
        }

        public int getTilesize() {
            return tilesize;
        }

        public void setPerson(player person) {
            this.person = person;
        }
    }
