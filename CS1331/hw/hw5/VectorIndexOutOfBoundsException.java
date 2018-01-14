/**
 * Exception for Vector index out of bounds.
 *
 * @author Noam Lerner
 * @version 1.0
 */
public class VectorIndexOutOfBoundsException extends IndexOutOfBoundsException {
    /**
     * Constructs the VectorIndexOutBoundsException with the given error
     *
     * @param error - string that represents the error
     */
    VectorIndexOutOfBoundsException(String error) {
        super(error);
    }
    /**
     * Constructs the VectorIndexOutOfBoundsException with a generic error
     */
    public VectorIndexOutOfBoundsException() {
        super("The index entered into the vector was out of the bounds "
            + "of the vector.");
    }
}