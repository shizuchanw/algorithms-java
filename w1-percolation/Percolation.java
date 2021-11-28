/* *****************************************************************************
 *  Name:              Flora Wang
 *  Last modified:     11/09/2021
 **************************************************************************** */

import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private WeightedQuickUnionUF uf;
    private boolean[][] grid;
    private int len;
    private int top;
    private int bottom;
    private int numOpen = 0;

    // creates n-by-n grid, with all sites initially blocked
    public Percolation(int n) {
        // make sure we have a legal size
        if (n <= 0) throw new IllegalArgumentException();
        // store size
        len = n;

        // set up the grid: if open, store true.
        // all are initialized to be blocked, so all are false (taking the default here).
        grid = new boolean[n][n];
        // make a WQU object: include the virtual top and bottom nodes
        uf = new WeightedQuickUnionUF(n * n + 2);

        // set up top and bottom nodes
        top = 0;
        bottom = n * n + 1;

    }

    // opens the site (row, col) if it is not open already
    public void open(int row, int col) {
        // check validity
        if (row < 1 || row > len || col < 1 || col > len) throw new IllegalArgumentException();
        // open the site
        grid[row - 1][col - 1] = true;
        // add open count
        numOpen++;

        // union it with its neighbors
        int i = (row - 1) * len + col;
        // left neighbor
        if (col - 1 > 0 && grid[row - 1][col - 2]) uf.union(i, i - 1);
        // right neighbor
        if (col - 1 < len - 1 && grid[row - 1][col]) uf.union(i, i + 1);
        // top neighbor
        if (row == 1) {
            uf.union(i, top);
        }
        else if (grid[row - 2][col - 1]) {
            uf.union(i, i - len);
        }
        // bottom neighbor
        if (row == len) {
            uf.union(i, bottom);
        }
        else if (grid[row][col - 1]) {
            uf.union(i, i + len);
        }
    }

    // is the site (row, col) open?
    public boolean isOpen(int row, int col) {
        // check validity
        if (row < 1 || row > len || col < 1 || col > len) throw new IllegalArgumentException();
        return grid[row - 1][col - 1];
    }

    // is the site (row, col) full?
    public boolean isFull(int row, int col) {
        // check validity
        if (row < 1 || row > len || col < 1 || col > len) throw new IllegalArgumentException();

        int i = (row - 1) * len + col;
        return uf.connected(i, top);
    }

    // returns the number of open sites
    public int numberOfOpenSites() {
        return numOpen;
    }

    // does the system percolate?
    public boolean percolates() {
        return uf.connected(top, bottom);
    }

    // test client (optional)
    public static void main(String[] args) {
    }
}
