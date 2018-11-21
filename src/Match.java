import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Match {
    private PlayerList team1;
    private PlayerList team2;
    private int team1Score;
    private int team2Score;
    enum Winner{TEAM1,TEAM2,DRAW}
    private Winner winner;

    public Match(PlayerList team1, PlayerList team2, int team1Score, int team2Score) {
        this.team1 = team1;
        this.team2 = team2;
        this.team1Score = team1Score;
        this.team2Score = team2Score;
        if (team1Score > team2Score)
            winner = Winner.TEAM1;
        else if (team1Score < team2Score)
            winner = Winner.TEAM2;
        else
            winner = Winner.DRAW;

    }

    public PlayerList getTeam1() {
        return team1;
    }

    public PlayerList getTeam2() {
        return team2;
    }

    public int getTeam1Score() {
        return team1Score;
    }

    public int getTeam2Score() {
        return team2Score;
    }

    public Winner getWinner() {
        return winner;
    }

    public void saveToFile() throws IOException {
        FileWriter writer = new FileWriter("Matches.csv",true);
        writer.write(toString());
        writer.close();
    }

    @Override
    public String toString() {

        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

        return "\n" + dateFormat.format(new Date()) + ":\n" +
                "Team 1:" + team1 +
                "\nTeam 2:" + team2 +
                "\nScore: " + team1Score +
                " - " + team2Score +
                '\n';
    }
}
