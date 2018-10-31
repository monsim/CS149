package swap;

import java.util.ArrayList;

class Fitment {

    int start = 0;
    int freeSpaceAfter = 0;

    public Fitment(int start, int freeSpaceAfter) {
        this.start = start;
        this.freeSpaceAfter = freeSpaceAfter;
    }

}

//best fit memory management algorithm
public class BestFitMemory extends Memory {
 
    @Override
    public int getNextIndex(ArrayList<String> memory, SimulatedProcess proccess) {
        ArrayList<Fitment> possibleIndices = new ArrayList<Fitment>(); // List of indexes that could be used
        Fitment lastIndex = null; 
       
        int freeSpaceCount = 0; 
        
        int freeSpaceStart = -1; // first free space
        
        for (int j = 0; j < memory.size(); j++) {
            if (memory.get(j).equals(".")) { //if there's a free space
                if (freeSpaceStart == -1)	//we haven't had a free space yet
                    freeSpaceStart = j; //this index is the starting index
                freeSpaceCount++; //there's one more free space
                if (lastIndex != null)
                    lastIndex.freeSpaceAfter++; //increment empty space after storing
            } else {		//no free space
                lastIndex = null;
                freeSpaceCount = 0; //reset
                freeSpaceStart = -1; //reset
            }
            if (freeSpaceCount == proccess.getSize()) { // Store start index if there is enough space
                lastIndex = new Fitment(freeSpaceStart, freeSpaceCount); // Keep track of empty space after allocation at that index
                possibleIndices.add(lastIndex); // Store possible index
            }
        }
        possibleIndices.sort((f1, f2) -> Integer.compare(f1.freeSpaceAfter, f2.freeSpaceAfter));
        
        if (possibleIndices.isEmpty()) return -1;
        else return possibleIndices.get(0).start;
    }
}