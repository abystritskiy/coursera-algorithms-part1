import edu.princeton.cs.algs4.FlowNetwork;
import edu.princeton.cs.algs4.FlowEdge;
import edu.princeton.cs.algs4.FordFulkerson;
import edu.princeton.cs.algs4.In;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class BaseballElimination {
    private final int n;
    private final HashMap<String, Integer> teams;
    private final String[] teamNames;

    private ArrayList<FlowEdge> edgesFromS;
    private ArrayList<Integer> minCut;

    private final int[][] games;
    private final int[][] schedule;
    private int s;
    private int t;
    private static final int SHIFT = 4;

    public BaseballElimination(String filename) {
        In in = new In(filename);
        this.n = Integer.parseInt(in.readLine());
        teams = new HashMap<>();
        teamNames = new String[n];
        games = new int[n][3];
        schedule = new int[n][n];

        int i = 0;
        while (!in.isEmpty()) {
            String line = in.readLine();
            if (line == null) {
                continue;
            }

            String[] row = line.trim().split("(\\s+)");
            teams.put(row[0], i);
            teamNames[i] = row[0];
            games[i] = new int[]{Integer.parseInt(row[1]), Integer.parseInt(row[2]), Integer.parseInt(row[3])};

            for (int k = 0; k < n; k++) {
                schedule[i][k] = Integer.parseInt(row[SHIFT + k]);
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
        int teamPotential = wins(team) + remaining(team);

        for (int k = 0; k < n; k++) {
            String opponent = teamNames[k];
            if (opponent.equals(team)) {
                continue;
            }
            if (teamPotential - wins(opponent) < 0) {
                return true;
            }
        }

        FlowNetwork fn = buildFlowNetworkForTeam(team);
        FordFulkerson maxFLow = new FordFulkerson(fn, s, t);

        minCut = new ArrayList<>();
        for (int v = 0; v < fn.V(); v++) {
            if (maxFLow.inCut(v)) {
                minCut.add(v);
            }
        }

        System.out.println(minCut);

        for (FlowEdge edge : edgesFromS) {
            if (edge.flow() < edge.capacity()) {
                return true;
            }
        }
        return false;
    }

    public Iterable<String> certificateOfElimination(String team) {
        if (!isEliminated(team)) {
            return null;
        }

        int teamPotential = wins(team) + remaining(team);
        ArrayList<String> eliminationSet = new ArrayList<>();

        for (int k = 0; k < n; k++) {
            String opponent = teamNames[k];
            if (opponent.equals(team)) {
                continue;
            }
            if (teamPotential - wins(opponent) < 0) {
                eliminationSet.add(opponent);
                return eliminationSet;
            }
        }

        for (int el : minCut) {
            if (el < n) {
                eliminationSet.add(teamNames[el]);
            }
        }
        return eliminationSet;
    }

    private FlowNetwork buildFlowNetworkForTeam(String team) {
        int id = getTeamId(team);
        int teamPotential = wins(team) + remaining(team);
        int teamsV = n;
        int gamesV = ((n - 1) * (n - 1) - n + 1) / 2;
        FlowNetwork fn = new FlowNetwork(teamsV + gamesV + 2);

        this.s = teamsV + gamesV;
        this.t = teamsV + gamesV + 1;

        for (Map.Entry<String, Integer> entry : teams.entrySet()) {
            if (entry.getKey().equals(team)) {
                continue;
            }
            int opponentWins = wins(entry.getKey());

            FlowEdge fe = new FlowEdge(entry.getValue(), t, Math.max(teamPotential - opponentWins, 0));
            fn.addEdge(fe);
        }
        int nextVertexId = n;

        edgesFromS = new ArrayList<>();

        for (int y = 0; y < n; y++) {
            if (y == id) continue;
            for (int x = y + 1; x < n; x++) {

                if (x == id) continue;

                FlowEdge fe0 = new FlowEdge(s, nextVertexId, schedule[y][x]);
                FlowEdge fe1 = new FlowEdge(nextVertexId, y, Double.POSITIVE_INFINITY);
                FlowEdge fe2 = new FlowEdge(nextVertexId, x, Double.POSITIVE_INFINITY);

                edgesFromS.add(fe0);

                fn.addEdge(fe0);
                fn.addEdge(fe1);
                fn.addEdge(fe2);

                nextVertexId++;
            }
        }
        return fn;
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
        System.out.println(be.isEliminated("Detroit"));
        System.out.println(be.certificateOfElimination("Detroit"));
    }
}
