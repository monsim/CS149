package hw3;

public class SellerL extends Seller
{

    private Object lock;
    public SellerL(Seat[][] aSeat, String aSellerID, Object aLock)
    {
        super(aSeat, randomVar.nextInt(4) + 4, aSellerID, aLock, System.currentTimeMillis());
        this.lock = aLock;

    }

    public void sell() 
    {
        while (!customerQueue.isEmpty())
        {
        		//will get the customer in queue that is ready 
            Customer aCustomer;
            if (customerQueue.isEmpty())
            {
            	return;
            }
            
            //will get the current time
            update();
            if(currentTime < 60)
            {
                aCustomer = customerQueue.peek();
            }
            else
            {
                return;
            }

            // looks at seat for the customer this cse of Seller L
            Seat aSeat = null;

            synchronized(lock)
            {
                update();
                //System.out.println("got in");
                if(currentTime  >= (aCustomer.getArrivalTime()))
                {
                    assignSeat:
                    for (int i = seating.length-1; i >= 0; i--) 
                    {
                        for (int j = 0; j < seating[0].length; j++)
                        {
                            if (seating[i][j].isSeatEmpty())
                            {
              
                                int seatNumber = (i * 10) + j + 1;
                                aSeat = new Seat(seatNumber);
                                super.assignSeat(aCustomer, aSeat, i, j);
                                printMsg(aCustomer, aSeat);
                                customerQueue.remove();
                                break assignSeat;
                            }
                        }
                    }
                }
            }
            if(aSeat != null)
            {
                try 
                {
                    Thread.sleep(serviceTime * 1000);
                    update();
                } catch (InterruptedException a)
                {
                    a.printStackTrace();
                }
            }

        }
    }
}