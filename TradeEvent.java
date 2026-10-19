public class TradeEvent 
{
    protected String senderId;
    protected long timestamp;
    protected double baseprice;

    public TradeEvent(String senderId, long timestamp, double baseprice) 
    {
        this.senderId = senderId;
        this.timestamp = timestamp;
        this.baseprice = baseprice;
    }

    public void dispatch() 
    {
        System.out.println("Generic Trade Event");
        System.out.println("Sender ID: " + senderId);
        System.out.println("Timestamp: " + timestamp);
        System.out.println("Base Price: " + baseprice);
    }
    
}
