package swapping;

import java.util.ArrayList; 

public class FirstFitMemory extends Memory {

    @Override
    // overrides the get index for first fit, simpler algorithm finds first available slot
    public int getNextIndex(ArrayList<String> mem, SimulatedProcess proccess) {
        int start = -1; 
        int freeCount = 0; // Keeps count of empty space
        for (int j = 0; j < mem.size(); j++) {
            if (mem.get(j).equals(".")) { // Check if space is free
                if (start == -1)
                    start = j; // Store starting index if start index is unset
                freeCount++; // Increment free block counter
            } else {
            		//reset free block counter and start index
                freeCount = 0;
                start = -1; 
            }
            if (freeCount == proccess.getSize())
                return start;
        }
        return -1;
    }
}