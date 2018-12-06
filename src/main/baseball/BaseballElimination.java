import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.FlowEdge;
import edu.princeton.cs.algs4.FlowNetwork;
import edu.princeton.cs.algs4.FordFulkerson;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class BaseballElimination {
    private final int n;
    private final HashMap<String, Integer> teams;
    private final int[][] games;
    private final int[][] schedule;

    public BaseballElimination(String filename) {
        In in = new In(filename);
        int n = Integer.parseInt(in.readLine());
        this.n = n;
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
        // is given team eliminated?
        return false;
    }

    public Iterable<String> certificateOfElimination(String team)  {
        // subset R of teams that eliminates given team; null if not eliminated
        return new ArrayList<>();
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
        BaseballElimination be = new BaseballElimination("input/baseball/teams4.txt");
        System.out.println(be.teams());
    }
}
