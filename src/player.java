public class player {
    private int score;
    private String name ;
    private int ID;
    private boolean isplaying;

    public player(int score ,boolean isplaying) {
        this.score = score;
        this.isplaying = isplaying;
    }

    public void setIsplaying(boolean isplaying) {
        this.isplaying = isplaying;
    }

    public boolean isIsplaying() {
        return isplaying;
    }

    public String getName() {
        return name;
    }

    public int getID() {
        return ID;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public int getScore() {
        return score;
    }
    public void update_score(int score){

        this.score+=score ;
        if (this.score < 0){  // this will prevent from becoming negative in case of eating poision food
            this.score = 0 ;
        }
    }

    public void setScore(int score) {
        this.score = score;
    }
}
