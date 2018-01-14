public class LinearAlgebra {
    /**
     * Multiplies matrix m and vector v
     * @param  m matrix to be multiplied
     * @param  v vector to be multiplied
     * @return   vector representation of the product (m * v)
     * @throws IllegalOperandException
     */
    public static Vector matrixVectorMultiply(Matrix m, Vector v)
        throws IllegalOperandException {
        if (m.getWidth() != v.getLength()) {
            String error = "In order to multiply a matrix and vector together,"
                + " the matrix's width and vector's length must be equal. The "
                + "entered vector has a length of " + v.getLength() + " and "
                + "the matrix entered has a width of " + m.getWidth() + ".";
            throw new IllegalOperandException(error);
        } else {
            double [] vector = new double[m.getHeight()];
            double num;
            for (int i = 0; i < m.getHeight(); i++) {
                num = 0.0;
                for (int j = 0; j < m.getWidth(); j++) {
                    num += m.get(i, j) * v.get(j);
                }
                vector[i] = num;
            }
            return new Vector(vector);
        }
    }
    /**
     * Adds two matricies together
     * @param  m1 first matrix to add to second
     * @param  m2 second matrix to add to first
     * @return    a matrix representation of the sum
     * @throws IllegalOperandException
     */
    public static Matrix matrixAdd(Matrix m1, Matrix m2)
        throws IllegalOperandException {
        if (m1.getWidth() != m2.getWidth() || m1.getHeight()
            != m2.getHeight()) {
            String error = "In order to add two matricies, they must be the "
                + "same height and width, the first matrix entered was "
                + m1.getHeight() + "x" + m1.getWidth() + "and the second"
                + " matrix entered was " + m2.getHeight() + "x"
                + m2.getWidth() + ".";
            throw new IllegalOperandException(error);
        } else {
            double [][] matrix = new double[m1.getHeight()][m1.getWidth()];
            for (int i = 0; i < m1.getHeight(); i++) {
                for (int j = 0; j < m1.getWidth(); j++) {
                    matrix[i][j] = m1.get(i, j) + m2.get(i, j);
                }
            }
            return new Matrix(matrix);
        }
    }
    /**
     * Returns the dot product of two vectors
     * @param  v1 Input Vector 1
     * @param  v2 Input vector 2
     * @return    the double value of the dot product of v1 and v2
     * @throws IllegalOperandException
     */
    public static double dotProduct(Vector v1, Vector v2)
        throws IllegalOperandException {
        if (v1.getLength() != v2.getLength()) {
            String error = "In order to get the dot product of two vectors, "
                + "they need to have the same length. The first vector entered"
                + "has a length of " + v1.getLength() + " and the second "
                + "vector entered has a length of " + v2.getLength() + ".";
            throw new IllegalOperandException(error);
        } else {
            double product = 0.0;
            for (int i = 0; i < v1.getLength(); i++) {
                product += v1.get(i) * v2.get(i);
            }
            return product;
        }
    }
    /**
     * Adds two vectors together
     * @param  v1 vector to add
     * @param  v2 vector to add
     * @return    returns the sume of v1 and v2
     * @throws IllegalOperandException
     */
    public static Vector vectorAdd(Vector v1, Vector v2)
        throws IllegalOperandException {
        if (v1.getLength() != v2.getLength()) {
            String error = "In order to do vector addition, the two vectors "
                + "must be the same length. The first vector entered has a "
                + "length of " + v1.getLength() + " and the second vector "
                + "entered has a length of " + v2.getLength() + ".";
            throw new IllegalOperandException(error);
        } else {
            double [] vector = new double[v1.getLength()];
            for (int i = 0; i < v1.getLength(); i++) {
                vector[i] = v1.get(i) + v2.get(i);
            }
            return new Vector(vector);
        }
    }
}