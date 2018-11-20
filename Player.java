public class Player {
    private String name;
    private int goals;
    private int gamesPlayed;
    private int goalsConceded;
    private int wins;

    public Player(String name) {
        this.name = name;

        goals = 0;
        gamesPlayed = 0;
        goalsConceded = 0;
        wins = 0;
    }

    public Player(String name, int gamesPlayed, int wins, int goals, int goalsConceded) {
        StringBuilder vName = new StringBuilder();
        char [] nameArr = name.toCharArray();

        for(char a : nameArr){
            if(a != ','){
                vName.append(a);
            }
        }

        this.name = vName.toString();
        this.goals = goals;
        this.gamesPlayed = gamesPlayed;
        this.goalsConceded = goalsConceded;
        this.wins = wins;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getGoals() {
        return goals;
    }

    public void setGoals(int goals) {
        this.goals = goals;
    }

    public int getGamesPlayed() {
        return gamesPlayed;
    }

    public void setGamesPlayed(int gamesPlayed) {
        this.gamesPlayed = gamesPlayed;
    }

    public int getGoalsConceded() {
        return goalsConceded;
    }

    public void setGoalsConceded(int goalsConceded) {
        this.goalsConceded = goalsConceded;
    }

    public int getWins() {
        return wins;
    }

    public void setWins(int wins) {
        this.wins = wins;
    }

    public void updateStats(int gamesPlayed, int wins, int goals, int goalsConceded){
        this.gamesPlayed = gamesPlayed;
        this.wins = wins;
        this.goals = goals;
        this.goalsConceded = goalsConceded;
    }

    @Override
    public String toString() {
        return name + "," + gamesPlayed + "," + wins + "," + goals + "," + goalsConceded + "\n";
    }
}
