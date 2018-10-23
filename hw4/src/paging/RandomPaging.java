package paging;

import java.util.Random;

/**
 * Implementation of random paging algorithm
 */
public class RandomPaging extends Memory {

	// random number generator
    private final Random randomIndex = new Random();

    //Gives access to a disk to memory
    public RandomPaging(Disk accessTo) {
        super(accessTo);
    }

   
    @Override
    //Generates a random index to remove
    public int getPageIndexToRemove() {
		// use randomIndex to generates pseudorandom number
		// with size equal to number of page numbers
        return randomIndex.nextInt(getPageFrames().size() - 1);
    }
}