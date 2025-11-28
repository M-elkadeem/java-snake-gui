import java.util.Random;
import java.awt.*;
public abstract  class Food {
    private point position;
    private Random random = new Random();
    private Color color ;
    private int pointvalue;
    private String type;

    public Food(Color color, int pointvalue) {
        this.color = color;
        this.pointvalue = pointvalue;
    }

    public void generatingfood (int width , int height , int tailsize , Board board ){
        do {
            int x = random.nextInt(width / tailsize);
            int y = random.nextInt(height / tailsize);
// this will generate a random place for the segments  positions
            position = new point(x, y);
        }while(board.isFoodPositionInvalid(position));
    }
    public abstract void applyEffect(snake snk);
    public abstract String gettype ();

    public point getPosition() {
        return position;
    }
    public void setPosition(point position) {
        this.position = position;
    }
    public Color getColor() {
        return color;
    }

    public int getPointvalue() {
        return pointvalue;
    }


}


