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
    }

    public void setScore(int score) {
        this.score = score;
    }
}
