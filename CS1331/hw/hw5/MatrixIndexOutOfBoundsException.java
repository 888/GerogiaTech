/**
 * Exception for Matrix index out of bounds.
 *
 * @author Noam Lerner
 * @version 1.0
 */
public class MatrixIndexOutOfBoundsException extends IndexOutOfBoundsException {
    /**
     * Constructs the MatrixIndexOutBoundsException with the given error
     *
     * @param error - string that represents the error
     */
    public MatrixIndexOutOfBoundsException(String error) {
        super(error);
    }
    /**
     * Constructs the MatrixIndexOutOfBoundsException with a generic error
     */
    public MatrixIndexOutOfBoundsException() {
        super("The coordinates entered into the matrix were out of the bounds "
            + "of the matrix");
    }
}