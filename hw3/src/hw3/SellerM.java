package hw3;

public class SellerM extends Seller{
    private Object lock;

    public SellerM(Seat[][] seat, String sellerID, Object lock)
    {
        super(seat, randomVariable.nextInt(3) + 2, sellerID, lock, System.currentTimeMillis());
        this.lock = lock;
    }

    public void sell()
    {
        while (!customerQueue.isEmpty())
        {
            //Object lock = new Object();
            Customer customer;
            if (customerQueue.isEmpty())
                return;
            // Get customer in queue that is ready


            update(); // get the current time
            if(currentTime <= 59)
                customer = customerQueue.peek();
            else
                return;

            // Find seat for the customer
            // Case for Seller M
            boolean flag = true;
            int counter = 1;

            Seat seat = null;

            //System.out.println(currentTime);

            synchronized(lock)
            {
                update();
                //System.out.println("got in");
                if(currentTime  >= (customer.getArrivalTime()))
                {
                    find_seat:
                    for(int i = 5; i >= 0 && i < seating.length;)
                    {
                        for (int j = 0; j < seating[0].length; j++)
                        {
                            if (seating[i][j].isSeatEmpty())
                            {
                                // Assign seat to customer
                                // Seat number = (Row x 10) + (Col + 1)
                                int seatNum = (i*10)+j+1;
                                seat = new Seat(seatNum);
                                super.assignSeat(customer, seat, i, j);
                                //update();
                                printMsg(customer, seat);
                                customerQueue.remove();
                                break find_seat;
                            }
                        }
                        if(flag == true)
                        {
                            i += counter;
                            flag = false;
                        }
                        else
                            {
                            i -= counter;
                            flag = true;
                        }
                        counter++;
                    }
                }
            }
            if(seat != null){
                try {
                    Thread.sleep(serviceTime * 1000);
                    update();
                } catch (InterruptedException e)
                {
                    e.printStackTrace();
                }
            }


        }
    }


}