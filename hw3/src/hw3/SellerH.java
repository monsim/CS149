package hw3;

public class SellerH extends Seller
{
    private Object lock;
    
    public SellerH(Seat[][] seat, String sellerID, Object lock) {
        super(seat, randomVariable.nextInt(2) + 1, sellerID, lock, System.currentTimeMillis());	//1 or 2 minutes to sell ticket
        this.lock = lock;
    }

    //method to sell a ticket to a customer and assign the customer to a seat
    public void sell() {
        while (!customerQueue.isEmpty()) {
            Customer customer = null;
            if (customerQueue.isEmpty()) return;
            update();
            if(currentTime < 60)
                customer = customerQueue.peek();
            else
                return;
            Seat seat = null;
            synchronized(lock) {
                update();
                if(currentTime  >= (customer.getArrivalTime())) {
                    assignSeat:
                    for (int i = 0; i < seating.length; i++) {
                        for (int j = 0; j < seating[0].length; j++) {
                        		//if the seat is empty, give it to a Customer
                            if (seating[i][j].isSeatEmpty()) {
                                int seatNum = (i*10)+j+1;
                                seat = new Seat(seatNum);
                                super.assignSeat(customer, seat, i, j);
                                printMsg(customer, seat);
                                customerQueue.remove();
                                break assignSeat;		//don't need to continue to find seats
                            }
                        }
                    }
                }
            }
            if(seat != null) {
                try {
                    Thread.sleep(serviceTime * 1000);
                    update();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
