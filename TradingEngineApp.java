public class TradingEngineApp
{
    public static void main(String[] args)
    {
        TradeEvent event1 = new FIXOrderEvent(
            "FIX-GW-01",
            System.currentTimeMillis(),
            10000.0,
            "ORD-101"
        );

        TradeEvent event2 = new ReutersQuoteEvent(
            "REUTERS-GW-02",
            System.currentTimeMillis(),
            150.0,
            149.80,
            150.20
        );

        System.out.println("=== FIX ORDER EVENT ===");
        event1.dispatch();

        System.out.println();

        System.out.println("=== REUTERS QUOTE EVENT ===");
        event2.dispatch();
    }
}