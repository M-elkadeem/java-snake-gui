public class player {
    private int score;
    private String name ;
    private int ID;

    public player(int score) {
        this.score = score;
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
