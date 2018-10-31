package page;

import java.util.*;

//most frequently used paging algorithm
public class MFUPaging extends Memory {
	
	//to keep track of how many times a block is referenced.
    private final List<Page> referenceCounter = new ArrayList<>();

    public MFUPaging(Disk disk) {
        super(disk);
    }

    @Override
    public void reset() {
        super.reset();
        referenceCounter.stream().forEach((p) -> {
            p.setReferenceCount(0);
        });
        referenceCounter.clear();
    }
  
    @Override
	//sort by reference count of the pages, from least to greatest. index 0 is the block referenced most and should be removed
    public int getPageIndexToRemove() {
        referenceCounter.sort((page1, page2) -> Integer.compare(page2.getReferenceCount(), page1.getReferenceCount()));
        Page page = referenceCounter.remove(0);
        page.setReferenceCount(0);
        return getPageFrames().indexOf(page);
    }

   
    @Override
    //increments the reference counter on the requested page and returns the page
    public Page requestPage(int page, int refsMade) {
        Page p = super.requestPage(page, refsMade);
        //if this page hasn't been referenced before, add it the reference counter
        if (!referenceCounter.contains(p))
            referenceCounter.add(p);
        p.setReferenceCount(p.getReferenceCount() + 1);
        return p;
    }


}
