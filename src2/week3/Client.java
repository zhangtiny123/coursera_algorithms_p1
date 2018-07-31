import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.Scanner;

import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdDraw;

public class Client {
    public static void main(String[] args) throws FileNotFoundException {

        Point[] input_points1 = new Point[6];
        input_points1[0] = new Point(19000, 10000);
        input_points1[1] = new Point(18000, 10000);
        input_points1[2] = new Point(32000, 10000);
        input_points1[3] = new Point(21000, 10000);
        input_points1[4] = new Point(1234, 5678);
        input_points1[5] = new Point(14000, 10000);

        Point[] input_points2 = new Point[8];
        input_points2[0] = new Point(10000, 0);
        input_points2[1] = new Point(0, 10000);
        input_points2[2] = new Point(3000, 7000);
        input_points2[3] = new Point(7000, 3000);
        input_points2[4] = new Point(20000, 21000);
        input_points2[5] = new Point(3000, 14000);
        input_points2[6] = new Point(14000, 15000);
        input_points2[7] = new Point(6000, 7000);




        Point[] points = input_points2;
//        for (int i = 0; i < N; i++) {
//            int x = scanner.nextInt();
//            int y = scanner.nextInt();
//            points[i] = new Point(x, y);
//        }



        // draw the points
        StdDraw.enableDoubleBuffering();
        StdDraw.setXscale(0, 32768);
        StdDraw.setYscale(0, 32768);
        for (Point p : points) {
            p.draw();
        }
        StdDraw.show();

        // print and draw the line segments
        BruteCollinearPoints collinear = new BruteCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();
    }
}