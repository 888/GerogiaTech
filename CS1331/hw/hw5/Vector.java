/**
 * Immutable abstraction for Vector
 *
 * @author Michael Maurer
 * @version 1.2
 */
public class Vector {

    private final double[] vector;
    private final int length;

    /**
     * Initialize instance variables
     * @param vector array representation of vector
     */
    public Vector(double[] vector) {
        this.vector = vector;
        length = vector.length;
    }

    /**
     * Gets value located at specified index
     * @param i index in vector
     * @return double located at index 'i' in vector
     */
    public double get(int i) {
        if (i < 0 || i > length) {
            String error = "Index " + i + " does not exist in the vector. The "
                + "vector has a length of " + length + " numbers";
            throw new VectorIndexOutOfBoundsException(error);
        } else {
            return this.vector[i];
        }
    }

    /**
     * Get's the length of the Vector.
     * @return number of components in vector
     */
    public int getLength() {
        return length;
    }

    /**
     * String representation of vector with components
     * separated by tabs
     * @return String representation of vector
     */
    public String toString() {
        String message = "";
        for (int i = 0; i < length; i++) {
            message += vector[i] + "\t";
        }
        return message;
    }
}