package page;

import java.util.*;

public abstract class Memory {

	private final Disk disk;
	private static final int MAX_PAGES = 4;
	private int time = 0;
	private final List<Page> frames = new ArrayList<>(); // pages in memory
	private Queue<Page> workload;
	private int hits;

	// Assigns disk access to memory
	public Memory(Disk disk) {
		workload = new LinkedList<>();
		this.disk = disk;
	}

	// Gets a requested page from memory, or from disk. And if memory cannot take
	// it, it removes a page.
	// Arguments needed are the page being requested and the amount of references
	// made

	public Page requestPage(int page, int reference) {
		Optional<Page> optPage = frames.stream().filter(p -> p.getPageNumber() == page).findFirst();
		System.out.print("Page reference " + reference + " pages in memory: ");

		frames.stream().forEach(System.out::print);
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
			hits++;
			return optPage.get();
		}

		System.out.println("Page " + page + " needs to be paged in");
		if (frames.size() == MAX_PAGES) {
			int evictedIndex = getPageIndexToRemove();
			Page pageRemoved = frames.remove(evictedIndex);
			System.out.println("Page " + pageRemoved.getPageNumber() + " was evicted");
		}

		Page retrievedPage = disk.getPage(page);
		frames.add(retrievedPage);
		return retrievedPage;
	}

	public abstract int getPageIndexToRemove();

	public void reset() {
		hits = 0;
		frames.clear();
	}

	// access page frames
	protected List<Page> getPageFrames() {
		return frames;
	}

	// returns number of hits
	public int getPageHits() {
		return hits;
	}

	// returns number of pages on the disk
	public int getPagesOnDisk() {
		return disk.getPageSizeOnDisk();
	}

}
