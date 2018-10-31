package page;

import java.io.FileNotFoundException;
import java.io.PrintStream;

public class Main {

    public static void main(String[] args) {
    		
    		try { 
    			PrintStream file = new PrintStream("PagingOutput.txt");
    			System.setOut(file);
    			
	        String text = ""; 
	        
	        Disk disk = new Disk();
	        Memory[] algorithms = {new FIFOPaging(disk), new LRUPaging(disk), new LFUPaging(disk), new MFUPaging(disk), new RandomPaging(disk)};
	        String[] algorithmNames = {"FIFO Paging", "LRU Paging", "LFU Paging", "MFU Paging", "Random Paging"};
	        	
	        for (int i = 0; i < algorithms.length; i++) {
	            Process process = new Process(algorithms[i], i+1);
	            
	            System.out.println(algorithmNames[i]);
	            System.out.println("Process " + process.getName() + " is running.");
	            for (int j = 1; j <= 5; j++) {
	                System.out.println("\r\nRun number " + (j) + " ");
	                process.run();
	                process.reset();
	            }
	            text += algorithmNames[i] + (" ") + process.getAverageHitRatio(); 
	            System.out.println();
	        }
	        System.out.println(text);
	    } catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
}
