package bababooey;

public class Team implements Comparable<Team>{

    private final String NAME;
    private int score;
    private int goals;
    private final char GroupID;
    private int goalDiff;
    private int wins;

    public Team(String Name, char groupID){
        this.NAME = Name;
        this.GroupID = groupID;
        this.score = 0;
        this.goals = 0;
        this.goalDiff = 0;
        this.wins = 0;
    }

    @Override
    public int compareTo(Team other){
        if(this.score > other.score) return -1;
        if(this.score < other.score) return 1;
        if(this.goalDiff > other.goalDiff) return -1;
        if(this.goalDiff < other.goalDiff) return 1;
        if(this.goals > other.goals) return -1;
        if(this.goals < other.goals) return 1;
        if(this.wins > other.wins) return -1;
        if(this.wins < other.wins) return 1;
        return 0;
    }

    public void setGoalDiff(int goalDiff) {
        this.goalDiff += goalDiff;
    }

    public void setGoals(int goals) {
        this.goals += goals;
    }

    public void setScore(int score) {
        this.score += score;
    }

    public void setWins() {
        this.wins++;
    }

    public char getGroupID() {
        return GroupID;
    }

    public int getGoalDiff() {
        return goalDiff;
    }

    public int getGoals() {
        return goals;
    }

    public int getScore() {
        return score;
    }

    public int getWins() {
        return wins;
    }

    public String getNAME() {
        return NAME;
    }

    public void reset(){
        this.score = 0;
        this.goals = 0;
        this.goalDiff = 0;
        this.wins = 0;
    }

    @Override
    public String toString(){
        return getNAME() + " Pts: " + getScore() + " Goals " + getGoals() + " goalDiff: " + getGoalDiff() + " Dubs: " + getWins();
    }
}
