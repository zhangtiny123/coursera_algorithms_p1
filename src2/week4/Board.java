import static java.lang.Math.*;
import edu.princeton.cs.algs4.Queue;

public class Board {
    // imaginary tile
    private static final int DIRKA = 0;

    private char[] board;
    private int boardDimension;

    // construct a board (1*N^2 array of char) from an N-by-N array of int
    public Board(int[][] blocks) {
        boardDimension = blocks.length;
        board = new char[boardDimension * boardDimension];
        int elem = 0;
        for (int[] cs : blocks) {
            for (int c : cs) {
                board[elem++] = (char) c;
            }
        }
    }

    // returns board dimension
    public int dimension() {
        return boardDimension;
    }

    // returns number of blocks out of place
    public int hamming() {
        int out = 0;
        int i = 1;
        // from 1 to end
        for (char c : board) {
            if ((int) c != 0 && (int) c != i) {
                // check last properly
                if ((int) c == 0 && (int) c == board.length - 1) {
                    continue;
                }
                out++;
            }
            i++;
        }
        return out;
    }

    // returns sum of Manhattan distances between blocks and goal position
    public int manhattan() {
        int out = 0;
        int i = 1;
        // from 1 to end
        for (char c : board) {
            if ((int) c != 0 && (int) c != i) {
                // check last properly
                if ((int) c == 0 && (int) c == board.length - 1) {
                    continue;
                }
                // row steps
                final int dR = toXY((int) c)[0] - toXY(i)[0];
                // colomn
                final int dC = toXY((int) c)[1] - toXY(i)[1];
                // count all
                out += abs(dR) + abs(dC);
            }
            i++;
        }
        return out;
    }

    // check if this board the goal board?
    public boolean isGoal() {
        return hamming() == 0;
    }

    // returns board that is obtained by exchanging
    // first pair of non-dirka blocks
    public Board twin() {
        return this.copyButTwin(true);
    }

    // return the clone of current board on "false" input,
    // and twin-copy if input param is "true"
    private Board copyButTwin(boolean twi) {
        boolean twined = false;
        // clone 1D array into 2D
        int[][] twinBoard = new int[boardDimension][boardDimension];
        int jIndex = 0;
        int iOne = 0, iTwo = 0;
        for (int i = 0; i < boardDimension; i++) {
            for (int k = 0; k < boardDimension; k++) {
                twinBoard[i][k] = this.board[jIndex++];
                // find first two tiles (not DIRKA) and remember to swap
                if (!twined && jIndex > 0 && (int) this.board[jIndex - 1] != DIRKA
                        && (int) this.board[jIndex] != DIRKA) {
                    iOne = jIndex;
                    iTwo = jIndex - 1;
                    twined = true;
                }
            }
        }

        Board twin = new Board(twinBoard);
        if (twi) {
            twin.swapTiles(iOne, iTwo);
        }
        return twin;
    }

    // check if this board equal to y?
    public boolean equals(Object y) {
        if (y == null)
            return false;
        if (y == this)
            return true;
        if (y.getClass() != this.getClass())
            return false;
        Board that = (Board) y;
        if (that.dimension() != this.dimension())
            return false;
        for (int i = 0; i < that.dimension() * that.dimension(); i++) {
            if (this.board[i] != that.board[i])
                return false;
        }
        return true;
    }

    // returns all neighboring boards
    public Iterable<Board> neighbors() {
        Queue<Board> jNeighbors = new Queue<Board>();
        int currentDIRKAIndex = -1;
        // try to find dirka
        for (int i = 0; i < board.length; i++) {
            if (board[i] == DIRKA) {
                currentDIRKAIndex = i;
                break;
            }
        }
        int n = currentDIRKAIndex / boardDimension;
        int m = currentDIRKAIndex % boardDimension;
        // dirka moves UP
        if (n - 1 >= 0) {
            Board u = this.copyButTwin(false);
            u.swapTiles(currentDIRKAIndex, xyTo1D(n - 1, m));
            jNeighbors.enqueue(u);
        }
        // dirka moves down
        if (n + 1 < boardDimension) {
            Board d = this.copyButTwin(false);
            d.swapTiles(currentDIRKAIndex, xyTo1D(n + 1, m));
            jNeighbors.enqueue(d);
        }
        // dirka moves left
        if (m - 1 >= 0) {
            Board l = this.copyButTwin(false);
            l.swapTiles(currentDIRKAIndex, xyTo1D(n, m - 1));
            jNeighbors.enqueue(l);
        }
        // dirka moves right
        if (m + 1 < boardDimension) {
            Board r = this.copyButTwin(false);
            r.swapTiles(currentDIRKAIndex, xyTo1D(n, m + 1));
            jNeighbors.enqueue(r);
        }
        return jNeighbors;
    }

    // returns [position] in 1D array from [X][Y] position of 2D array
    private int xyTo1D(final int x, final int y) {
        return (boardDimension * (x) + y);
    }

    // returns {X,Y} coords from [1D] coordinate.
    // init array count begins from 1 !!!
    private int[] toXY(final int d) {
        int x = (d - 1) / boardDimension;
        int y = (d - 1) % boardDimension;
        return new int[] { x, y };
    }

    // swap tiles i and k of this.board
    private void swapTiles(int i, int k) {
        char aux = this.board[i];
        this.board[i] = this.board[k];
        this.board[k] = aux;
    }

    // returns Board as matrix toString
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(boardDimension + "\n");
        int lol = 0;
        for (int i = 0; i < board.length; i++) {
            sb.append(String.format("%2d ", (int) board[i]));
            // alws make new line followed by matrix dimension
            if (lol++ % boardDimension == boardDimension - 1) {
                sb.append("\n");
            }
        }
        return sb.toString();
    }
}