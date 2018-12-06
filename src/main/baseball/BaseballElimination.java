import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.FlowEdge;
import edu.princeton.cs.algs4.FlowNetwork;
import edu.princeton.cs.algs4.FordFulkerson;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class BaseballElimination {
    private final int n;
    private final int s;
    private final int t;
    private final HashMap<String, Integer> teams;
    private final int[][] games;
    private final int[][] schedule;

    public BaseballElimination(String filename) {
        System.out.println(filename);
        In in = new In(filename);
        int n = Integer.parseInt(in.readLine());

        this.n = n;
        this.s = n-1;
        this.t = n;
        teams = new HashMap<>();
        games = new int[n][3];
        schedule = new int[n][n];

        int i = 0;
        while (!in.isEmpty()) {
            String line = in.readLine();
            String[] row = line.split("(\\s+)");
            teams.put(row[0], i);
            games[i] = new int[] {Integer.parseInt(row[1]), Integer.parseInt(row[2]), Integer.parseInt(row[3])};

            for (int k=0; k<n; k++) {
                schedule[i][k] = Integer.parseInt(row[4+k]);
            }
            i++;
        }
    }

    public int numberOfTeams() {
        return n;
    }

    public Iterable<String> teams() {
        return teams.keySet();
    }

    public int wins(String team) {
        return games[getTeamId(team)][0];
    }

    public int losses(String team) {
        return games[getTeamId(team)][1];
    }

    public int remaining(String team) {
        return games[getTeamId(team)][2];
    }

    public int against(String team1, String team2) {
        int id1 = getTeamId(team1);
        int id2 = getTeamId(team2);
        return schedule[id1][id2];
    }

    public boolean isEliminated(String team) {
        int id = getTeamId(team);
        if (id != 0){

        }
        return false;
    }

    public Iterable<String> certificateOfElimination(String team)  {
        // subset R of teams that eliminates given team; null if not eliminated
        return new ArrayList<>();
    }

    private void buildFlowNetworkForTeam(String team) {
        int id = getTeamId(team);
        int teamPotential = wins(team) + remaining(team);
        int teamsV = (n-1);
        int gamesV = (n-1)*(n-2)/2;
        FlowNetwork fn = new FlowNetwork(teamsV + gamesV+2);

        for (Map.Entry<String, Integer> entry: teams.entrySet()) {
            if (entry.getKey().equals(team)) {
                continue;
            }
            FlowEdge fe = new FlowEdge(entry.getValue(), this.t, teamPotential - wins(entry.getKey()));
            fn.addEdge(fe);
        }
        int nextVertId = t+1;
        for (int y=0; y<n; y++) {
            for (int x=y+1; x<n; x++){
                if (x != id) {
                    FlowEdge fe0 = new FlowEdge(this.s, nextVertId, schedule[y][x]);
                    FlowEdge fe1 = new FlowEdge(nextVertId, y, Double.POSITIVE_INFINITY);
                    FlowEdge fe2 = new FlowEdge(nextVertId, x, Double.POSITIVE_INFINITY);
                    fn.addEdge(fe0);
                    fn.addEdge(fe1);
                    fn.addEdge(fe2);
                    nextVertId++;
                }
            }
        }
        System.out.println(fn);
    }

    private int getTeamId(String team) {
        if (team == null) {
            throw new java.lang.IllegalArgumentException();
        }

        if (teams.get(team) == null) {
            throw new java.lang.IllegalArgumentException();
        }
        return teams.get(team);
    }

    public static void main(String[] args) {
        BaseballElimination be = new BaseballElimination("input/baseball/teams5.txt");
        be.buildFlowNetworkForTeam("Detroit");
    }
}
