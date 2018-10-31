package swap;

import java.util.*;


public class CPUSystem {

	private static final int[] POSSIBLE_SIZES = {5, 11, 17, 31};
    private static final int[] POSSIBLE_DURATIONS = {1, 2, 3, 4, 5};
    public static final int MAX_TIME = 60;
    private final ArrayList<Integer> stats = new ArrayList<>();
    
    private final Memory memory;
    private final LinkedList<SimulatedProcess> allProcesses = new LinkedList<>();
    //queue of processes
    private final ArrayList<SimulatedProcess> current = new ArrayList<>();
    
    private int currentTime = 0;
    private final Random random = new Random(0);
    
    public CPUSystem(Memory memory) {
        this.memory = memory;
    }

    public void reset() {
        allProcesses.clear();
        memory.reset();
        current.clear();
        currentTime = 0;
        SimulatedProcess.nextPID = 0;
    }

    public void generateProcesses() {
    		ArrayList<Integer> usedSizes = new ArrayList<>();
    		ArrayList<Integer> usedDuration = new ArrayList<>();
        int index = random.nextInt(POSSIBLE_SIZES.length);
        int duration = random.nextInt(POSSIBLE_DURATIONS.length);
       
        for (int i = 0; i < 160; i++) { // Evenly distribute processes durations and sizes
            while (usedDuration.contains(duration)) {
                duration = random.nextInt(POSSIBLE_DURATIONS.length);
            }
            
            usedDuration.add(duration);
            if (usedDuration.size() == POSSIBLE_DURATIONS.length) {
                usedDuration.clear(); 
            }
            
            while (usedSizes.contains(index)) { 
                index = random.nextInt(POSSIBLE_SIZES.length);
            }
            
            usedSizes.add(index); 
            if (usedSizes.size() == POSSIBLE_SIZES.length) {
                usedSizes.clear(); 
            }
            allProcesses.add(new SimulatedProcess(POSSIBLE_SIZES[index], POSSIBLE_DURATIONS[duration]));
        }
    }

   
    public void start() {
        int numProcessesSwappedIn = 0; 
        while (currentTime <= MAX_TIME) {
            Iterator<SimulatedProcess> iterator = current.iterator();
            while (iterator.hasNext()) { // while there are processes still to run
                SimulatedProcess process = iterator.next();
                process.executing(); 
                if (process.isFinished()) {
                    memory.deallocateMemory(process); 
                    iterator.remove();  //the process is done, we can remove it from the list
                }
            }
            
            SimulatedProcess process = allProcesses.peek();  //first process
            if (memory.allocateMemory(process)) { 
                System.out.println("The following process was added: " + process);
                current.add(allProcesses.poll()); 
                numProcessesSwappedIn++;
            }

            currentTime++; 
        }
        stats.add(numProcessesSwappedIn);
    }

  
    public String getStats() {
        OptionalDouble avgSwap = stats.stream().mapToDouble(a -> a).average();
        double average;
        if (avgSwap.isPresent()) {
        		average = avgSwap.getAsDouble();
        } else {
        		average = 0;
        }
        String toReturn = "Average processes swapped in: " + (average);
        return "Average processes swapped in: " + toReturn;
    }

}