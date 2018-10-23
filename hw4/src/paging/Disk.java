package paging;

import java.util.ArrayList;
import java.util.List;


// this class represents a physical disk
public class Disk
{

    private final List<Page> pageList = new ArrayList<>();

    // creates 10 pages on this disk
    public Disk()
    {
        for (int i = 0; i < 10; i++)
            pageList.add(new Page(i));
    }

    // get method - retries according to index denoted by 'p', and
    // then returns page of that index

    public Page getPage(int p)
    {
        return p < pageList.size() ? pageList.get(p) : null;
    }

    // returns # of pages on the disk
    public int getPageSizeOnDisk()
    {
        return pageList.size();
    }
}
