/**
 * Immutable abstraction of Matrix.
 *
 * @author Michael Maurer
 * @version 1.2
 */
public class Matrix {

    private final double [][] matrix;
    private final int height;
    private final int width;

    /**
     * Initialize instance variables
     * @param matrix 2D array representation of Matrix
     */
    public Matrix(double[][] matrix) {
        this.matrix = matrix;
        height = matrix.length;
        width = matrix[0].length;
    }

    /**
     * Gets value located at specified row and column
     * @param i row
     * @param j column
     * @return double located at row i and column j in matrix
     */
    public double get(int i, int j) {
        if (i > height || j > width || i < 0 || j < 0) {
            String error = "(" + i + "," + j + ") does not exist in the matrix"
                + ". The matrix exists between (0,0) and (" + (height - 1)
                + "," + (width - 1) + "), inclusive.";
            throw new MatrixIndexOutOfBoundsException(error);
        }
        return matrix[i][j];
    }

    /**
     * Get's the height of the matrix.
     * @return number of rows in matrix
     */
    public int getHeight() {
        return height;
    }

    /**
     * Get's the width of the matrix.
     * @return number of columns in matrix
     */
    public int getWidth() {
        return width;
    }

    /**
     * Gets String representation of matrix.
     * Columns separated by tabs, rows by new lines.
     * @return String representation of matrix.
     */
    public String toString() {
        String representation = "";
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                representation += matrix[i][j] + "\t";
            }
            representation += "\n";
        }
        return representation;
    }
}