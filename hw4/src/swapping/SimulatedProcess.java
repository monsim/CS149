package swapping;

public class SimulatedProcess {

    public static int nextpID = 0;
    private final int parentId;
    private final char processName;
    private final int pSize;
    private int pDuration;
    private boolean isFinished;
    private static final String NAMES = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";

   
    //Gets a process object with the specified size and duration
    public SimulatedProcess(int aSize, int aDuration) {
        parentId = nextpID++;
        this.processName = NAMES.charAt(parentId % NAMES.length());
        this.pSize = aSize;
        this.pDuration = aDuration;
    }

    
    public String getName() {
        return String.valueOf(processName);
    }

    public int getSize() {
        return pSize;
    }

    //A simulation of a process executing. Decreases remaining time
    public void executing() {
        pDuration -= 1;
        if (pDuration <= 0) {
            isFinished = true;
        }
    }

   
    public boolean isFinished() {
        return isFinished;
    }

    @Override
    public String toString() {
        String result = "Name=" + processName + "/Size=" + pSize + "/Duration=" + pDuration;
        return result;
    }

}