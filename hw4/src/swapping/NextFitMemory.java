package swapping;

import java.util.ArrayList;

public class NextFitMemory extends Memory {

    private int lastAssignedIndex;

    @Override
    //Gets index of where to allocate memory for the given process
    public int getNextIndex(ArrayList<String> memory, SimulatedProcess proccess) {
        int start = -1; 
        int freeCount = 0;
        boolean wrapAround = true; 
        
        for (int i = lastAssignedIndex; i < memory.size(); i++) { 
            // checks if memory is free
			if (memory.get(i).equals(".")) { 
                if (start == -1)
                    start = i; 
                freeCount++; 	// increments number of free spaces
            } else {
			// if not free then reset start and freeCount
                freeCount = 0; 
                start = -1; 
            }	// if number of free spaces is enough for process then return this start index
            if (freeCount == proccess.getSize()) { 
                lastAssignedIndex = i; 
                return start;
            }   
            if (wrapAround && i == memory.size() - 1) {
                i = 0; 
                freeCount = 0;
                start = -1;
                wrapAround = false;
            }
        }
        return -1;
    }
}