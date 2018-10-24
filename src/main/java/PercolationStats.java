import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {
    double simulations[];
    int T;

    public PercolationStats(int n, int trials)  {
        if (n <= 0 || trials <= 0) {
            throw new IllegalArgumentException();
        }
        T = trials;
        simulations = new double[trials];
        for (int i = 0; i < trials; i++) {
            Percolation perc = new Percolation(n);
            while (!perc.percolates()) {
                int row = StdRandom.uniform(1, n+1);
                int col = StdRandom.uniform(1, n+1);
                perc.open(row, col);
            }
            simulations[i] = (double)perc.numberOfOpenSites() / 20 / 20;
        }
    }

    public double mean()  {
        return StdStats.mean(simulations);
    }

    public double stddev() {
        return StdStats.stddev(simulations);
    }

    public double confidenceLo() {
        return mean() - 1.96 * stddev() / Math.sqrt(T);
    }
    public double confidenceHi() {
        return mean() + 1.96 * stddev() / Math.sqrt(T);
    }

    public static void main(String[] args) {
        int n = Integer.parseInt(args[0]);
        int trials = Integer.parseInt(args[1]);
        PercolationStats stats = new PercolationStats(n, trials);

        System.out.println(stats.mean());
        System.out.println(stats.stddev());
        System.out.println(stats.confidenceLo());
        System.out.println(stats.confidenceHi());
    }
}
