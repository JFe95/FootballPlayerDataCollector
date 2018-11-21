import java.io.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class PlayerList {

    private ArrayList <Player> playerList;
    private String filename;

    public PlayerList() {
        playerList = new ArrayList<>();
    }

    public PlayerList(String filename){
        playerList = new ArrayList<>();
        this.filename = filename;
        new PlayerList();
        try {
            loadFromFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public ArrayList<Player> getPlayerList() {
        return playerList;
    }

    public boolean addPlayer(Player p){
        for (Player q : playerList){
            if (p.getName().equals(q.getName())){
                return false;
            }
        }
        return playerList.add(p);
    }

    public boolean removePlayer(Player p){
        return playerList.remove(p);
    }

    public boolean removePlayer(String pName) {
        Player toRemove = new Player("");
        boolean removed = false;
        for (Player p : playerList) {
            if (p.getName().equals(pName)) {
                toRemove = p;
                removed = true;
            }
        }
        return removed && removePlayer(toRemove);

    }

    public Player getPlayer(int i){
        return playerList.get(i);
    }

    public Player getPlayer(String pName) throws Exception {
        for(Player p : playerList){
            if(p.getName().equals(pName)){
                return p;
            }
        }
        throw new Exception("Player "+ pName +  " not found");
    }

    public String getPlayerName(int i){
        return playerList.get(i).getName();
    }

    public int size(){return playerList.size();}

    public void sort(){
        playerList.sort(BY_NAME);
    }

    private static Comparator<Player> BY_NAME = (player, t1) -> {
        int result = String.CASE_INSENSITIVE_ORDER.compare(player.getName(),t1.getName());
        if (result == 0){
            result = player.getName().compareTo(t1.getName());
        }
        return result;
    };

    private void loadFromFile() throws IOException {
        File f = new File(filename);
        f.createNewFile();
        BufferedReader reader = new BufferedReader(new FileReader(f));
        String line;
        String delimeter = ",";

        while ((line = reader.readLine()) != null) {
            String[] playerData = line.split(delimeter);

            String name = playerData[0];
            int goals = Integer.parseInt(playerData[1]);
            int games = Integer.parseInt(playerData[2]);
            int goalsConceded = Integer.parseInt(playerData[3]);
            int wins = Integer.parseInt(playerData[4]);

            addPlayer(new Player(name, goals, games, goalsConceded, wins));
        }
        reader.close();
    }

    public void saveToFile() throws IOException {
        FileWriter writer = new FileWriter(filename);
        for (Player p : playerList){
            writer.write(p.toString());
        }
        writer.close();
    }

    @Override
    public String toString() {
        StringBuilder str = new StringBuilder();
        for (Player p : playerList){
            str.append(p.getName()).append(",");
        }
        return str.toString();
    }

    public void addPlayers(List<String> pList) {
    }

    public void updatePlayerStats(Match m) {
        ArrayList <Player> newPlayerList = new ArrayList<>();

        for (Player p : playerList) {
            if (m.getTeam1().contains(p.getName())){
                p.setGamesPlayed(p.getGamesPlayed()+1);
                if(m.getWinner() == Match.Winner.TEAM1)
                    p.setWins(p.getWins() + 1);
                p.setGoals(p.getGoals() + m.getTeam1Score());
                p.setGoalsConceded(p.getGoalsConceded() + m.getTeam2Score());
            }
            if (m.getTeam2().contains(p.getName())){
                p.setGamesPlayed(p.getGamesPlayed() + 1);
                if(m.getWinner() == Match.Winner.TEAM2)
                    p.setWins(p.getWins() + 1);
                p.setGoals(p.getGoals() + m.getTeam2Score());
                p.setGoalsConceded(p.getGoalsConceded() + m.getTeam1Score());
            }
            newPlayerList.add(p);
        }

        playerList = newPlayerList;
    }

    private boolean contains(String name) {
        for (Player p : playerList) {
            if (p.getName().equals(name))
                return true;
        }
        return false;
    }
}
