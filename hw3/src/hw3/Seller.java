package hw3;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;

public abstract class Seller implements Runnable {
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

	public Seller(Seat[][] seating, int serviceTime, String sellerID, Object lock, long pastTime) {
		customerQueue = new LinkedList<Customer>();
		this.serviceTime = serviceTime;
		this.seating = seating;
		this.lock = lock;
		this.sellerID = sellerID;
		this.pastTime = pastTime;

	}

	protected void callTime(Customer customer) {

		time = (int) (currentTime + serviceTime); // + elapse_time;
		customer.setTime(time);
		// System.out.println("----------------" + customer.getArrivalTime() + "---sid:
		// " + this.sellerID + "service: " +this.serviceTime);
	}

	protected void assignSeat(Customer customer, Seat seat, int i, int j) {
		if (ticketNum < 10)
			customer.setTicket(sellerID + "0" + ticketNum);
		else
			customer.setTicket(sellerID + ticketNum);
		callTime(customer);
		ticketNum++;
		seat.assignSeat(customer);
		seating[i][j] = seat;
		if (sellerID.substring(0,1).equals("M")) Tester.successM++;
		if (sellerID.substring(0,1).equals("H")) Tester.successH++;
		if (sellerID.substring(0,1).equals("L")) Tester.successL++;
	}

	protected void update() {
		currentTime = System.currentTimeMillis() - this.pastTime;
		if (currentTime < 1000)
			currentTime = 0;
		else
			currentTime /= 1000;
	}

	public void addCustomer(Customer c) {
		customerQueue.add(c);
	}

	public void sortQueue() {
		Customer[] temp = customerQueue.toArray(new Customer[customerQueue.size()]);
		customerQueue.clear();
		Arrays.sort(temp);
		for (Customer c : temp)
			customerQueue.add(c);
	}

	protected void printMsg(Customer customer, Seat seat) {
		//System.out.println("TEST: " + customer.getArrivalTime() + " + " + this.serviceTime);
		// Arrival Time
		System.out.println(getArrivalTime(customer) + "  Customer " + customer.getCustomerID()
				+ " just arrived at seller " + this.sellerID);
		// Service Time
		System.out.println(getServiceTime(customer) + "  Service start time: Customer " + customer.getCustomerID());
		// Success Time
		if (seat == null)
			System.out.println(getSuccessTime(customer) + "  " + sellerID + " - Sorry, the concert is sold out!");
		else
			System.out.println(getSuccessTime(customer) + "  " + sellerID + " - Success! Your seat is " + seat.getSeatNumber());

		printSeating(this.seating, 10, 10);
		
		System.out.println();
	}

	protected String getSuccessTime(Customer customer) {
		int hour = customer.getTime() / 60;
		int min = customer.getTime() % 60;
		String time = "";
		if (min < 10)
			time = hour + ":0" + min;
		else
			time = hour + ":" + min;

		return time;
	}

	protected String getArrivalTime(Customer customer) {
		String timeStampArrival = "";
		// String timeStampService = "";

		int hour = customer.getArrivalTime() / 60;
		if (customer.getArrivalTime() < 10)
			timeStampArrival = hour + ":0" + customer.getArrivalTime();
		else
			timeStampArrival = hour + ":" + customer.getArrivalTime();
		return timeStampArrival;
	}

	private String getServiceTime(Customer customer) {
		//String arrival = getArrivalTime(customer);
		String success = getSuccessTime(customer);
		
//		int hourArrival = Integer.parseInt(arrival.substring(0,1)) * 60;
//		int minArrival = Integer.parseInt(arrival.substring(2,4));
//		int totalArrival = hourArrival + minArrival;
		
		int hourSuccess = Integer.parseInt(success.substring(0,1)) * 60;
		int minSuccess = Integer.parseInt(success.substring(2,4));
		int totalSuccess = hourSuccess + minSuccess;
//		
//		System.out.println("TotalSuccess: " + totalSuccess);
//		System.out.println("Service Time: " + this.serviceTime);
		int servedTime = Math.abs(totalSuccess - this.serviceTime);
		String serviveString = "";
		
		int hour = servedTime / 60;
		if (servedTime < 10)
			serviveString = hour + ":0" + servedTime;
		else
			serviveString = hour + ":" + servedTime;
		
		return serviveString;
	}
	// seller thread to serve one time "quanta" á 1 minute

	public abstract void sell();

	@Override
	public void run() {
		sell();
	}

	public static void printSeating(Seat[][] seating, int maxRows, int maxCols) {
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
