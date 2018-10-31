package page;

public class Page {

    private final int page;
    private int referenceCount;

    public Page(int pageNumber) {
        page = pageNumber;
    }

    // toString method that returns current page
    @Override
    public String toString() {
        return page + " ";
    }

    // returns page number 
    public int getPageNumber() {
        return page;
    }
    
    // sets reference count of this page
    public void setReferenceCount(int ref) {
        referenceCount = ref;
    }

    // returns reference count of this page
    public int getReferenceCount() {
        return referenceCount;
    }
}
