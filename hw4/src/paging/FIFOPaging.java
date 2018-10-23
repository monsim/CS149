package paging;

// FIFO Paging class. FIFO (First In First Out) works according to the First Come, First Served principle

public class FIFOPaging extends Memory
{
	
    public FIFOPaging(Disk disk)
    {
        super(disk);
    }

    @Override
    public int getPageIndexToRemove()
    {
    	return 0;
    }
}