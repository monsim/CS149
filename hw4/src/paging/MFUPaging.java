package paging;

import java.util.ArrayList;
import java.util.List;

public class MFUPaging extends Memory {
    private final List<Page> refCounter = new ArrayList<>();

    public MFUPaging(Disk d) {
        super(d);
    }

  
    @Override
    //Picks the most frequently used page which should be at index 0 after sorting. Removes from cache and sets reference count to 0.
    public int getPageIndexToRemove() {
        // Sort list by highest reference count first
        refCounter.sort((p1, p2) -> Integer.compare(p2.getReferenceCount(), p1.getReferenceCount()));
        Page p = refCounter.remove(0);
        p.setReferenceCount(0);
        return getPageFrames().indexOf(p);
    }

   
    @Override
    //increments the reference counter on the requested page
    public Page requestPage(int page, int refsMade) {
        Page p = super.requestPage(page, refsMade);
        if (!refCounter.contains(p))
            refCounter.add(p);
        p.setReferenceCount(p.getReferenceCount() + 1);
        return p;
    }

    @Override
    //Resets reference counters in cache
    public void reset() {
        super.reset();
        refCounter.stream().forEach((p) -> {
            p.setReferenceCount(0);
        });
        refCounter.clear();
    }

}
