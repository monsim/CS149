package swap;

import java.util.*;

public class NextFitMemory extends Memory {

    private int index;

    @Override
    public int getNextIndex(ArrayList<String> memory, SimulatedProcess proccess) {
        int freeSpaceStart = -1; 
        int freeSpaceCount = 0; 
        boolean wrapped = true; 
        
        for (int i = index; i < memory.size(); i++) { 
			if (memory.get(i).equals(".")) { 	//if there's a free space
                if (freeSpaceStart == -1)
                    freeSpaceStart = i; 
                freeSpaceCount++; 	
            } else {
                freeSpaceCount = 0; 
                freeSpaceStart = -1; 
            }	
            if (freeSpaceCount == proccess.getSize()) { 
                index = i; 
                return freeSpaceStart;
            }   
            if (wrapped && i == memory.size() - 1) { //if we should wrap back to the beginning and we're at the end
                i = 0; 
                freeSpaceCount = 0;
                freeSpaceStart = -1;
                wrapped = false;
            }
        }
        return -1;
    }
}