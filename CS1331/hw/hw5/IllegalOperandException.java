/**
 * Exception for Illegal Operands.
 *
 * @author Noam Lerner
 * @version 1.0
 */
public class IllegalOperandException extends Exception {
    /**
     * Constructs an IllegalOperandException  with the given error
     *
     * @param error - string that represents the error
     */
    IllegalOperandException(String error) {
        super(error);
    }
    /**
     * Construcs a generic IllegalOperandException
     */
    IllegalOperandException() {
        super("The operands entered were invalid.");
    }
}