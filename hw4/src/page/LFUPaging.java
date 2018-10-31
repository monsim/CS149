package page;
import java.util.*;

//least frequently used paging algorithm. 
public class LFUPaging extends Memory {
	//to keep track of how many times a block is referenced. 
    private final List<Page> referenceCounter = new ArrayList<>();

    public LFUPaging(Disk disk) {
        super(disk);
    }

    
    @Override
    public void reset() {
        super.reset();
        referenceCounter.stream().forEach((p) ->
        {
            p.setReferenceCount(0);
        });
        referenceCounter.clear();
    }
    
    	//sort by reference count of the pages, from least to greatest. index 0 is the block referenced least and should be removed
    @Override
    public int getPageIndexToRemove() {
        referenceCounter.sort((page1, page2) -> Integer.compare(page1.getReferenceCount(), page2.getReferenceCount()));

        Page page = referenceCounter.remove(0);
        page.setReferenceCount(0);

        return getPageFrames().indexOf(page);
    }

  
    //returns the requested page
    @Override
    public Page requestPage(int page, int referencesMade) {
        Page p = super.requestPage(page, referencesMade);
        if (!referenceCounter.contains(p))
            referenceCounter.add(p);
        p.setReferenceCount(p.getReferenceCount() + 1);

        return p;
    }
}
