
import java.util.*;
import java.util.concurrent.*;

public class InventoryCatalogManager
{
    private Map<String, Integer> syncMap;
    private ConcurrentHashMap<String, Integer> concurrentMap;

    public void benchmarkSynchronizedMap() throws InterruptedException
    {
        syncMap = Collections.synchronizedMap(new HashMap<String, Integer>());

        for(int i = 0; i < 100; i++)
            syncMap.put("P" + i, 100);

        ExecutorService pool = Executors.newFixedThreadPool(10);

        long start = System.nanoTime();

        for(int t = 0; t < 10; t++)
        {
            pool.submit(() ->
            {
                for(int i = 0; i < 5000; i++)
                {
                    String id = "P" + (i % 100);

                    synchronized(syncMap)
                    {
                        int stock = syncMap.get(id);
                        syncMap.put(id, stock + 1);
                    }
                }
            });
        }

        pool.shutdown();
        pool.awaitTermination(1, TimeUnit.HOURS);

        long end = System.nanoTime();

        double seconds = (end - start) / 1_000_000_000.0;

        System.out.println("SynchronizedMap Time: " + seconds + " seconds");

        System.out.println("SynchronizedMap Throughput: " + (100000.0 / seconds) + " operations/second");
    }

    public void benchmarkConcurrentMap() throws InterruptedException
    {
        concurrentMap = new ConcurrentHashMap<String, Integer>();

        for(int i = 0; i < 100; i++)
            concurrentMap.put("P" + i, 100);

        ExecutorService pool = Executors.newFixedThreadPool(10);

        long start = System.nanoTime();

        for(int t = 0; t < 10; t++)
        {
            pool.submit(() ->
            {
                for(int i = 0; i < 5000; i++)
                {
                    String id = "P" + (i % 100);

                    concurrentMap.compute(id,
                        (key, stock) -> stock + 1);
                }
            });
        }

        pool.shutdown();
        pool.awaitTermination(1, TimeUnit.HOURS);

        long end = System.nanoTime();

        double seconds = (end - start) / 1_000_000_000.0;

        System.out.println("ConcurrentHashMap time: " + seconds + " seconds");

        System.out.println("ConcurrentHashMap Throughput: " + (100000.0 / seconds) + " operations/second");
    }

    public static void main(String[] args) throws InterruptedException
    {
        InventoryCatalogManager manager = new InventoryCatalogManager();

        manager.benchmarkSynchronizedMap();
        manager.benchmarkConcurrentMap();
    }
}
