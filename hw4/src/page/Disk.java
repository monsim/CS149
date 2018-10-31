package page;

import java.util.*;

public class Disk {

    private final List<Page> pageList = new ArrayList<>();

    public Disk() {
    		//10 pages per disk
        for (int i = 0; i < 10; i++) {
        		this.pageList.add(new Page(i));
        }
    }

    //returns page at given index p
    public Page getPage(int p) {
    		if (p < this.pageList.size()) {
    			return this.pageList.get(p);
    		} else {
    			return null;
    		}
    }

    // returns how many pages this disk has
    public int getPageSizeOnDisk() {
        return this.pageList.size();
    }
}
