package paging;

public class Main {

    public static void main(String[] args) {
        String output = ""; 
        
        Disk disk = new Disk();
        Memory[] algorithms = {new FIFOPaging(disk), new LRUPaging(disk), new LFUPaging(disk), new MFUPaging(disk), new RandomPaging(disk)};
        String[] algorithmNames = {"FIFO Paging", "LRU Paging", "LFU Paging", "MFU Paging", "Random Paging"};
        
        for (int i = 0; i < algorithms.length; i++) {
            Process process = new Process(algorithms[i]);
            System.out.println(algorithmNames[i]);
            for (int j = 0; j < 5; j++) {
                System.out.println("\r\nRun " + (j + 1) + " ");
                process.run();
                process.reset();
            }
            output += algorithmNames[i] + (" ") + process.printAverageHitRatio(); 
            System.out.println();
        }
        System.out.println(output);
    }
}
