package hw3;

public class Seat
{
    private int seatNumber;
    private Customer seatTaken;

    /**
     * Constructor
     * @param int num initializes seat number
     */
    public Seat(int seatNumber)
    {
        this.seatNumber = seatNumber;
        seatTaken = null;
    }

    /**
     * Assigns Customer to a seat
     * @param Customer customer customer to assign
     */
    public void assignSeat(Customer customer)

    {
        seatTaken = customer;
    }

    /**
     * Gets the seatNumber
     * @return int seatNumber
     */
    public int getSeatNumber() {
        return seatNumber;
    }

    /**
     * Gets the Customer seated in the seat
     * @return seatTaken
     */
    public Customer getCustomer(){
        return seatTaken;
    }
    
    /**
     * Checks if seat us empty
     * @return boolean returns true if empty else false
     */
    public boolean isSeatEmpty()
    {
        return seatTaken == null;
    }
}