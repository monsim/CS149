package hw3;

import java.util.Random;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;

public abstract class Seller implements Runnable {
	public Queue<Customer> customerQueue;
	public static Random randomVar = new Random();
	public String sellerID;
	public int ticketNum = 1;
	
	public int time = 0;
	public int serviceTime;
	public long pastTime;
	public long currentTime;

	public Seat[][] seating;
	public Object lock;

	/**
	 * Constructs a Seller 'thread' 
	 * @param seating 2D array of the seat map
	 * @param serviceTime time it takes for a customer to be served
	 * @param sellerID unique id for the seller
	 * @param lock
	 * @param pastTime time that has already past
	 */
	public Seller(Seat[][] seating, int serviceTime, String sellerID, Object lock, long pastTime) {
		customerQueue = new LinkedList<Customer>();
		this.seating = seating;
		this.serviceTime = serviceTime;
		this.sellerID = sellerID;
		this.lock = lock;
		this.pastTime = pastTime;
	}

	/**
	 * Customer is assigned an empty seat
	 * @param customer
	 * @param seat
	 * @param i
	 * @param j
	 */
	protected void assignSeat(Customer customer, Seat seat, int i, int j) {
		if (ticketNum < 10)
			customer.setTicket(sellerID + "0" + ticketNum);
		else
			customer.setTicket(sellerID + ticketNum);
		updateCustomerTime(customer);
		ticketNum++;
		seat.assignSeat(customer);
		seating[i][j] = seat;
		if (sellerID.substring(0,1).equals("M")) Tester.successM++;
		if (sellerID.substring(0,1).equals("H")) Tester.successH++;
		if (sellerID.substring(0,1).equals("L")) Tester.successL++;
	}

	/**
	 * Updates the current time
	 */
	protected void update() {
		currentTime = System.currentTimeMillis() - pastTime;
		if (currentTime < 1000)
			currentTime = 0;
		else
			currentTime /= 1000;
	}
	
	/**
	 * Updates the customer time
	 * @param customer
	 */
	protected void updateCustomerTime(Customer customer) {
		time = (int) (currentTime + serviceTime); 
		customer.setTime(time);
	}
	

	/**
	 * Adds a customer to the seller's queue
	 * @param c
	 */
	public void addCustomer(Customer c) {
		customerQueue.add(c);
	}

	/**
	 * Sorts the customers in the seller's queue based on arrival time
	 */
	public void sortQueue() {
		// The queue is sorted by converting it to an array and sorted there
		Customer[] customerArray = customerQueue.toArray(new Customer[customerQueue.size()]);
		customerQueue.clear();
		Arrays.sort(customerArray);
		for (Customer c : customerArray)
			customerQueue.add(c);
	}

	/**
	 * Prints the arrival, service, and success time with a timestamp
	 * @param customer
	 * @param seat
	 */
	protected void printMsg(Customer customer, Seat seat) {
		// Arrival Time
		System.out.println(getArrivalTime(customer) + "  Customer " + customer.getCustomerID()
				+ " just arrived at seller " + this.sellerID);
		// Service Time
		System.out.println(getServiceTime(customer) + "  Service start time: Customer " + customer.getCustomerID());
		// Success Time
		if (seat == null)
			System.out.println(getSuccessTime(customer) + "  " + this.sellerID + " - Sorry, the concert is sold out!");
		else
			System.out.println(getSuccessTime(customer) + "  " + this.sellerID + " - Success! Your seat is " + seat.getSeatNumber());

		printSeating(this.seating, 10, 10);
		
		System.out.println();
		System.out.println();
	}

	/**
	 * Calculates and formats success time
	 * @param customer
	 * @return timeStampSuccess timestamp when a customer has successfully purchased a ticket
	 */
	protected String getSuccessTime(Customer customer) {
		int hour = customer.getTime() / 60;
		int min = customer.getTime() % 60;
		String timeStampSuccess = "";
		if (min < 10)
			timeStampSuccess = hour + ":0" + min;
		else
			timeStampSuccess = hour + ":" + min;
		return timeStampSuccess;
	}

	/**
	 * Calculates and formats arrival time
	 * @param customer 
	 * @return timeStampArrival timestamp when a customer arrives to the Queue
	 */
	protected String getArrivalTime(Customer customer) {
		String timeStampArrival = "";
		int hour = customer.getArrivalTime() / 60;
		if (customer.getArrivalTime() < 10)
			timeStampArrival = hour + ":0" + customer.getArrivalTime();
		else
			timeStampArrival = hour + ":" + customer.getArrivalTime();
		return timeStampArrival;
	}

	/**
	 * Calculates and formats service time
	 * @param customer
	 * @return timeStampService timestamp when a customer starts being serviced by a seller
	 */
	private String getServiceTime(Customer customer) {
		String success = getSuccessTime(customer);
		
		int hourSuccess = Integer.parseInt(success.substring(0,1)) * 60;
		int minSuccess = Integer.parseInt(success.substring(2,4));
		int totalSuccess = hourSuccess + minSuccess;
		int servedTime = Math.abs(totalSuccess - this.serviceTime);
		String timeStampService = "";
		int hour = servedTime / 60;
		if (servedTime < 10)
			timeStampService = hour + ":0" + servedTime;
		else
			timeStampService = hour + ":" + servedTime;
		
		return timeStampService;
	}

	/**
	 * Prints the Seating Map
	 * @param seating
	 * @param maxRows
	 * @param maxCols
	 */
	public static void printSeating(Seat[][] seating, int maxRows, int maxCols) {
		System.out.println("-------------------------");
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
	
	
	public abstract void sell();

	@Override
	public void run() {
		sell();
	}
}
