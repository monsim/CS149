package paging;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.Queue;
import java.util.Random;


public abstract class Memory {

    private final Disk disk;
    private final int maxPages = 4;
    //pages in memory
    private final List<Page> pageFrames = new ArrayList<>();
    private int pageHits;
    private int time = 0;
    private Queue<Page> workload;

    //Assigns disk access to memory
    public Memory(Disk d) {
    		workload = new LinkedList<>();
        disk = d;
    }

    
    // Gets a requested page from memory, or from disk. And if memory cannot take it, it removes a page. 
    // Arguments needed are the page being requested and the amount of references made
  
    public Page requestPage(int page, int referencesMade) {
        Optional<Page> optPage = pageFrames.stream().filter(p -> p.getPageNumber() == page).findFirst();
        System.out.print("Page reference " + referencesMade + " pages in memory: ");
        
        pageFrames.stream().forEach(System.out::print);
        System.out.println();
        if (optPage.isPresent()) {
        		Random gen = new Random();
        		int duration = gen.nextInt(5) + 1;
        		String arrivalTime = (duration + time) + "";
        		if (arrivalTime.length() <= 2) {
        			arrivalTime = "." + arrivalTime;
        		} else {
        			String lhs = arrivalTime.substring(0, arrivalTime.length() - 2);
        			String rhs = arrivalTime.substring(arrivalTime.length() - 2, arrivalTime.length());
        			arrivalTime = lhs + "." + rhs;
        		}
        		System.out.println("Arrival time: " + arrivalTime + " seconds");
        		time += duration;
        		System.out.println("Page " + page + " is hit");
        		pageHits++;
            return optPage.get();
        }
        
        System.out.println("Page " + page + " needs to be paged in");
        if (pageFrames.size() == maxPages) {
            int evictedIndex = getPageIndexToRemove();
            Page pageRemoved = pageFrames.remove(evictedIndex);
            System.out.println("Page " + pageRemoved.getPageNumber() + " was evicted");
        }
        
        Page rPage = disk.getPage(page);
        pageFrames.add(rPage);
        return rPage;
    }

   
    public abstract int getPageIndexToRemove();

    //this is to allow subclasses to access memory page frames
    protected List<Page> getPageFrames() {
        return pageFrames;
    }

    
    //get the number of pages on disk
    public int getPagesOnDisk() {
        return disk.getPageSizeOnDisk();
    }

    //the number of page hits during the current run 
    public int getPageHits() {
        return pageHits;
    }

  
    //reset the memory state of the page
    public void reset() {
        pageHits = 0;
        pageFrames.clear();
    }
}
