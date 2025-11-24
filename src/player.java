public class player {
    private int score;

    public player(int score) {
        this.score = score;
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
