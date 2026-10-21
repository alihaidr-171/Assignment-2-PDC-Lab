public class AsyncWebEngineApp
{
    public static void main(String[] args)
    {
        System.out.println("Main Thread: " + Thread.currentThread().getName());

        TelemetryThread telemetry = new TelemetryThread();
        telemetry.setName("telemetry worker");

        LogProcessorTask logTask = new LogProcessorTask();
        Thread logThread = new Thread(logTask);
        logThread.setName("Log worker");

        System.out.println("starting worker threads");

        telemetry.start();
        logThread.start();

        System.out.println("worker threads started.");
    }
}