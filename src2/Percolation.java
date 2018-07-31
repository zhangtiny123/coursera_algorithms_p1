import edu.princeton.cs.algs4.WeightedQuickUnionUF;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.In;

import java.lang.String;

public class Percolation {
    private final WeightedQuickUnionUF wuf;
    // sites state array, blocked: false, opened: true
    private boolean[] sitesState;
    private int openSitesCount;
    private final int sitesLen;
    private final int edgeSize;

    // create n-by-n grid, with all sites blocked
    public Percolation(int n) {
        if (n <= 0) {
            throw new IllegalArgumentException("n must greater than 0");
        }

        edgeSize = n;
        openSitesCount = 0;
        sitesLen = n * n + 2;
        wuf = new WeightedQuickUnionUF(sitesLen);

        sitesState = new boolean[sitesLen];
        
        // first and last are virtual sites.
        sitesState[0] = true;
        sitesState[sitesLen - 1] = true;
        for (int i = 1; i < sitesLen - 1; i++) {
            sitesState[i] = false;
        }
    }

    private int getIndexOf(int row, int col) {
        return (row - 1) * edgeSize + col;
    }

    // open site (row, col) if it is not open already
    public void open(int row, int col) {
        if (row < 1 || row > edgeSize || col < 1 || col > edgeSize) {
            throw new IllegalArgumentException("row and col must between 1 to n");
        }

        int targetIndex = getIndexOf(row, col);
        if (sitesState[targetIndex] == true) {
            return;
        }

        sitesState[targetIndex] = true;
        openSitesCount++;

        int upTempIndex = targetIndex - edgeSize;
        int upIndex = upTempIndex > 0 ? upTempIndex : 0;
        int downTempIndex = targetIndex + edgeSize;
        int downIndex = downTempIndex < sitesLen - 1 ? downTempIndex : sitesLen - 1;
        int leftTempIndex = targetIndex - 1;
        int leftIndex = leftTempIndex % edgeSize == 0 ? -1 : leftTempIndex;
        int rightTempIndex = targetIndex + 1;
        int rightIndex = rightTempIndex % edgeSize == 1 ? -1 : rightTempIndex;

        if (sitesState[upIndex] == true) {
            wuf.union(targetIndex, upIndex);
        }

        if (sitesState[downIndex] == true) {
            wuf.union(targetIndex, downIndex);
        }

        if (leftIndex != -1 && sitesState[leftIndex] == true) {
            wuf.union(targetIndex, leftIndex);
        }

        if (rightIndex != -1 && sitesState[rightIndex] == true) {
            wuf.union(targetIndex, rightIndex);
        }
    }

    // is site (row, col) open?
    public boolean isOpen(int row, int col) {
        if (row < 1 || row > edgeSize || col < 1 || col > edgeSize) {
            throw new IllegalArgumentException("row and col must between 1 to n");
        }

        int index = getIndexOf(row, col);
        return sitesState[index] == true;
    }

    // is site (row, col) full?
    public boolean isFull(int row, int col) {
        if (row < 1 || row > edgeSize || col < 1 || col > edgeSize) {
            throw new IllegalArgumentException("row and col must between 1 to n");
        }
        
        int index = getIndexOf(row, col);
        return wuf.connected(index, 0);

    }

    // number of open sites
    public int numberOfOpenSites() {
        return openSitesCount;
    }

    // does the system percolate?
    public boolean percolates() {
        return wuf.connected(sitesLen - 1, 0);
    }

    // test client (optional)
    public static void main(String[] args) {
        int eSize;
        if (args.length != 0) {
            eSize = Integer.parseInt(args[0]);
        } else {
            eSize = 100;
        }

        String filePath = "/Users/zhangtao/Downloads/percolation/input20.txt";

        System.out.println("readAll() from file " + filePath);
        System.out.println("---------------------------------------------------------------------------");
        try {
            In fileIn = new In(filePath);
//            System.out.println(in.readAll());
            while (!fileIn.isEmpty()) {
                String s = fileIn.readLine();
                System.out.println("read line: " + s);

                Pattern p = Pattern.compile("-?\\d+");
                Matcher m = p.matcher("There are more than -2 and less than 12 numbers here");
                while (m.find()) {
                    System.out.println(m.group());
                }

            }
        }
        catch (IllegalArgumentException e) {
            System.out.println(e);
        }
        System.out.println();

        Percolation pTest = new Percolation(eSize);
        while (!pTest.percolates()) {
            int randRow = StdRandom.uniform(pTest.edgeSize) + 1;
            int randCol = StdRandom.uniform(pTest.edgeSize) + 1;
            pTest.open(randRow, randCol);
        }

        int openCount = pTest.numberOfOpenSites();
        System.out.println("Open sites number: " + openCount);
        int allCount = pTest.sitesLen - 2;
        System.out.println("Whole sites number: " + allCount);
        System.out.println("rate: " + ((double) openCount / (double) allCount));
    }
}