package hw3;

import java.util.Random;

public class Customer implements Comparable<Customer> {

	private int currentTime;
	private int arrivalTime;
	Random randomGen = new Random();
	private String ticket;
	private int seatNumber;
	private int customerID;

	/**
	 * Sets customer id and generates random arrival time for testing of this
	 * program
	 * 
	 * @param int
	 *            customerID
	 */
	public Customer(int customerID) {
		arrivalTime = randomGen.nextInt(60); // arrives between 0 and 60 minutes
		this.customerID = customerID;
		seatNumber = -1; // not seated originally
	}

	/**
	 * returns arrival time
	 * 
	 * @return arrival time
	 */
	public int getArrivalTime() {
		return this.arrivalTime;
	}

	/**
	 * returns the Customer's id
	 * 
	 * @return customer id
	 */
	public int getCustomerID() {
		return customerID;
	}

	/**
	 * sets the Customer's id
	 * 
	 * @param customerID customer id
	 */
	public void setCustomerID(int customerID) {
		this.customerID = customerID;
	}

	/**
	 * returns the time
	 * 
	 * @return time
	 */
	public int getTime() {
		return currentTime;
	}

	/**
	 * sets the time
	 * 
	 * @param time to set to
	 */
	public void setTime(int time) {
		this.currentTime = time;
	}

	/**
	 * gets ticket info
	 * 
	 * @return ticker info
	 */
	public String getTicket() {
		return ticket;
	}

	/**
	 * assigns ticket to Customer
	 * 
	 * @param ticket
	 */
	public void setTicket(String ticket) {
		this.ticket = ticket;
	}

	/**
	 * assigns seat to Customer
	 * 
	 * @param seat number to assign
	 */
	public void setSeatNumber(int seatNum) {
		this.seatNumber = seatNum;
	}

	/**
	 * returns the seat number assigned to this customer
	 * 
	 * @return int seatNum
	 */
	public int getSeatNumber() {
		return this.seatNumber;
	}

	/**
	 * returns whether the Customer is seated or not
	 * 
	 * @return true if they're seated, false if not
	 */
	public boolean isSeated() {
		if (seatNumber == -1)
			return false;
		else
			return true;
	}

	/**
	 * compares Customers based on their arrival times (used for queue)
	 */
	@Override
	public int compareTo(Customer customer) {
		if (this.arrivalTime < customer.arrivalTime)
			return -1;
		else if (this.arrivalTime > customer.arrivalTime)
			return 1;
		else
			return 0;
	}
}