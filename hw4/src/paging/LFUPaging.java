package paging;
import java.util.ArrayList;
import java.util.List;

// LFU Paging class. Assumes that pages that have been accessed rarely are unlikely to
// be accessed again. Maintains a count of how many times each page is accessed, and
// evicts the page with the lowest count.

public class LFUPaging extends Memory
{
    private final List<Page> referenceCounter = new ArrayList<>();

    // method gives disk access to memory, variable 'disk'
    // is what memory has access to
    public LFUPaging(Disk d)
    {
        super(d);
    }

    // chooses Least Frequently Used page, should be at index 0 after sorting.
    // then removes index 0 from cache, and sets referenceCount to 0
    // returns index of page to remove

    @Override
    public int getPageIndexToRemove()
    {
        // sort according to LRC first using method sort()
        referenceCounter.sort((p1, p2) -> Integer.compare(p1.getReferenceCount(), p2.getReferenceCount()));

        Page p = referenceCounter.remove(0);
        p.setReferenceCount(0);

        return getPageFrames().indexOf(p);
    }

    // overrides the default requestPage method by incrementing the reference
    // variable 'page' holds index of the page to get
    // variable referencesMade holds current # of references made
    // returns the page being requested
    @Override
    public Page requestPage(int page, int referencesMade)
    {
        Page p = super.requestPage(page, referencesMade);
        if (!referenceCounter.contains(p))
            referenceCounter.add(p);
        p.setReferenceCount(p.getReferenceCount() + 1);

        return p;
    }


    // reset reference counter of the cache, then clear it
    @Override
    public void reset()
    {
        super.reset();
        referenceCounter.stream().forEach((p) ->
        {
            p.setReferenceCount(0);
        });
        referenceCounter.clear();
    }
}
