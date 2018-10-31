package page;

import java.util.*;

//random paging algorithm 
public class RandomPaging extends Memory {

    private final Random random = new Random();

    //Gives access to a disk to memory
    public RandomPaging(Disk disk) {
        super(disk);
    }
   
    @Override
	// return a random index within the range of the page frames
    public int getPageIndexToRemove() {
        return random.nextInt(getPageFrames().size() - 1);
    }
}