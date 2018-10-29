package paging;
import java.util.LinkedList;

// Least Recently Used assumes that pages that were accessed recently are likely to be needed.
// keeps a timestamp of last access, evicts the page with the lowest timestamp.

public class LRUPaging extends Memory
{
	//queue
    private final LinkedList<Page> LRUCache = new LinkedList<>(); 


    // method gives disk access to memory, variable 'disk'
    // is what memory has access to
    public LRUPaging(Disk d)
    {
        super(d);
    }

    // chooses Least Recently Used page, should be at index 0.
    // returns index of page to remove
    @Override
    public int getPageIndexToRemove()
    {
        return getPageFrames().indexOf(LRUCache.poll());
    }

    // override default requestPage method by keeping track of what pages were
    // referenced after calling the requestPage method in Superclass.
    // LRU cache order is Least recently used up front to Most recently used at the back.
    // variable 'page' index of page to get
    // variable 'referencesMade' holds current # of references made
    // returns the requested page
    @Override
    public Page requestPage(int page, int refsMade)
    {
        Page p = super.requestPage(page, refsMade);
        if (LRUCache.contains(p)) // is page already in cache?
            LRUCache.remove(p); // if true, move page to end of list
                                    // to signify recent use by adding and deleting

        LRUCache.addLast(p);    // append page to end of LRUCache List (end = most recent)

        return p;
    }

    // clear LRUCache list
    @Override
    public void reset()
    {
        super.reset();
        LRUCache.clear();
    }
}
