
import java.util.Random;
import java.util.concurrent.ForkJoinPool;

public class ForkJoinPortfolioApp
{
    public static void main(String[] args)
    {
        int n = 10000;
        double[] assets = new double[n];
        Random random = new Random();

        for(int i = 0; i < n; i++)
            assets[i] = 500 + random.nextDouble() * 1500;

        double riskCap = 1500;
        double serialRisk = 0;

        long start = System.nanoTime();

        for(int i = 0; i < n; i++)
            serialRisk += assets[i];

        long serialTime = System.nanoTime() - start;

        ForkJoinPool pool = new ForkJoinPool();

        start = System.nanoTime();

        double parallelRisk = pool.invoke( new ParallelRiskCalculator(assets, 0, n) );

        long parallelTime = System.nanoTime() - start;

        start = System.nanoTime();

        pool.invoke(new PortfolioThresholdTask(assets, 0, n, riskCap));

        long stressTime = System.nanoTime() - start;

        double adjustedRisk = 0;

        for(int i = 0; i < n; i++)
            adjustedRisk += assets[i];

        System.out.println("Number of Assets: " + n);
        System.out.println("Sequential Risk: " + serialRisk);
        System.out.println("Sequential Time: " + serialTime / 1000000.0 + " ms");

        System.out.println("Parallel Risk: " + parallelRisk);
        System.out.println("Parallel Time: " + parallelTime / 1000000.0 + " ms");

        System.out.println("Adjusted Risk After Stress Test: " + adjustedRisk);

        System.out.println("Stress Test Time: " + stressTime / 1000000.0 + " ms");

        System.out.println("Total Parallel Task Time: " + (parallelTime + stressTime) / 1000000.0 + " ms");

        if(parallelTime > 0)
            System.out.printf("Speedup Ratio: %.2fx%n", (double) serialTime / parallelTime);

        pool.shutdown();
    }
}
