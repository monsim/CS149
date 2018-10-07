package hw3;

public class Seat
{

    private int seatNumber;
    private Customer seatTaken;

    /**
     * Constructor
     * @param int num sets seat number
     */
    public Seat(int num)
    {
        seatNumber = num;
        seatTaken = null;
    }

    /**
     * Assigns a Customer to this Seat
     * @param Customer c - customer to assign
     */
    public void assignSeat(Customer c)

    {
        seatTaken = c;
    }

    /**
     * Checks if this Seat is taken
     * @return boolean - true if seat empty else false 
     */
    public boolean isSeatEmpty()
    {
        return seatTaken == null;
    }

    /**
     * Getter method to access seat number
     * @return int seatNumber
     */
    public int getSeatNumber() {
        return seatNumber;
    }

    /**
     * Getter method to access Customer assigned to this seat
     * @return seatTaken
     */
    public Customer getCustomer(){
        return seatTaken;
    }
}