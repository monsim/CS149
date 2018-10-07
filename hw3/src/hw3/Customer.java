package hw3;

import java.util.Random;

public class Customer implements Comparable<Customer>
{
    Random random = new Random();
    private int arrivalTime;
    private int seatNum;
    private int customerID;
    private String ticket;
    private int time;

    /**
     * Getter method to access time
     * @return int time
     */
    public int getTime() {
        return time;
    }

    /**
     * Setter method for time
     * @param int time to set this.time
     */
    public void setTime(int time) {
        this.time = time;
    }

    /**
     * Getter method to access ticket
     * @return String ticket
     */
    public String getTicket() {
        return ticket;
    }

    /**
     * Setter method for time
     * @param String ticket to set this.ticket
     */
    public void setTicket(String ticket) {
        this.ticket = ticket;
    }

    /**
     * Sets customer id and generates random arrival time for testing of this program
     * @param int customerID
     */
    public Customer(int customerID){
        arrivalTime = random.nextInt(60);
        this.customerID = customerID;
        seatNum = -1;
    }

    /**
     * Getter method to arrival time
     * @return int arrivalTime
     */
    public int getArrivalTime(){
        return this.arrivalTime;
    }
    
    /**
     * Setter method for seat number
     * @param int seatNum to set this.seatNum
     */
    public void setSeatNum(int seatNum){
        this.seatNum = seatNum;
    }

    /**
     * Checks to see if a seat number is has been assigned to a customer
     * @return false if seat number is not assigned, else true
     */
    public boolean isSigned()
    {
        if(seatNum== -1)  return false;
        else return true;
    }
    
    /**
     * Getter method to access seat number
     * @return int seatNum
     */
    public int getSeatNum(){
        return this.seatNum;
    }

    /**
     * Getter method to access customer's id number
     * @return int customerID
     */
    public int customerID(){
        return this.customerID;
    }

    /**
     * Compares arrival time of 2 customers
     */
    @Override
    public int compareTo(Customer customer)
    {
        if(this.arrivalTime < customer.arrivalTime)
            return -1;
        else if(this.arrivalTime > customer.arrivalTime)
            return 1;
        else
            return 0;
    }
}