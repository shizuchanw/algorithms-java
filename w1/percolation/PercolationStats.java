/* *****************************************************************************
 *  Name:              Flora Wang
 *  Last modified:     11/09/2021
 **************************************************************************** */

import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {
    private double[] openRatio;
    private int T;

    // perform independent trials on an n-by-n grid
    public PercolationStats(int n, int trials) {

        if (n <= 0) {
            throw new IllegalArgumentException("n is lower or equal than 0");
        }
        if (trials <= 0) {
            throw new IllegalArgumentException("T is lower or equal than 0");
        }

        openRatio = new double[trials];
        T = trials;

        // create trials
        for (int i = 0; i < trials; i++) {
            Percolation attempt = new Percolation(n);
            // keep opening till percolates
            double number_open = 0;
            while (!attempt.percolates()) {
                // generate random blocked site
                int row = StdRandom.uniform(n) + 1;
                int col = StdRandom.uniform(n) + 1;

                // open it
                if (!attempt.isOpen(row, col)) {
                    attempt.open(row, col);
                    number_open += 1;
                }
            }
            // record the ratio of # opened sites and # total sites
            openRatio[i] = number_open / (n * n);
        }


    }


    // sample mean of percolation threshold
    public double mean() {
        return StdStats.mean(openRatio);
    }

    // sample standard deviation of percolation threshold
    public double stddev() {
        return StdStats.stddev(openRatio);
    }

    // low endpoint of 95% confidence interval
    public double confidenceLo() {
        return mean() - 1.96 * stddev() / T;
    }

    // high endpoint of 95% confidence interval
    public double confidenceHi() {
        return mean() + 1.96 * stddev() / T;
    }

    // test client
    public static void main(String[] args) {
        if (args.length != 2) {
            return;
        }

        int n = Integer.parseInt(args[0]);
        int trials = Integer.parseInt(args[1]);

        PercolationStats test = new PercolationStats(n, trials);
        StdOut.println("mean                    = " + test.mean());
        StdOut.println("stddev                  = " + test.stddev());
        StdOut.println("95% confidence interval = ["
                               + test.confidenceLo()
                               + ", "
                               + test.confidenceHi()
                               + "]");
    }
}
