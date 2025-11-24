import java.awt.*;

public class Normalfood extends Food{
    public Normalfood(Color color, int pointvalue) {
        super(color, pointvalue);
    }

    @Override
    public void applyEffect(snake snk) {
        snk.grow(this);
    }

    @Override
    public String gettype() {
        return "Normal";
    }
}

