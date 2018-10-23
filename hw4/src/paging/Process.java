package paging;

import java.util.ArrayList;
import java.util.List;
import java.util.OptionalDouble;
import java.util.Random;

/**
 * Simulates process which will reference memory
 */
 
public class Process {

    private final Memory mem;
    private int lastPageIndex = -1;
    private final Random r = new Random();
    private int pageRefs;
    private final List<Integer> stats = new ArrayList<>();

   
    //Constructor to create process and set mem
    public Process(Memory accessTo) {
        mem = accessTo;
    }

    //Generates the page that process will reference
    private int getPageToRequest() {
		// generates a random page index
        if (lastPageIndex == -1) {
            lastPageIndex = r.nextInt(mem.getPagesOnDisk());
            return lastPageIndex;
        }
        int p = r.nextInt(10);
        if (p < 7) { // 70% chance of locality of reference
            lastPageIndex = (lastPageIndex + (r.nextInt(3) - 1)) % 10;
        } else {
            lastPageIndex = (lastPageIndex + (r.nextInt(7) + 2)) % 10;
        }
        return Math.max(0, lastPageIndex);
    }

    //Simulates 100 page references
    public void run() {
        while (pageRefs < 100) {
            pageRefs++;
            mem.requestPage(getPageToRequest(), pageRefs);
        }
        stats.add(mem.getPageHits());
        System.out.println("Page hit ratio: " + mem.getPageHits() / 100.0);
    }

    public void reset() {
        pageRefs = 0;
        lastPageIndex = -1;
        mem.reset();
    }

   
    //average hit ratio string
    public String printAverageHitRatio() {
        OptionalDouble avgSwap = stats.stream().mapToDouble(a -> a).average();
        return "Average hit ratio: " + (avgSwap.isPresent() ? avgSwap.getAsDouble() / 100.0 : 0) + "\r\n";
    }
}