/* *****************************************************************************
 * Flora Wang
 * Nov.27, 2021
 **************************************************************************** */

import java.util.ArrayList;
import java.util.Arrays;

public class BruteCollinearPoints {
    private LineSegment[] segments_array;

    // finds all line segments containing 4 points
    public BruteCollinearPoints(Point[] points) {
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

        for (int i = 0; i < n - 3; i++) {
            for (int j = i + 1; j < n - 2; j++) {
                for (int k = j + 1; k < n - 1; k++) {
                    if (copy[i].slopeTo(copy[j]) == copy[i].slopeTo(copy[k])) {
                        for (int l = k + 1; l < n; l++) {
                            if (copy[i].slopeTo(copy[j]) == copy[i].slopeTo(copy[l])) {
                                segments.add(new LineSegment(copy[i], copy[l]));
                            }
                        }
                    }
                }
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

    }
}
