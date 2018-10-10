package hw3;

public class SellerM extends Seller
{
    private Object lock;

    public SellerM(Seat[][] aSeat, String aSellerId, Object aLock)
    {
        super(aSeat, randomVar.nextInt(3) + 2, aSellerId, aLock, System.currentTimeMillis());
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
               

            // looks at seat for the customer this case of Seller M 
            boolean checker = true;
            int count = 1;

            Seat aSeat = null;


            synchronized(lock)
            {
                update();
                if(currentTime  >= (aCustomer.getArrivalTime()))
                {
                    assignSeat:
                    for(int i = 5; i >= 0 && i < seating.length;)
                    {
                        for (int j = 0; j < seating[0].length; j++)
                        {
                            if (seating[i][j].isSeatEmpty())
                            {
                                int seatNumber = (i*10)+j+1;
                                aSeat = new Seat(seatNumber);
                                super.assignSeat(aCustomer, aSeat, i, j);
                                printMsg(aCustomer, aSeat);
                                customerQueue.remove();
                                break assignSeat;
                            }
                        }
                        if(checker == true)
                        {
                            i = i + count;
                            checker = false;
                        }
                        else
                        {
                            i = i - count;
                            checker = true;
                        }
                        count++;
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