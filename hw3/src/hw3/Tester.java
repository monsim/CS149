package hw3;

import java.util.Scanner;

public class Tester
{
    public static void main(String[] args)
    {

        // number of customers per seller per hour
        // ask for input from user
        int numOfCustomers = 0;
        Scanner userInput = new Scanner(System.in);
        System.out.print("Please enter # of Customers per Seller: ");

        if(userInput.hasNextInt())
            numOfCustomers = userInput.nextInt();
        else
            System.out.print("Invalid input. Please enter an integer.\n");

        final Object lock = new Object();

        // create 2d array that represents the seating
        int maxRows = 10;
        int maxCols = 10;
        Seat[][] theSeating = createSeating(maxRows, maxCols);

        //create 10 threads representing 10 sellers
        Seller[] allSellers = new Seller[10];
        for (int numSeller = 0; numSeller < 10; numSeller++)
        {
            if (numSeller == 0)
                allSellers[numSeller] = new SellerH(theSeating, "H" + (numSeller + 1), lock);
            else if (numSeller >= 1 && numSeller < 4)
                allSellers[numSeller] = new SellerM(theSeating, "M" + (numSeller), lock);
            else if (numSeller >= 4 && numSeller < 10)
                allSellers[numSeller] = new SellerL(theSeating, "L" + (numSeller - 3), lock);
        }


        //add numOfCustomers for each seller for each hour, initially add numOfCustomers for each seller's queue
        allSellers = addNewCustomers(allSellers, numOfCustomers);

        // start() all seller threads so they may run in parallel
        Thread []threads = new Thread[allSellers.length];

        for(int numSellers = 0; numSellers < allSellers.length; numSellers++)
        {
            threads[numSellers] = new Thread(allSellers[numSellers]);
            threads[numSellers].start();
        }


        for(int numSellers = 0; numSellers < allSellers.length; numSellers++)
        {
            try {
                threads[numSellers].join();
            } catch (InterruptedException e)
            {
                e.printStackTrace();
            }
        }
    }


     // Create a seating chart and label with seat numbers
     // maxRows: max number of rows for the chart
     // maxCols max number of columns for the chart
     // Return: seating chart with the given size and fully labeled

    public static Seat[][] createSeating(int maxRows, int maxCols)
    {
        //create 10x10 seating and label with seat numbers
        Seat[][] theSeating = new Seat[maxRows][maxCols];
        int numSeat = 1;
        for (int row = 0; row < maxRows; row++)
        {
            for (int column = 0; column < maxCols; column++)
            {
                theSeating[row][column] = new Seat(numSeat);
                numSeat++;
            }
        }
        return theSeating;
    }


     // Add the given number of customers for each seller's queue
     // allSellers: array containing all the sellers
     //  numAdd: number of customers to add to each seller
     // return : updated array with the new customers added to each queue

    public static Seller[] addNewCustomers(Seller[] allSellers, int numAdd)
    {
        for (int numSeller = 0; numSeller < allSellers.length; numSeller++)
        {
            for (int count = 0; count < numAdd; count++)
            {
                Customer c = new Customer(numSeller);
                allSellers[numSeller].addCustomer(c);
            }
            allSellers[numSeller].sortQueue();
        }
        return allSellers;
    }


     // Print the current seating chart
     // seating: current seating chart
     // maxRows: max number of rows for the chart
     // maxCols: max number of columns for the chart


    public static void prtSeating(Seat[][] seating, int maxRows, int maxCols)
    {
        System.out.println("__________________________");
        for (int row = 0; row < maxRows; row++)
        {
            for (int col = 0; col < maxCols; col++)
            {
                if (seating[row][col].isSeatEmpty())
                    System.out.printf("%7s ", "O");
                else
                    System.out.printf("%7s ", seating[row][col].getCustomer().getTicket());
            }
            System.out.println();
        }
    }

}