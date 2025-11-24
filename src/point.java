public class point {
    private int x ;
    private int y ;

    public point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
    @Override
    public boolean equals(Object obj) {// this  method will handle comparing two points object , cuz we have to compare the positions of them
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        point p = (point) obj;
        return x == p.x && y == p.y;
    }

    @Override
    public int hashCode() {
        return 31 * x + y;
    }
}
