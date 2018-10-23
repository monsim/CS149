
package swapping;

import java.util.ArrayList;

public class BestFitMemory extends Memory {

    /**
     computes index where you need to start writing to memory, according to the best fit for the process
     * @param memory current memory
     * @param proccess process to allocate memory for
     * @return index of where to start allocating
     */
    @Override
    public int getNextIndex(ArrayList<String> memory, SimulatedProcess proccess) {
        ArrayList<Fitment> possibleIndices = new ArrayList<Fitment>(); // List of indexes that could be used
        Fitment f = null; // Last stored index
       
        int freeCount = 0; // how much space you have free
        
        int start = -1; // first free space
        
        for (int j = 0; j < memory.size(); j++) {
            if (memory.get(j).equals(".")) { // Check if space is free
                if (start == -1)
                    start = j; // Store starting index if start index is unset
                freeCount++; // Increment free block counter
                if (f != null)
                    f.freeSpaceAfter++; // Increment empty space after storing
            } else {
                f = null;
                freeCount = 0; // Reset free block counter
                start = -1; // Reset start Index
            }
            if (freeCount == proccess.getSize()) { // Store start index if there is enough space
                f = new Fitment(start, freeCount); // Keep track of empty space after allocation at that index
                possibleIndices.add(f); // Store possible index
            }
        }
        possibleIndices.sort((f1, f2) -> Integer.compare(f1.freeSpaceAfter, f2.freeSpaceAfter));
        
        return possibleIndices.isEmpty() ? -1 : possibleIndices.get(0).start;
    }

    
    class Fitment {

        int start = 0;
        int freeSpaceAfter = 0;

        public Fitment(int start, int freeSpaceAfter) {
            this.start = start;
            this.freeSpaceAfter = freeSpaceAfter;
        }

    }
}