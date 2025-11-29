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
    public int hashCode() {// we need this function to allow the above function to work correctly other wise any comparison using equal. , will not work properly
        return 19 * x + y; // u can use any prime number  //  and this function  will just help us to look for the point faster , using the hashcode cuz we when we call equal0. it will call the hashcode function automatically and then go for its bucket and then search in this bucket for the thing we want
    }
}
