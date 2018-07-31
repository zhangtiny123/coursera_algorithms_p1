import java.lang.IllegalArgumentException;
import java.util.ArrayList;
import java.util.Arrays;

public class FastCollinearPoints {
    private ArrayList<LineSegment> foundSegments = new ArrayList<LineSegment>();

    // finds all line segments containing 4 or more points
    public FastCollinearPoints(Point[] points) {
        if (points == null) {
            throw new IllegalArgumentException("argument points should not be null");
        }

        Point[] ps_copy = points.clone();
        Arrays.sort(ps_copy);

        for (int i = 0; i < ps_copy.length; i++) {
            if (ps_copy[i] == null) {
                throw new IllegalArgumentException("you have null in points.");
            }

            if (i < ps_copy.length - 1 && ps_copy[i].compareTo(ps_copy[i+1]) == 0) {
                throw new IllegalArgumentException("you have duplicate points.");
            }
        }

        for (int i = 0; i < ps_copy.length; i++) {
            // resort the ps_copy array
            Arrays.sort(ps_copy);
            // sort by slope from i
            Arrays.sort(ps_copy, ps_copy[i].slopeOrder());

            for (int p = 0, j = 1, k = 2; k < ps_copy.length; k++) {
                // because of sorted slopes, find last position k whose slope to p is equal to j. (j is start position, k is end position)
                while (k < ps_copy.length && Double.compare(ps_copy[p].slopeTo(ps_copy[j]), ps_copy[p].slopeTo(ps_copy[k])) == 0) {
                    k++;
                }

                // when found the end position, k is equal to endPosition + 1; so make sure k -j >= 3 and p is less than the slope.
                if (k - j >= 3 && ps_copy[p].compareTo(ps_copy[j]) < 0) {
                    foundSegments.add(new LineSegment(ps_copy[p], ps_copy[k - 1]));
                }

                // continue to find the next piece of points, reset start position j to k, and continue to move k to the end position.
                j = k;
            }
        }
    }

    // the number of line segments
    public int numberOfSegments() {
        return foundSegments.size();
    }

    // the line segments
    public LineSegment[] segments() {
        return foundSegments.toArray(new LineSegment[foundSegments.size()]);
    }
}