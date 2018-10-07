package hw3;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;

public abstract class Seller implements Runnable
{
    public Queue<Customer> customerQueue;
    protected static Random randomVariable = new Random();
    protected String sellerID;
    protected int serviceTime;
    protected int ticketNum = 1;
    protected int time = 0;

    protected long pastTime;
    protected long currentTime;

    protected Seat[][] seating;
    private Object lock;

    public Seller(Seat[][] seating, int serviceTime, String sellerID, Object lock, long pastTime)
    {
        customerQueue = new LinkedList<Customer>();
        this.serviceTime = serviceTime;
        this.seating = seating;
        this.lock = lock;
        this.sellerID = sellerID;
        this.pastTime = pastTime;

    }
    protected void callTime(Customer customer)
    {

        time = (int) (currentTime + serviceTime); //+ elapse_time;
        //System.out.println("----------------" + customer.getArrivalTime() + "---sid: " + this.sellerID + "service: " +this.serviceTime);
        customer.setTime(time);
    }

    protected void assignSeat(Customer customer, Seat seat, int i, int j)
    {
        if (ticketNum < 10)
            customer.setTicket(sellerID + "0" + ticketNum);
        else
            customer.setTicket(sellerID + ticketNum);
        callTime(customer);
        ticketNum++;
        seat.assignSeat(customer);
        seating[i][j] = seat;
    }

    protected void update()
    {

        currentTime = System.currentTimeMillis() - this.pastTime;
        if (currentTime < 1000)
            currentTime = 0;
        else
            currentTime /= 1000;
    }

    public void addCustomer(Customer c) {
        customerQueue.add(c);
    }

    public void sortQueue()
    {
        Customer[] temp = customerQueue.toArray(new Customer[customerQueue.size()]);
        customerQueue.clear();
        Arrays.sort(temp);
        for (Customer c : temp)
            customerQueue.add(c);
    }

    protected void printMsg(Customer customer, Seat seat)
    {
        int hour = customer.getTime() / 60;
        int min = customer.getTime() % 60;
        String time = "";
        if (min < 10) time = hour + ":0" + min;
        else time = hour + ":" + min;
        if (seat == null) System.out.println(time + "  " + sellerID + " - Sorry, the concert is sold out!");
        else System.out.println(time + "  " + sellerID + " - Success! Your seat is " + seat.getSeatNumber());

        printSeating(this.seating, 10, 10);
    }

    // seller thread to serve one time "quanta" á 1 minute

    public abstract void sell();

    @Override
    public void run() {
        sell();
    }

    public static void printSeating(Seat[][] seating, int maxRows, int maxCols)
    {
        System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
        for (int row = 0; row < maxRows; row++) {
            for (int col = 0; col < maxCols; col++) {
                if (seating[row][col].isSeatEmpty())
                    System.out.printf("%7s ", "-");
                else
                    System.out.printf("%7s ", seating[row][col].getCustomer().getTicket());
            }
            System.out.println();
        }
    }
}
