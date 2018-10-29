package paging;

import java.io.FileNotFoundException;
import java.io.PrintStream;

public class Main {

    public static void main(String[] args) {
    		
    		try { 
    			PrintStream streamToFile = new PrintStream("PagingOutput.txt");
    			System.setOut(streamToFile);
    			
	        String output = ""; 
	        
	        Disk disk = new Disk();
	        Memory[] algorithms = {new FIFOPaging(disk), new LRUPaging(disk), new LFUPaging(disk), new MFUPaging(disk), new RandomPaging(disk)};
	        String[] algorithmNames = {"FIFO Paging", "LRU Paging", "LFU Paging", "MFU Paging", "Random Paging"};
	        	
	        for (int i = 0; i < algorithms.length; i++) {
	            Process process = new Process(algorithms[i], i+1);
	            
	            System.out.println(algorithmNames[i]);
	            System.out.println("Process " + process.getName() + " is running.");
	            for (int j = 0; j < 5; j++) {
	                System.out.println("\r\nRun " + (j + 1) + " ");
	                process.run();
	                process.reset();
	            }
	            output += algorithmNames[i] + (" ") + process.printAverageHitRatio(); 
	            System.out.println();
	        }
	        System.out.println(output);
	    } catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
}
