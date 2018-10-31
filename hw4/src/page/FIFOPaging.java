package page;

//first in first out paging algorithm

public class FIFOPaging extends Memory {
	
    public FIFOPaging(Disk disk) {
        super(disk);
    }

    @Override
    public int getPageIndexToRemove() {
    		return 0;
    }
}