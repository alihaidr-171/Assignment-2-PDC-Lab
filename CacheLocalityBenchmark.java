
public class CacheLocalityBenchmark
{
    static final int N = 2000;
    double[][] matrix = new double[N][N];

    public double traverseRowMajor()
    {
        double sum = 0;

        for(int i = 0; i < N; i++)
        {
            for(int j = 0; j < N; j++)
            {
                sum += matrix[i][j];
            }
        }

        return sum;
    }

    public double traverseColumnMajor()
    {
        double sum = 0;

        for(int j = 0; j < N; j++)
        {
            for(int i = 0; i < N; i++)
            {
                sum += matrix[i][j];
            }
        }

        return sum;
    }

    public static void main(String[] args)
    {
        CacheLocalityBenchmark obj = new CacheLocalityBenchmark();

        for(int i = 0; i < N; i++)
        {
            for(int j = 0; j < N; j++)
            {
                obj.matrix[i][j] = i + j;
            }
        }

        double rowSum = 0;
        long start = System.nanoTime();

        for(int i = 0; i < 10; i++)
        {
            rowSum += obj.traverseRowMajor();
        }

        long rowTime = System.nanoTime() - start;

        double columnSum = 0;
        start = System.nanoTime();

        for(int i = 0; i < 10; i++)
        {
            columnSum += obj.traverseColumnMajor();
        }

        long columnTime = System.nanoTime() - start;

        double rowAverage = rowTime / 10.0 / 1000000.0;
        double columnAverage = columnTime / 10.0 / 1000000.0;

        double speedup = (double) columnTime / rowTime;

        System.out.println("Matrix Size: " + N + " x " + N);
        System.out.println("Iterations: 10");
        System.out.println("Row-Major Average Time: " + rowAverage + " ms");
        System.out.println("Column-Major Average Time: " + columnAverage + " ms");
        System.out.println("Row-Major Speedup: " + speedup + "x");
        System.out.println("Row Sum: " + rowSum);
        System.out.println("Column Sum: " + columnSum);
    }
}
