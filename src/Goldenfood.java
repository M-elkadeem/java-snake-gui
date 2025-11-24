import java.awt.*;

public class Goldenfood extends Food {
    public Goldenfood(Color color, int pointvalue) {
        super(color, pointvalue);
    }

    @Override
    public void applyEffect(snake snk) {
        snk.grow(this);
    }
    @Override
    public String gettype() {
        return "Golden";
    }
}

