package swap;
import java.util.*;

public abstract class Memory {
    private final ArrayList<String> memory = new ArrayList<>();
    private int time = 0;

    public Memory() {
        for (int i = 0; i < 100; i++) {
            memory.add(".");
        }
    }

    public abstract int getNextIndex(ArrayList<String> memory, SimulatedProcess proccess);

    public boolean allocateMemory(SimulatedProcess process) {
        int index = getNextIndex(memory, process);
        if (index != -1) {
            for (int i = index; i < index + process.getSize(); i++) {
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
            System.out.print("Entered memory map for process " + process.getName() + ": ");
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
        System.out.print("Exited memory map for process " + process.getName() + ": ");
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