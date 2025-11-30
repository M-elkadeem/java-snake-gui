import java.util.Random;
import java.awt.*;
public abstract  class Food {
    private point position;
    private final Random random = new Random();
    private final Color color ;
    private final int pointvalue;

    public Food(Color color, int pointvalue) {
        this.color = color;
        this.pointvalue = pointvalue;
    }

    public void generatingfood (int width , int height , int tilesize , Board board ){
        do {
            int x = random.nextInt(width / tilesize);
            int y = random.nextInt(height / tilesize);
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


