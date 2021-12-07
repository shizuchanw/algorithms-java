/* *****************************************************************************
 * Flora Wang
 * 12/02, 2021
 **************************************************************************** */

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.StdOut;

import java.util.Arrays;

public class Board {
    private final int n;
    private final int[] tiles;

    // create a board from an n-by-n array of tiles,
    // where tiles[row][col] = tile at (row, col)
    public Board(int[][] tiles) {
        // assume receives an n-by-n array containing
        // the n^2 integers between 0 and n^2 − 1
        // 0 represents the blank square
        if (tiles == null) throw new IllegalArgumentException("");

        n = tiles.length;
        this.tiles = new int[n * n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                this.tiles[n * i + j] = tiles[i][j];
            }
        }
    }

    // string representation of this board
    public String toString() {
        String str = "";
        str += n + "\n";
        for (int i = 0; i < n * n; i++) {
            str += (tiles[i] + " ");
            if ((i + 1) % n == 0) {
                str += "\n";
            }
        }
        return str;
    }

    // board dimension n
    public int dimension() {
        return n;
    }

    // number of tiles out of place
    public int hamming() {
        int count = 0;
        for (int i = 0; i < n * n; i++) {
            if (tiles[i] != i + 1 && tiles[i] != 0) {
                count++;
            }
        }
        return count;
    }

    // row starts from 1
    private int row(int i) {
        return i / n + 1;
    }

    // column starts from 1
    private int col(int i) {
        return i % n + 1;
    }

    // sum of Manhattan distances between tiles and goal
    public int manhattan() {
        int diff = 0;
        for (int i = 0; i < n * n; i++) {
            if (tiles[i] != 0) {
                diff += (Math.abs(row(i) - row(tiles[i] - 1))) +
                        (Math.abs(col(i) - col(tiles[i] - 1)));
            }
        }
        return diff;
    }

    // is this board the goal board?
    public boolean isGoal() {
        for (int i = 0; i < n * n - 1; i++) {
            if (tiles[i] != i + 1) {
                return false;
            }
        }
        return true;
    }

    // does this board equal y?
    public boolean equals(Object y) {
        if (y == this) {
            return true;
        }
        if (y == null) {
            return false;
        }
        if (y.getClass() != this.getClass()) {
            return false;
        }

        Board that = (Board) y;
        return Arrays.equals(this.tiles, that.tiles);
    }

    // all neighboring boards
    public Iterable<Board> neighbors() {
        int zero_pos = -1;
        for (int i = 0; i < n * n; i++) {
            if (tiles[i] == 0) {
                zero_pos = i;
                break;
            }
        }
        int row = row(zero_pos);
        int col = col(zero_pos);

        Stack<Board> neighbors = new Stack<Board>();
        int[] dx = { 0, 1, 0, -1 };
        int[] dy = { 1, 0, -1, 0 };
        for (int i = 0; i < 4; i++) {
            if (row + dx[i] <= n && row + dx[i] >= 1 && col + dy[i] <= n && col + dy[i] >= 1) {
                int[][] neighbor = toMatrix(tiles);
                swap(neighbor, row, row + dx[i], col, col + dy[i]);
                Board neighborBoard = new Board(neighbor);
                neighbors.push(neighborBoard);
            }
        }
        return neighbors;
    }

    private void swap(int[][] matrix, int x0, int x1, int y0, int y1) {
        int temp = matrix[x0 - 1][y0 - 1];
        matrix[x0 - 1][y0 - 1] = matrix[x1 - 1][y1 - 1];
        matrix[x1 - 1][y1 - 1] = temp;
    }

    private int[][] toMatrix(int[] array) {
        int[][] matrix = new int[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                matrix[i][j] = array[i * n + j];
            }
        }
        return matrix;
    }

    // a board that is obtained by exchanging any pair of tiles
    public Board twin() {
        int[][] twin = toMatrix(tiles);
        if (tiles[0] != 0 && tiles[1] != 0)
            swap(twin, 1, 1, 1, 2);
        else
            swap(twin, 2, 1, 2, 2);
        return new Board(twin);
    }

    // unit testing (not graded)
    public static void main(String[] args) {
        // create initial board from file
        In in = new In(args[0]);
        int n = in.readInt();
        int[][] tiles = new int[n][n];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                tiles[i][j] = in.readInt();
        Board initial = new Board(tiles);

        StdOut.println(initial.dimension());
        StdOut.println(initial.hamming());
        StdOut.println(initial.manhattan());
        StdOut.println(initial.isGoal());
    }
}
