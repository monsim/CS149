
package swapping;

public class Main {

    public static void main(String[] args) {
        String output = "";
        
        Memory[] algorithms = {new FirstFitMemory(), new NextFitMemory(), new BestFitMemory()};
        String[] algorithmNames = {"First Fit", "Next Fit", "Best Fit"};
        for (int algIndex = 0; algIndex < algorithms.length; algIndex++) {
            System.out.println(algorithmNames[algIndex]);
            
            CPUSystem sys = new CPUSystem(algorithms[algIndex]);
            for (int i = 0; i < 5; i++) {
                sys.generateProcesses();
                sys.start();
                sys.reset();
                System.out.println();
            }
 
            output += algorithmNames[algIndex] + (" ") + sys.printStats() + "\r\n";
            System.out.println();
        }
        System.out.println(output);
    }

}