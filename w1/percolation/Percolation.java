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
    private int num_open = 0;

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

        // connect all points in the first row to the top
        for (int i = 0; i < n; i++)
            uf.union(top, i + 1);
        // connect all points in the second row to the bottom
        for (int i = 0; i < n; i++)
            uf.union(bottom, n * n - i);
    }

    // opens the site (row, col) if it is not open already
    public void open(int row, int col) {
        // check validity
        if (row < 1 || row > len || col < 1 || col > len) throw new IndexOutOfBoundsException();
        // open the site
        grid[row - 1][col - 1] = true;
        // add open count
        num_open++;

        // union it with its neighbors
        int i = (row - 1) * len + (col - 1);
        // left neighbor
        if (col - 1 > 0 && grid[row - 1][col - 2]) uf.union(i, i - 1);
        // right neighbor
        if (col - 1 < len - 1 && grid[row - 1][col]) uf.union(i, i + 1);
        // top neighbor
        if (row - 1 > 0 && grid[row - 2][col - 1]) uf.union(i, i - len);
        // bottom neighbor
        if (row - 1 < len - 1 && grid[row][col - 1]) uf.union(i, i + len);
    }

    // is the site (row, col) open?
    public boolean isOpen(int row, int col) {
        // check validity
        if (row < 1 || row > len || col < 1 || col > len) throw new IndexOutOfBoundsException();
        return grid[row - 1][col - 1];
    }

    // is the site (row, col) full?
    public boolean isFull(int row, int col) {
        // check validity
        if (row < 1 || row > len || col < 1 || col > len) throw new IndexOutOfBoundsException();

        int i = (row - 1) * len + (col - 1);
        if (uf.connected(i, top)) return true;
        return false;
    }

    // returns the number of open sites
    public int numberOfOpenSites() {
        return num_open;
    }

    // does the system percolate?
    public boolean percolates() {
        return uf.connected(top, bottom);
    }

    // test client (optional)
    public static void main(String[] args) {
    }
}
