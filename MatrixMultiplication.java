import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

public class MatrixMultiplication {
    
    public static class MatrixTask implements Callable<int[]> {
        private final int[][] matrixA;
        private final int[][] matrixB;
        private final int row;
        private final int col;

        public MatrixTask(int[][] matrixA, int[][] matrixB, int row, int col) {
            this.matrixA = matrixA;
            this.matrixB = matrixB;
            this.row = row;
            this.col = col;
        }

        @Override
        public int[] call() throws Exception {
            int[] result = new int[matrixB[0].length];
            for (int i = 0; i < matrixB.length; i++) {
                result[col] += matrixA[row][i] * matrixB[i][col];
            }
            return result;
        }
    }

    public static int[][] multiplyMatrices(int[][] matrixA, int[][] matrixB) throws InterruptedException, ExecutionException {
        int rows = matrixA.length;
        int cols = matrixB[0].length;
        int[][] result = new int[rows][cols];

        ExecutorService executor = Executors.newFixedThreadPool(4);
        List<Future<int[]>> futures = new ArrayList<>();

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                MatrixTask task = new MatrixTask(matrixA, matrixB, i, j);
                futures.add(executor.submit(task));
            }
        }

        int taskIndex = 0;
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                result[i][j] = futures.get(taskIndex++).get()[j];
            }
        }

        executor.shutdown();
        return result;
    }

    public static void printMatrix(int[][] matrix) {
        for (int[] row : matrix) {
            for (int val : row) {
                System.out.print(val + " ");
            }
            System.out.println();
        }
    }

    public static void main(String[] args) {
        int[][] matrixA = {{1, 2}, {3, 4}};
        int[][] matrixB = {{2, 0}, {1, 2}};

        try {
            int[][] result = multiplyMatrices(matrixA, matrixB);
            System.out.println("Result of the multiplication:");
            printMatrix(result);
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
    }
}
