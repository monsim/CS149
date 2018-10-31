package swap;

import java.util.*; 

public class FirstFitMemory extends Memory {

    @Override
    public int getNextIndex(ArrayList<String> memory, SimulatedProcess proccess) {
        int freeSpaceStart = -1; 
        int freeSpaceCount = 0; 
        for (int j = 0; j < memory.size(); j++) {
            if (memory.get(j).equals(".")) { //if there's a free space
                if (freeSpaceStart == -1)	//we haven't had a free space yet
                    freeSpaceStart = j; //this index is the starting index
                freeSpaceCount++;  //there's one more free space
            } else {		//no free space
                freeSpaceCount = 0;
                freeSpaceStart = -1; 
            }
            if (freeSpaceCount == proccess.getSize())
                return freeSpaceStart;
        }
        return -1;
    }
}