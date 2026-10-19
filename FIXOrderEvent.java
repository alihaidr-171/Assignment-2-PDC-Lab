public class FIXOrderEvent extends TradeEvent
{
    private String orderId;

    public FIXOrderEvent(String senderId, long timestamp, double baseprice, String orderId) 
    {
        super(senderId, timestamp, baseprice);
        this.orderId = orderId;
    }

    @Override 
    public void dispatch() 
    {
        double fee = baseprice * 0.0015;
        System.out.println("FIX Stock purchase Event");
        System.out.println("Sender ID: " + senderId);
        System.out.println("Timestamp: " + timestamp);
        System.out.println("Base Price: " + baseprice);
        System.out.println("Fee: " + fee);
        System.out.println("Order ID: " + orderId);
    }
    
}
