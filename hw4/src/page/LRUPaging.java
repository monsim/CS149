package page;
import java.util.*;

//least recently used paging algorithm
public class LRUPaging extends Memory
{
	//queue to keep track of the cached page
    private final LinkedList<Page> LRUCache = new LinkedList<>();
    
    public LRUPaging(Disk disk)
    {
        super(disk);
    }

    @Override
    public void reset()
    {
        super.reset();
        LRUCache.clear();
    }
    
    //least recently used page is at index 0
    @Override
    public int getPageIndexToRemove()
    {
        return getPageFrames().indexOf(LRUCache.poll());
    }
    
    //returns the requested page
    @Override
    public Page requestPage(int page, int refsMade)
    {
        Page p = super.requestPage(page, refsMade);
        
        //if the page is already in the cache, remove it
        if (LRUCache.contains(p)) 
            LRUCache.remove(p);
        //add page to the end of the queue
        LRUCache.addLast(p);    
        return p;
    }

  
}
