public class PacketSymmetryAuditor
{
    public static void main(String[] args)
    {
        String payload = "ABCCBA";

        TransactionStack stack = new TransactionStack(payload.length());

        RequestQueue queue =  new RequestQueue(payload.length());

        for(int i = 0; i < payload.length(); i++)
        {
            stack.push(payload.charAt(i));
            queue.enqueue(payload.charAt(i));
        }

        boolean palindrome = true;

        while(!stack.isEmpty() && !queue.isEmpty())
        {
            char a = stack.pop();
            char b = queue.dequeue();

            if(a != b)
            {
                palindrome = false;
                break;
            }
        }

        System.out.println("Packet Payload: " + payload);

        if(palindrome)
            System.out.println("Packet is Symmetrical");
        else
            System.out.println("Packet is Not Symmetrical");
    }
}