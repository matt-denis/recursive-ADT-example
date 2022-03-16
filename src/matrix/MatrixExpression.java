package matrix;

import java.util.Arrays;
import java.util.Objects;


public interface MatrixExpression {

    public static final MatrixExpression I = new Identity();

    boolean isIdentity();

    MatrixExpression scalars();

    MatrixExpression matrices();

    MatrixExpression optimize();

    public static MatrixExpression make(double value) {
        return new Scalar(value);
    }

    public static MatrixExpression make(double[][] array) {
        return new Matrix(array);
    }
}

final class Identity implements MatrixExpression {

    @Override
    public boolean isIdentity() { return true; }

    @Override
    public MatrixExpression scalars() { return this; }

    @Override
    public MatrixExpression matrices() { return this; }

    @Override
    public MatrixExpression optimize() { return this; }

    @Override
    public String toString() { return "Id"; }

    @Override
    public boolean equals(Object o) {
        return (o instanceof Identity); // singleton
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(I); // singleton
    }
}

class Scalar implements MatrixExpression {

    private final double value;

    public Scalar(double value) {
        this.value = value;
    }

    @Override
    public boolean isIdentity() { return value == 1; }

    @Override
    public MatrixExpression scalars() { return this; }

    @Override
    public MatrixExpression matrices() { return I; }

    @Override
    public MatrixExpression optimize() { return this; }

    @Override
    public String toString() {
        return Objects.toString(value);
    }

    @Override 
    public boolean equals(Object o) {
        if (!(o instanceof Scalar)) return false;
        Scalar that = (Scalar) o;
        return this.value == that.value;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(value);
    }
}

class Matrix implements MatrixExpression {

    // RI: array.length > 0, and all array[i] are equal nonzero length
    private final double[][] array;
    private final int dim1;
    private final int dim2;

    public Matrix(double[][] array) {
        int rowSize = 0;
        if (array.length > 0) {
            rowSize = array[0].length;
        } else { throw new IllegalArgumentException("dimensions of the matrix must be nonzero"); }
        this.array = new double[array.length][];
        for (int i = 0; i < array.length; i++) {
            if (array[i].length != rowSize) throw new IllegalArgumentException();
            this.array[i] = Arrays.copyOf(array[i], array[i].length);
        }
        dim1 = this.array.length;
        dim2 = this.array[0].length;
    }

    @Override
    public boolean isIdentity() {
        for (int row = 0; row < array.length; row++) {
            for (int col = 0; col <= row; col++) {
                if (row != col && (array[row][col] != 0 || array[col][row] != 0))
                    return false;
                else if (array[row][col] != 1) return false;
            }
        }
        return true;
    }

    public int dim1() { return dim1; }

    public int dim2() { return dim2; }

    @Override
    public MatrixExpression scalars() { return I; }

    @Override
    public MatrixExpression matrices() { return this; }

    @Override
    public MatrixExpression optimize() { return this; }

    @Override
    public String toString() {
        StringBuilder buffer = new StringBuilder();
        for (int row = 0; row < array.length; row++) {
            buffer.append("| ");
            for (int col = 0; col < array[row].length; col++) {
                buffer.append(array[row][col] + " ");
            }
            buffer.append(" |\n");
        }
        return buffer.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Matrix)) return false;
        Matrix that = (Matrix) o;
        if (this.array.length != that.array.length) return false;
        for (int row = 0; row < array.length; row++) {
            if (array[row].length != that.array[row].length) return false;
            for (int col = 0; col <= array[row].length; col++) {
                if (array[row][col] != that.array[row][col]) return false;
            }
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        for (double[] row : array) {
            hash = hash + 37 * Arrays.hashCode(row);
        }
        return hash;
    }
}

class Product implements MatrixExpression {

    private final MatrixExpression m1;
    private final MatrixExpression m2;

    public Product(MatrixExpression m1, MatrixExpression m2) {
        this.m1 = m1;
        this.m2 = m2;
    }

    @Override
    public boolean isIdentity() {
        return m1.isIdentity() && m2.isIdentity();
    }

    @Override
    public MatrixExpression scalars() { return times(m1.scalars(), m2.scalars()); }

    @Override 
    public MatrixExpression matrices() { return times(m1.matrices(), m2.matrices()); }

    
    public MatrixExpression times(MatrixExpression m1, MatrixExpression m2) {
        return new Product(m1, m2);
    }

    @Override
    public MatrixExpression optimize() { 
        return times(scalars(), matrices());
    }

    @Override 
    public String toString() {
        return m1.toString() + " " + m2.toString();
    }

}
