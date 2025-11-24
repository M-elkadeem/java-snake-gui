import java.awt.*;

public class Poisonfood extends Food{

    public Poisonfood(Color color, int pointvalue) {
        super(color, pointvalue);
    }

    @Override
    public void applyEffect(snake snk) {
        snk.shrinking(this);
    }


    @Override
    public String gettype() {
        return "Poison";
    }
}

