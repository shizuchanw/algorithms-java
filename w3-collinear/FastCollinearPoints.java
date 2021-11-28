/* *****************************************************************************
 * Flora Wang
 * Nov.27, 2021
 **************************************************************************** */

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

import java.util.ArrayList;
import java.util.Arrays;

public class FastCollinearPoints {
    private LineSegment[] segments_array;

    public FastCollinearPoints(Point[] points) {
        ArrayList<LineSegment> segments = new ArrayList<>();

        // null exceptions
        if (points == null) throw new IllegalArgumentException("");
        int n = points.length;
        for (int i = 0; i < n; i++) {
            if (points[i] == null) throw new IllegalArgumentException("");
        }

        // duplicates exceptions
        Point[] copy = points.clone();
        Arrays.sort(copy);
        for (int i = 0; i < copy.length - 1; i++) {
            if (copy[i].compareTo(copy[i + 1]) == 0) {
                throw new IllegalArgumentException("");
            }
        }

        // sort points by slope
        for (int i = 0; i < n - 3; i++) {
            // reorganize array
            Arrays.sort(copy);
            // sort based on slope relative to point[i]
            Arrays.sort(copy, copy[i].slopeOrder());
            for (int p = 0, first = 1, last = 2; last < n; last++) {
                // look for the last collinear point
                while (last < n && copy[p].slopeTo(copy[first]) == copy[p]
                        .slopeTo(copy[last])) {
                    last++;
                }
                if ((last - first) >= 3 && copy[p].compareTo(copy[first]) < 0) {
                    segments.add(new LineSegment(copy[p], copy[last - 1]));
                }
                first = last;
            }
        }

        segments_array = segments.toArray(new LineSegment[segments.size()]);
    }

    // the number of line segments
    public int numberOfSegments() {
        return segments_array.length;
    }

    // the line segments
    public LineSegment[] segments() {
        return segments_array;
    }

    public static void main(String[] args) {

        // read the n points from a file
        In in = new In(args[0]);
        int n = in.readInt();
        Point[] points = new Point[n];
        for (int i = 0; i < n; i++) {
            int x = in.readInt();
            int y = in.readInt();
            points[i] = new Point(x, y);
        }

        // draw the points
        StdDraw.enableDoubleBuffering();
        StdDraw.setXscale(0, 32768);
        StdDraw.setYscale(0, 32768);
        for (Point p : points) {
            p.draw();
        }
        StdDraw.show();

        // print and draw the line segments
        FastCollinearPoints collinear = new FastCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();
    }

}
