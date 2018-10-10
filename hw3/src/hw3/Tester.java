package hw3;

import java.util.Scanner;

public class Tester
{
	
	static int successH = 0;
    static int successM = 0;
    static int successL = 0;
    
    
   //Prints the seating format 
   public static void printSeatingFormat(Seat[][] aSeat, int maximumRows, int maximumCols)
   {
       System.out.println("__________________________");
       for (int i = 0; i < maximumRows; i++)
       {
           for (int j = 0; j < maximumCols; j++)
           {
               if (aSeat[i][j].isSeatEmpty())
                   System.out.printf("%7s ", "O");
               else
                   System.out.printf("%7s ", aSeat[i][j].getCustomer().getTicket());
           }
           System.out.println();
       }
   }
   
    public static void main(String[] args)
    {

        // number of customers per seller every hour
        // asks for the input from user
    		if (args.length <= 0) 
    		{
    			System.out.println("Please enter a valid number of customers (above 1)");
    			return;
    		}
    		
        int numberOfCustomers = Integer.parseInt(args[0]);

        final Object lock = new Object();
        
        

        // a 2d array that is the seating arrangement 
        int maimumxRows = 10;
        int maximumCols = 10;
        Seat[][] seatingArragnement = seatingChart(maimumxRows, maximumCols);

        //10 threads that represent each of the 10 sellers
        Seller[] everySeller = new Seller[10];
        for (int numberOfSeller = 0; numberOfSeller < 10; numberOfSeller++)
        {
            if (numberOfSeller == 0)
            {
            	 everySeller[numberOfSeller] = new SellerH(seatingArragnement, "H" + (numberOfSeller + 1), lock);
            }
               
            else if (numberOfSeller >= 1 && numberOfSeller < 4)
            {
            	everySeller[numberOfSeller] = new SellerM(seatingArragnement, "M" + (numberOfSeller), lock);
            }
                
            else if (numberOfSeller >= 4 && numberOfSeller < 10)
            {
                everySeller[numberOfSeller] = new SellerL(seatingArragnement, "L" + (numberOfSeller - 3), lock);
            }
        }


        //add number of customers for each seller for each hour
        // first add number of customers  for each queue of seller 
        everySeller = addCustomers(everySeller, numberOfCustomers);

        // start() will run all the seller threads in parallel 
        Thread []threads = new Thread[everySeller.length];

        for(int numberOfSeller = 0; numberOfSeller < everySeller.length; numberOfSeller++)
        {
            threads[numberOfSeller] = new Thread(everySeller[numberOfSeller]);
            threads[numberOfSeller].start();
        }


        for(int numberOfSeller = 0; numberOfSeller < everySeller.length; numberOfSeller++)
        {
            try {
                threads[numberOfSeller].join();
            } catch (InterruptedException e)
            {
                e.printStackTrace();
            }
        }
        

		System.out.println("Total H customers successfully served: " + Tester.successH);
		System.out.println("Total M customers successfully served: " + Tester.successM);
		System.out.println("Total L customers successfully served: " + Tester.successL);
		
		int totalTicketsSold = numberOfCustomers*10;
		
		System.out.println("Total number of customers that were served: " + totalTicketsSold);
		System.out.println("Total number of customers that weren't served: " + (totalTicketsSold - Tester.successH - Tester.successM - Tester.successL));
    }
    


    // Add the given number of customers for each seller's queue
    // totalSellers: array containing every seller
    //  numofCusToAdd: number of customers to add to each of the sellers
    // return : array with the new customers added to the queue

   public static Seller[] addCustomers(Seller[] totalSellers, int numofCusToAdd)
   {
       for (int numberOfSeller = 0; numberOfSeller < totalSellers.length; numberOfSeller++)
       {
           for (int count = 0; count < numofCusToAdd; count++)
           {
               Customer c = new Customer(numberOfSeller);
               totalSellers[numberOfSeller].addCustomer(c);
           }
           totalSellers[numberOfSeller].sortQueue();
       }
       return totalSellers;
   }


     // Create a seating chart and label with seat numbers
     // Return: seating chart with the size and labeled
    public static Seat[][] seatingChart(int maximumRows, int maximumCols)
    {
        //create 10x10 seating and label with seat numbers
        Seat[][] theSeating = new Seat[maximumRows][maximumCols];
        int numOfSeat = 1;
        for (int i = 0; i < maximumRows; i++)
        {
            for (int j = 0; j < maximumCols; j++)
            {
                theSeating[i][j] = new Seat(numOfSeat);
                numOfSeat++;
            }
        }
        return theSeating;
    }


}