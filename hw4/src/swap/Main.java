package swap;

import java.io.FileNotFoundException;
import java.io.PrintStream;

public class Main {

    public static void main(String[] args) {
    	
    		try {
    			PrintStream streamToFile = new PrintStream("SwappingOutput.txt");
    			
    			System.setOut(streamToFile);
	        String output = "";
	        
	        Memory[] algorithms = {new FirstFitMemory(), new NextFitMemory(), new BestFitMemory()};
	        String[] algorithmNames = {"First Fit", "Next Fit", "Best Fit"};
	        for (int i = 0; i < algorithms.length; i++) {
	            System.out.println(algorithmNames[i]);
	            
	            CPUSystem sys = new CPUSystem(algorithms[i]);
	            for (int j = 0; j < 5; j++) {
	                sys.generateProcesses();
	                sys.start();
	                sys.reset();
	                System.out.println();
	            }
	 
	            output += algorithmNames[i] + (" ") + sys.getStats() + "\r\n";
	            System.out.println();
	        }
	        System.out.println(output);
    		} catch (FileNotFoundException e){
    			e.printStackTrace();
    		}
    }    
}