package swap;

public class SimulatedProcess {

	private static final String ALPHABET = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
   
    private int durationOfProcess;
    private final char name;
    private final int sizeOfProcess;
    public static int nextPID = 0;
    private boolean isFinished;
    private final int pid;

   
    public SimulatedProcess(int size, int duration) {
        this.pid = nextPID++;
        this.sizeOfProcess = size;
        this.name = ALPHABET.charAt(pid % ALPHABET.length());
        this.durationOfProcess = duration;
    }

    
    public String getName() {
        return String.valueOf(name);
    }

    public int getSize() {
        return sizeOfProcess;
    }

    //A simulation of a process executing. Decreases remaining time
    public void executing() {
        durationOfProcess -= 1;
        if (durationOfProcess <= 0) {
            isFinished = true;
        }
    }

   
    public boolean isFinished() {
        return isFinished;
    }

    @Override
    public String toString() {
        String result = "Name=" + name + "/Size=" + sizeOfProcess + "/Duration=" + durationOfProcess;
        return result;
    }

}