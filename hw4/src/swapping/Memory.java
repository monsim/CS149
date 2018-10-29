package swapping;
import java.util.ArrayList;
import java.util.Random;

public abstract class Memory {
    private final ArrayList<String> memory = new ArrayList<>();
    private int time = 0;

    //Initialize memory with 100 free spaces
    public Memory() {
        for (int i = 0; i < 100; i++) {
            memory.add(".");
        }
    }

    //get the starting index of where to allocate memory for the provided process.
    public abstract int getNextIndex(ArrayList<String> memory, SimulatedProcess proccess);

    public boolean allocateMemory(SimulatedProcess process) {
        int startingIndex = getNextIndex(memory, process);
        if (startingIndex != -1) {
            for (int i = startingIndex; i < startingIndex + process.getSize(); i++) {
                memory.set(i, process.getName());
            }
            Random gen = new Random();
	    		int duration = gen.nextInt(5) + 1;
	    		String arrivalTime = (duration + time) + "";
	    		if (arrivalTime.length() <= 2) {
	    			arrivalTime = "." + arrivalTime;
	    		} else {
	    			String lhs = arrivalTime.substring(0, arrivalTime.length() - 2);
	    			String rhs = arrivalTime.substring(arrivalTime.length() - 2, arrivalTime.length());
	    			arrivalTime = lhs + "." + rhs;
	    		}
	    		System.out.println("Arrival time: " + arrivalTime + " seconds");
	    		time += duration;
            System.out.print("Entered memory map " + process.getName() + ": ");
            printMemoryMap();
            return true;
        }
        return false;
    }

   
    public void deallocateMemory(SimulatedProcess process) {
        for (int i = 0; i < memory.size(); i++) {
            if (memory.get(i).equals(process.getName())) {
                memory.set(i, ".");
            }
        }
        System.out.print("Exited memory map " + process.getName() + ": ");
        printMemoryMap();
    }

    public void printMemoryMap() {
        memory.stream().forEach(System.out::print);
        System.out.println();
    }

    public void reset() {
        for (int i = 0; i < memory.size(); i++) {
            memory.set(i, ".");
        }
    }
}