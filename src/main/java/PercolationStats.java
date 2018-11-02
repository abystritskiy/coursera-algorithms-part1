import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {
    private static final double CONFIDENCE_RATIO = 1.96;
    private final double[] simulations;
    private final int t;

    public PercolationStats(int n, int trials) {
        if (n <= 0 || trials <= 0) {
            throw new IllegalArgumentException();
        }
        t = trials;
        simulations = new double[trials];
        for (int i = 0; i < trials; i++) {
            Percolation perc = new Percolation(n);
            while (!perc.percolates()) {
                int row = StdRandom.uniform(1, n + 1);
                int col = StdRandom.uniform(1, n + 1);
                perc.open(row, col);
            }
            simulations[i] = (double) perc.numberOfOpenSites() / n / n;
        }
    }

    public double mean() {
        return StdStats.mean(simulations);
    }

    public double stddev() {
        return StdStats.stddev(simulations);
    }

    public double confidenceLo() {
        return mean() - getRateCoefficient();

    }

    public double confidenceHi() {
        return mean() + getRateCoefficient();
    }

    private double getRateCoefficient() {
        return CONFIDENCE_RATIO * stddev() / Math.sqrt(t);
    }

    public static void main(String[] args) {
        int n = Integer.parseInt(args[0]);
        int trials = Integer.parseInt(args[1]);
        PercolationStats stats = new PercolationStats(n, trials);
        System.out.println(stats.mean());
    }
}
