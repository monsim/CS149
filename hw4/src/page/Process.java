package page;

import java.util.*;

 
public class Process {

    private final Memory memory;
    private int pageIndex = -1;
    private final Random random = new Random();
    private int pageReferences;
    private final List<Integer> statistics = new ArrayList<>();
    private int name;
   
    public Process(Memory accessTo, int name) {
        this.memory = accessTo;
        this.name = name;
    }
    
    public int getName() {
    		return this.name;
    }

    private int getPageToRequest() {
		//we haven't assigned a page index to this process, assign one
        if (pageIndex == -1) {
            pageIndex = random.nextInt(memory.getPagesOnDisk());
            return pageIndex;
        }
        int p = random.nextInt(10);
        //if p is 0 - 7 it fits within the locality reference 
        if (p < 7) { 
            pageIndex = (pageIndex + (random.nextInt(3) - 1)) % 10;
        } else {
            pageIndex = (pageIndex + (random.nextInt(7) + 2)) % 10;
        }
        return Math.max(0, pageIndex);
    }

    // 100 page references
    public void run() {
        while (pageReferences < 100) {
            pageReferences++;
            memory.requestPage(getPageToRequest(), pageReferences);
        }
        statistics.add(memory.getPageHits());
        System.out.println("Page hit ratio: " + memory.getPageHits() / 100.0);
    }

    public void reset() {
        pageReferences = 0;
        pageIndex = -1;
        memory.reset();
    }

    public String getAverageHitRatio() {
        OptionalDouble avgSwap = statistics.stream().mapToDouble(a -> a).average();
        double avg;
        if (avgSwap.isPresent()) {
        		avg = avgSwap.getAsDouble() / 100.0;
        } else {
        		avg = 0;
        }
        String toReturn = "Average hit ratio: " + avg + "\r\n";
        return toReturn;
    }
}