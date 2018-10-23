
package swapping;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.OptionalDouble;
import java.util.Random;


public class CPUSystem {

    private final Memory memory;
    private final LinkedList<SimulatedProcess> processes = new LinkedList<>();
    private final ArrayList<SimulatedProcess> runningProcesses = new ArrayList<>();
    private int currentTime = 0;
    private final Random rand = new Random(0);
    private static final int[] POSSIBLE_SIZES = {5, 11, 17, 31};
    private static final int[] POSSIBLE_DURATIONS = {1, 2, 3, 4, 5};
    private final List<Integer> stats = new ArrayList<>();

   
    public static final int SECONDS_TO_RUN = 60;

    //Sets up a new CPU scheduler with the given algorithm
    public CPUSystem(Memory alg) {
        this.memory = alg;
    }

   
    public void reset() {
        processes.clear();
        runningProcesses.clear();
        currentTime = 0;
        memory.reset();
        SimulatedProcess.nextpID = 0;
    }

    //Generates new processes to put on scheduler
    public void generateProcesses() {
        List<Integer> usedSizes = new ArrayList<>();
        List<Integer> usedDuration = new ArrayList<>();
        int size_idx = rand.nextInt(POSSIBLE_SIZES.length), duration_idx = rand.nextInt(POSSIBLE_DURATIONS.length);
       
        for (int i = 0; i < 160; i++) { // Evenly distribute processes durations and sizes
            while (usedDuration.contains(duration_idx)) { // Loop until unused index found
                duration_idx = rand.nextInt(POSSIBLE_DURATIONS.length);
            }
            
            usedDuration.add(duration_idx); // Add to used index
            if (usedDuration.size() == POSSIBLE_DURATIONS.length) {
                usedDuration.clear(); // Clear used list if list contains all possible indexes
            }
            
            while (usedSizes.contains(size_idx)) { // Loop until unused index found
                size_idx = rand.nextInt(POSSIBLE_SIZES.length);
            }
            
            usedSizes.add(size_idx); // Add to used index
            if (usedSizes.size() == POSSIBLE_SIZES.length) {
                usedSizes.clear(); // Clear used list if list contains all possible indexes
            }
            processes.add(new SimulatedProcess(POSSIBLE_SIZES[size_idx], POSSIBLE_DURATIONS[duration_idx]));
        }
    }

   
    public void start() {
        int procsSwappedIn = 0; // Keeps track number of processes swapped in
        // Each loop represents 1 second
        while (currentTime <= SECONDS_TO_RUN) {
            Iterator<SimulatedProcess> iter = runningProcesses.iterator();
            while (iter.hasNext()) { // Execute all running processes
                SimulatedProcess p = iter.next();
                p.executing(); 
                if (p.isFinished()) {
                    memory.deallocateMemory(p); 
                    iter.remove(); // Remove from running processes list
                }
            }
            
            SimulatedProcess process = processes.peek(); // Gets first process
            if (memory.allocateMemory(process)) { 
                System.out.println("Process added: " + process);
                runningProcesses.add(processes.poll()); // Adds to running processes list if memory was successfully allocated
                procsSwappedIn++;
            }

            currentTime += 1; // Increase CPU time by 1 second
        }
        stats.add(procsSwappedIn);
    }

  
    //Print average amount of processes swapped
    public String printStats() {
        OptionalDouble avgSwap = stats.stream().mapToDouble(a -> a).average();
        return "Average processes swapped in: " + (avgSwap.isPresent() ? avgSwap.getAsDouble() : 0);
    }

}