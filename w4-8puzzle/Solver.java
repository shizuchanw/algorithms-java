/* *****************************************************************************
 * Flora Wang
 * 12/02, 2021
 **************************************************************************** */

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.StdOut;

public class Solver {
    private MinPQ<searchNode> pq = new MinPQ<searchNode>();
    private MinPQ<searchNode> pqTwin = new MinPQ<searchNode>();
    private searchNode goal;

    private class searchNode implements Comparable<searchNode> {
        private int moves;
        private Board board;
        private searchNode prev;
        private int priority;

        public searchNode(int moves, Board board, searchNode prev) {
            this.moves = moves;
            this.board = board;
            this.prev = prev;
            this.priority = moves + board.manhattan();
        }

        public int compareTo(searchNode that) {
            return this.priority - that.priority;
        }
    }

    // find a solution to the initial board (using the A* algorithm)
    public Solver(Board initial) {
        // check argument validity
        if (initial == null) throw new NullPointerException();

        // initialize variables
        pq.insert(new searchNode(0, initial, null));
        pqTwin.insert(new searchNode(0, initial.twin(), null));
        searchNode min = pq.delMin();
        searchNode minTwin = pqTwin.delMin();

        // search for goal
        while (!min.board.isGoal() && !minTwin.board.isGoal()) {
            // check neighbors for pq
            for (Board b : min.board.neighbors()) {
                if (min.prev == null || !b.equals(min.prev.board)) {
                    searchNode n = new searchNode(min.moves + 1, b, min);
                    pq.insert(n);
                }
            }
            // check neighbors for pqTwin
            for (Board b : minTwin.board.neighbors()) {
                if (minTwin.prev == null || !b.equals(minTwin.prev.board)) {
                    searchNode n = new searchNode(minTwin.moves + 1, b, minTwin);
                    pqTwin.insert(n);
                }
            }
            min = pq.delMin();
            minTwin = pqTwin.delMin();
        }
        if (min.board.isGoal()) goal = min;
        else goal = null;
    }


    // is the initial board solvable? (see below)
    public boolean isSolvable() {
        return goal != null;
    }

    // min number of moves to solve initial board; -1 if unsolvable
    public int moves() {
        if (!isSolvable()) return -1;
        return goal.moves;
    }

    // sequence of boards in a shortest solution; null if unsolvable
    public Iterable<Board> solution() {
        if (!isSolvable()) return null;
        Stack<Board> sol = new Stack<Board>();
        searchNode curr = goal;
        while (curr != null) {
            sol.push(curr.board);
            curr = curr.prev;
        }
        return sol;
    }

    // test client (see below)
    public static void main(String[] args) {
        // create initial board from file
        In in = new In(args[0]);
        int n = in.readInt();
        int[][] tiles = new int[n][n];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                tiles[i][j] = in.readInt();
        Board initial = new Board(tiles);

        // solve the puzzle
        Solver solver = new Solver(initial);

        // print solution to standard output
        if (!solver.isSolvable())
            StdOut.println("No solution possible");
        else {
            StdOut.println("Minimum number of moves = " + solver.moves());
            for (Board board : solver.solution())
                StdOut.println(board);
        }
    }
}
