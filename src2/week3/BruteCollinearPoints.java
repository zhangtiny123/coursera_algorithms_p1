import java.lang.IllegalArgumentException;
import java.util.ArrayList;
import java.util.Arrays;

public class BruteCollinearPoints {
    private ArrayList<LineSegment> foundSegments = new ArrayList<LineSegment>();


    // finds all line segments containing 4 points
    public BruteCollinearPoints(Point[] points) {
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

        for (int i=0; i < ps_copy.length - 3; i++) {
            for (int j=i+1; j < ps_copy.length - 2; j++) {
                double slopeFS = ps_copy[i].slopeTo(ps_copy[j]);
                for (int k=j+1; k < ps_copy.length -1; k++) {
                    double slopeFT = ps_copy[i].slopeTo(ps_copy[k]);
                    if (slopeFS == slopeFT) {
                        for (int h=k+1; h < ps_copy.length; h++) {
                            double slopeFF = ps_copy[i].slopeTo(ps_copy[h]);
                            if (slopeFS == slopeFF) {
                                foundSegments.add(new LineSegment(ps_copy[i], ps_copy[h]));
                            }
                        }
                    }
                }
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