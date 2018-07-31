import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {
    private double[] openRates;
    private final int trialTimes;
    private final int edgeSize;
    private final int sitesLen;

    // perform trials independent experiments on an n-by-n grid
    public PercolationStats(int n, int trials) {
        if (n <= 0 || trials <= 0) {
            throw new IllegalArgumentException("n and trial must greater than or equal to 0");
        }

        openRates = new double[trials];
        trialTimes = trials;
        edgeSize = n;
        sitesLen = n * n + 2;
    }

    // sample mean of percolation threshold
    public double mean() {
        return StdStats.mean(openRates);
    }

    // sample standard deviation of percolation threshold
    public double stddev() {
        return StdStats.stddev(openRates);
    }

    // low  endpoint of 95% confidence interval
    public double confidenceLo() {
        return mean() - getConfidenceDelta();
    }

    private double getConfidenceDelta() {
        return (1.96 * stddev()) / Math.sqrt(trialTimes);
    }

    // high endpoint of 95% confidence interval
    public double confidenceHi() {
        return mean() + getConfidenceDelta();
    }
    
    private void runPercolation() {
        for (int i = 0; i < trialTimes; i++) {
            Percolation pcl = new Percolation(edgeSize);
            while (!pcl.percolates()) {
                int randRow = StdRandom.uniform(edgeSize) + 1;
                int randCol = StdRandom.uniform(edgeSize) + 1;
                pcl.open(randRow, randCol);
            }
            openRates[i] = (double) pcl.numberOfOpenSites() / (double) (sitesLen - 2);
        }
    }

    // test client (described below)
    public static void main(String[] args) {
        int eSize = 100;
        int trials = 50;
        if (args.length != 0) {
            if (args[0] != null) {
                eSize = Integer.parseInt(args[0]);
            }
            if (args[1] != null) {
                trials = Integer.parseInt(args[1]);
            }
        }

        PercolationStats pcls = new PercolationStats(eSize, trials);
        pcls.runPercolation();

        System.out.println("mean                      = " + pcls.mean());
        System.out.println("stddev                    = " + pcls.stddev());
        System.out.println("95% confidence interval   = [" + pcls.confidenceLo() + ", " + pcls.confidenceHi() + "]");
    }
}