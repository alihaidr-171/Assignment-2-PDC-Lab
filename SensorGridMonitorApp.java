import java.util.Random;
import java.util.concurrent.*;

public class SensorGridMonitorApp
{
    public static void main(String[] args) throws Exception
    {
        int n = 50000;
        double[] sensorData = new double[n];
        Random random = new Random();

        for(int i = 0; i < n; i++)
            sensorData[i] = 10 + random.nextDouble() * 90;

        int workers = 4;
        int chunkSize = n / workers;

        Future<Double>[] minResults = new Future[workers];
        Future<Double>[] maxResults = new Future[workers];

        ExecutorService executor =
            Executors.newFixedThreadPool(workers);

        long startTime = System.nanoTime();

        for(int i = 0; i < workers; i++)
        {
            int start = i * chunkSize;
            int end = start + chunkSize;

            minResults[i] = executor.submit(() -> new ParallelMinSearch( sensorData, start, end).processGrid());

            maxResults[i] = executor.submit(() -> new ParallelMaxSearch(sensorData, start, end).processGrid());
        }

        double min = Double.MAX_VALUE;
        double max = -Double.MAX_VALUE;

        for(int i = 0; i < workers; i++)
        {
            min = Math.min(min, minResults[i].get());
            max = Math.max(max, maxResults[i].get());
        }

        long endTime = System.nanoTime();

        executor.shutdown();

        System.out.println("Sensor Readings: " + n);
        System.out.println("Worker Threads: " + workers);
        System.out.println("Minimum Temperature: " + min);
        System.out.println("Maximum Reading: " + max);
        System.out.println("Execution Time: " + (endTime - startTime) / 1000000.0 + " ms");
    }
}