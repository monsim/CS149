package hw2;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Collections;

// CS 149 GROUP 10 \\

public class FCFS
{
	public String manageCPU; // String represents CPU status at quanta from 0 to 100 
	public String processInfo; // String that contains the process information
	public final static int MAX_QUANTA_RUN_TIME = 100;
	public static int timeCount = 0; // Keeps track of time from 0 to 100 
	public Queue<Process> processChain; //processQueue
	public ArrayList<Process> processes;
	public double responseTime;
	public int processEnd;//processDone
	public double turnAroundTime;
	public double waitTime;

	// Using Collections.sort() processes are sorted in ascending order depending on when they started 
	public FCFS(ArrayList<Process> processes)
	{
		this.processes = processes;
		this.manageCPU = "";
		this.processInfo = "";
		Collections.sort(this.processes, (o1, o2) -> {              
			if (o1.getArrivalTime() < o2.getArrivalTime()){
				return -1;
			}
			else if (o1.getArrivalTime() > o2.getArrivalTime()){
				return 1;
			}
			else
			{
				return 0;
			}
        });

		// This queue of processes will be executed by the CPU
		processChain = new LinkedList<Process>();
		for(Process processOne: this.processes){
			processChain.add(processOne);
		}
	}

	// Method run() starts the FCFS algorithm
	public void run()
	{
		timeCount = 0;
		boolean isRunning = false; // Boolean var to represent if process is still running
		Process currentProcess = null; 
		double nextAvailable = 0.0; // Keeps track of running processes
		for (; timeCount < MAX_QUANTA_RUN_TIME; timeCount++)
		{
			if (nextAvailable <= timeCount) // if true, then current process is finished
			{
				isRunning = false;
				if (currentProcess != null) {
					this.processInfo = this.processInfo + this.printProcess(currentProcess);
					currentProcess = null;
					this.processEnd++;
				}
			}
			// Checks if there are any processes in the queue and whether CPU is running or idle
			if (!processChain.isEmpty() && processChain.peek().getArrivalTime() <= timeCount && !isRunning)
			{
				currentProcess = processChain.poll();
				isRunning = true;
				nextAvailable = timeCount + currentProcess.getExpectedRunTime(); // Expected run time is when the current process is expected to end 
				this.manageCPU = this.manageCPU + timeCount + ": Process " + currentProcess.getProcessID() +"\n";
				// Process start time to finish time 
				this.turnAroundTime += nextAvailable - currentProcess.getArrivalTime();
				// Time in queue
				this.waitTime += timeCount - (currentProcess.getArrivalTime() + currentProcess.getExpectedRunTime());
				// time from process arrival until start
				this.responseTime += timeCount - currentProcess.getArrivalTime();
			}
			else
			{
				if (currentProcess != null) // Checks if the CPU is busy 
				{
					// Adds process ID number
					this.manageCPU = this.manageCPU + timeCount + ": Process " + currentProcess.getProcessID() +  "\n";
				}
				else // CPU is idle
				{
					// Waits for a process
					this.manageCPU = this.manageCPU + timeCount + ": Waiting for a process to run\n";
				}
				
			}
		}
		// Checks if process is still running after 100 quanta
		if (isRunning)
		{
			this.processEnd++;
			for (; timeCount < Math.round(nextAvailable); timeCount++) {
				this.manageCPU = this.manageCPU + timeCount + ": Process " + currentProcess.getProcessID() +  "\n";
				this.responseTime = this.responseTime + timeCount;
			}
			this.processInfo = this.processInfo + this.printProcess(currentProcess);
		}
		
		// Output printed on txt files
		System.out.println(this.processInfo);
		System.out.println(this.manageCPU);
		System.out.println("\nAverage Turn-Around Time is: " + this.turnAroundTime/this.processEnd);
		System.out.println("Average Wait Time is: " + this.waitTime/this.processEnd);
		System.out.println("Average Response Time is: " + this.responseTime/this.processEnd);
		System.out.println("Throughput: " + this.processEnd/ (float) timeCount);
	}


	// Returns formatted strings of the processes
	private String printProcess(Process currProcess) {
			return "\nProcess " + currProcess.getProcessID() +
					" \nArrival Time of current process is: " + currProcess.getArrivalTime() +
					" \nRun Time of current process is: " + currProcess.getExpectedRunTime() +
					" \nPriority of current process is: " + currProcess.getPriority() + "\n";
	}
}