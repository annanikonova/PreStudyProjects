package com.nixsolutions.project1.task1;

import java.text.DecimalFormat;

/**
 * Class for storing and manipulating matrixes.
 * Each matrix encapsulates 2-dimension double array.
 * The first dimension is a rows number.
 * The second dimension is a columns number.
 *
 * @author annnikon
 * @version 1.0
 */
public class Matrix {
    /**
     * Constant that defines a default rows number for matrix.
     */
    public static final int DEFAULT_ROWS = 2;
    /**
     * Constant that defines a default columns numberfor matrix.
     */
    public static final int DEFAULT_COLUMNS = 2;

    /**
     * Private array storage for matrix values.
     */
    private double[][] array;

    /**
     * Public constructor for building new matrix from existing array.
     *
     * @param array 2-dimention array of double values.
     */
    public Matrix(double[][] array) {

        this.array = array;
    }

    /**
     * Private constructor, which creates an array of given size for storing.
     * Used only inside this class methods.
     *
     * @param rows    number of rows (first dimension), positive value.
     * @param columns number of columnss (second dimension), positive value.
     */
    private Matrix(int rows, int columns) {
        int m = (rows > 0) ? rows : DEFAULT_ROWS;
        int n = (columns > 0) ? columns : DEFAULT_COLUMNS;
        this.array = new double[m][n];
    }

    /**
     * Default constructor creates matrix of default sizes and fills it by zeroes.
     */
    public Matrix() {
        this.array = new double[DEFAULT_ROWS][DEFAULT_COLUMNS];
    }

    /**
     * Shows number of rows in array - the first dimension of a matrix.
     *
     * @return number of rows.
     */
    public int getRows() {
        return this.array.length;
    }

    /**
     * Shows number of columns in array - the second dimension of a matrix.
     *
     * @return number of columns.
     */
    public int getColumns() {
        return this.array[0].length;
    }

    /**
     * Sets a new value to the element by its indexes.
     *
     * @param rowNum number of row
     * @param colNum number of column
     * @param value  double value of element
     */
    public void setElement(int rowNum, int colNum, double value) {
        this.array[rowNum][colNum] = value;
    }

    /**
     * Gets value of the element by its indexes.
     *
     * @param rowNum number of row
     * @param colNum number of column
     * @return value of the element by given indexes
     */
    public double getElement(int rowNum, int colNum) {
        return this.array[rowNum][colNum];
    }

    /**
     * Adds matrixes.
     * <p><b> To be able to add two matrices, they must be the same size.</b> </p>
     *
     * @param m2 the second matrix which values will be added to the current matrix.
     * @return new matrix which is a result of adding two matrixes, null if operation is impossible.
     */
    public Matrix add(Matrix m2) {
        if (!this.isSameSize(m2)) {
            System.out.println("Adding Matrixes of different sizes is not allowed.");
            return null;
        }
        Matrix result = new Matrix(getRows(), getColumns());

        for (int i = 0; i < getRows(); i++) {
            for (int j = 0; j < getColumns(); j++) {
                double value = this.getElement(i, j) + m2.getElement(i, j);
                result.setElement(i, j, value);

            }
        }

        return result;
    }

    /**
     * Reverts matrix dimensions so that rows become columns,
     * and columns become rows.
     * Used in many math operations.
     *
     * @return new matrix which is a result or reverting of the current one.
     */
    public Matrix transponate() {
        Matrix result = new Matrix(this.getColumns(), this.getRows());
        for (int i = 0; i < getColumns(); i++) {
            for (int j = 0; j < getRows(); j++) {
                double value = this.getElement(j, i);
                result.setElement(i, j, value);

            }
        }
        return result;

    }

    /**
     * Multiplies current matrix to another one.
     * <p><b>Matrixes can be multiplied only if they are coherent.</b></p>
     * <p>If A is an <i> m * n </i> matrix and B is <i> n * p </i> matrix,</p>
     * <p>their matrix product AB is an <i>m * p </i> matrix, </p>
     * <p>in which the n entries across a row of A are multiplied </p>
     * <p> with the n entries down a columns of B, </p>
     * <p> and summed to produce an entry of AB. </p>
     *
     * @param m2 another matrix which will be multiplied for
     * @return new matrix with m*n size, or null if operation is impossible
     */
    public Matrix multiply(Matrix m2) {
        if (!isCoherent(m2)) {
            System.out.println("Matrixes should be coherent.");
            return null;
        }
        int newRowsNumber = this.getRows();
        int newColumnsNumber = m2.getColumns();
        Matrix result = new Matrix(newRowsNumber, newColumnsNumber);
        for (int i = 0; i < newRowsNumber; i++) {
            for (int j = 0; j < newColumnsNumber; j++) {
                double sum = 0;
                for (int r = 0; r < this.getColumns(); r++) {
                    sum += this.array[i][r] * m2.array[r][j];
                }
                result.setElement(i, j, sum);
            }
        }

        return result;

    }

    /**
     * Shows if the current matrix has the same number of rows
     * and columns as the another one.
     *
     * @param m2 another matrix for comporating
     * @return true if rows and columns numbers of both matrixes are equal, else false
     */
    public boolean isSameSize(Matrix m2) {
        return (m2.getRows() == this.getRows()
                && m2.getColumns() == this.getColumns());
    }

    /**
     * Shows if the current matrix has the same number of columns,
     * as the number of rows in another one.
     * Coherent matrixes can be multiplied.
     *
     * @param m2 another matrix for comporating
     * @return true if rmatrixes are coherent, else false
     */
    public boolean isCoherent(Matrix m2) {
        return (this.getColumns() == m2.getRows());
    }

    /**
     * Prints matrix as a formatted table,
     * where the first dimension represented as rows,
     * and the second dimension is represented as columns.
     * Uses decimal pattern for double numbers,
     * which shows 2 character after a point.
     */
    public void print() {
        DecimalFormat df = new DecimalFormat("#0.00");
        for (int i = 0; i < this.getRows(); i++) {
            for (int j = 0; j < this.getColumns(); j++) {
                System.out.print(df.format(this.getElement(i, j)));
                System.out.print("  ");
            }
            System.out.println();
        }

    }

    /**
     * Demonstrates a usage of metod {@link Matrix#add}.
     * Checks if adding returns null, and if not, prints
     * a resulting matrix.
     *
     * @param m1 the first matrix for adding
     * @param m2 the second matrix for adding
     */
    public static void testAdd(Matrix m1, Matrix m2) {
        System.out.println("\n Test adding:");
        m1.print();
        System.out.println("\n+");
        m2.print();
        System.out.println("\n=");
        Matrix result = m1.add(m2);
        if (result != null) {
            result.print();
        } else {
            System.out.println("Adding is not allowed");
        }


    }

    /**
     * Demonstrates a usage of metod {@link Matrix#transponate}.
     * Checks if adding returns null, and if not, prints
     * a resulting matrix.
     *
     * @param matrix the matrix that should be transponated
     */
    public static void testTransponate(Matrix matrix) {
        System.out.println("\n Test transponating:");
        matrix.print();
        System.out.println("\nTransponated:");
        Matrix result = matrix.transponate();
        if (result != null) {
            result.print();
        } else {
            System.out.println("Error transponating");
        }
    }

    /**
     * Demonstrates a usage of metod {@link Matrix#multiply}.
     * Checks if multiplying returns null, and if not, prints
     * a resulting matrix.
     *
     * @param m1 the first matrix for multiplying
     * @param m2 the second matrix for multiplying
     */
    public static void testMultiply(Matrix m1, Matrix m2) {

        System.out.println("\nTest multiplying:");
        m1.print();
        System.out.println("\n*");
        m2.print();
        System.out.println("\n=");
        Matrix result = m1.multiply(m2);
        if (result != null) {
            result.print();
        } else {
            System.out.println("Multiplying is not allowed");
        }

    }


    /**
     * Creates 3 examples of double arrays and matrixes based on them.
     * Tests adding ({@link Matrix#add(Matrix)}),
     * transponating ({@link Matrix#transponate}),
     * and multiplying ({@link Matrix#multiply(Matrix)})
     *
     * @param args arguments of a command line
     */
    public static void main(String[] args) {
        double[][] arrayA = new double[][]
                {{1.3, 2.4, 7.2333},
                        {2.5, -3.36, 6.986}};
        double[][] arrayB = new double[][]
                {{-1.6, 7.4, -1.987},
                        {-2.5, 8.98, -8.09978}};
        double[][] arrayC = new double[][]
                {{-1.6, 7.4, 7, 7},
                        {-2.5, 8.98, 6, 7},
                        {1, 2, 3, 4}};
        Matrix matrixA = new Matrix(arrayA);
        Matrix matrixB = new Matrix(arrayB);
        Matrix matrixC = new Matrix(arrayC);
        testAdd(matrixA, matrixB);
        testTransponate(matrixA);
        testMultiply(matrixA, matrixC); //allowed
        testMultiply(matrixC, matrixA); //not allowed

    }


}
