package paging;

// this class represents a page file
public class Page
{

    // private fields declared for the accessor methods below
    private final int page;
    private int reference;

    // creates a page with specified #
    // pageNumber = page number for current page object
    public Page(int pageNumber)
    {
        page = pageNumber;
    }

    // gets page # for current Page object, and returns it
    public int getPageNumber()
    {
        return page;
    }

    // gets reference count of current page, and returns it
    public int getReferenceCount()
    {
        return reference;
    }

    // sets reference count for current page, passing in integer 'ref' for new reference count

    public void setReferenceCount(int ref)
    {
        reference = ref;
    }

    // toString method that returns current page
    @Override
    public String toString()
    {
        return page + " ";
    }
}
