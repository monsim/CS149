package hw2;

import java.util.*;

/**
* Round Robin scheduling algorithm
*/
public class RoundRobin
{
	private final static int MAX_QUANTA_RUN_TIME = 100;
	private final static int TIME_SLICE = 1;
	
	
	private static int timeCount;
	
	private String quantaScale;
	private String processInfo;
	
	private Queue<Process> queueProcess;
	private ArrayList<Process> processes;
	private float waitTime;
	private float responseTime;
	
	/**
	 * Constructor for Round Robin that takes incoming processes and sorts based on its arrival time. 
	 * @param aProcesses - randomly generated processes in a list
	 */
	public RoundRobin(ArrayList<Process> aProcesses)
	{
		processInfo = "";
		quantaScale = "";
		waitTime = 0;
		responseTime = 0;
		
		processes = aProcesses;
		
		Collections.sort(this.processes, new Comparator<Process>()
		{
			public int compare(Process process1, Process process2)
			{
				if(process1.getArrivalTime() < process2.getArrivalTime())
				{
					return -1; 
				}
				else if(process1.getArrivalTime() > process2.getArrivalTime())
				{
					return 1;
				}
				else 
				{
					return 0; 
				}
			}
		});
		
		queueProcess = new LinkedList<Process>();
		for(Process p: this.processes)
		{	
			queueProcess.add(p);
			this.processInfo = this.processInfo + this.printProcessesInformation(p);
		}
	}
	
	/**
     * Removes starved processes and processes that havent started from readyQueue and processes and starved is set to true
     * @param processes - collection of processes
     * @param readyQueue - ready processes queue
     * @param currentTime - the current quanta time
     * @param quanta - starved processes that are ignored
     * @param starved - the boolean to check if process is starved or not 
     * @return true for starve and if a purge takes place then its false  
     */
	public boolean checkStarve(ArrayList<Process> aProcesses, Queue<Process> readyQueue, int currentTime, int quanta, boolean starved) 
	{
		if(!starved && currentTime >= quanta)
		{
			readyQueue.removeIf((process) -> (process.getStartTime() < 0));
            aProcesses.removeIf((process) -> (process.getStartTime() < 0));
            starved = true;
        }
        return starved;
	}
	
	/**
	 * Runs Round Robin on list of processes and keeps track of information
	 */
	public void run() 
	{
		timeCount = 0;
		Process currentProcess = null;
		boolean starved = false; 
		boolean complete = false;
		Queue<Process> readyQueue = new LinkedList<Process>();
		while (!complete)
		{
			//Starve is used to end any inactive processes in readyQueue
			//the boolean is a checker. 
			starved = checkStarve(processes,readyQueue,timeCount, MAX_QUANTA_RUN_TIME, starved);
		
			// If its starved we dont add the processes from queue to readyQueue since the current time is more than 100 Quanta
			if(!starved)
			{
	
				// Using a loop because there could be more than one processes that have arrived when the current time is 2
				for(Process process: queueProcess)
				{
					if(process.getArrivalTime() <= timeCount)
					{
						readyQueue.add(process);
					} 
					else 
					{
						break;
					}
				}
				// take out all processes from queue
				queueProcess.removeIf((process) -> (process.getArrivalTime() <= timeCount));
			}
			if (currentProcess != null) 
			{
				if (currentProcess.getExpectedRunTime() > 0) 
				{
					readyQueue.add(currentProcess);
				} else
				{
					currentProcess.setCompletionTime(timeCount);
				}
				currentProcess = null;
			}
			
			if((starved && readyQueue.isEmpty() && currentProcess == null)||(!starved && queueProcess.isEmpty() && readyQueue.isEmpty() && currentProcess == null)) 
			{
				complete = true;
			}
			
			if (currentProcess == null && !readyQueue.isEmpty()) 
			{
				currentProcess = readyQueue.poll();
				if(currentProcess.getStartTime() < 0) 
				{
					currentProcess.setStartTime(timeCount);
				}
				currentProcess.setExpectedRunTime(currentProcess.getExpectedRunTime() - TIME_SLICE);
				this.quantaScale += timeCount + ": " + currentProcess.getProcessID() + "\n";
			} else if (!complete) 
			{
				this.quantaScale = this.quantaScale + timeCount + ": Waiting for a process\n";
			}
			if(!complete) 
				timeCount = timeCount + TIME_SLICE;
		}
		currentProcess = null;
		System.out.println(this.processInfo);
		System.out.println(this.quantaScale);
	}
	
	/**
	 * Calculates and logs the avg times  of Round Robin
	 */
	public void calculations() 
	{
		
		float turnaroundTime = 0;
		
		
		for(Process process: processes)
		{
			turnaroundTime = turnaroundTime + process.getCompletionTime() - process.getArrivalTime();
			waitTime = waitTime + (process.getCompletionTime() - process.getArrivalTime()) - process.getExpectedRunTimeForCal();
			responseTime = responseTime + process.getStartTime() - process.getArrivalTime();
		}
		float avgTurnaroundTime = 0;
		float throughput = 0;
		float avgResponseTime = 0;
		float avgWaitTime = 0;
	
		
		if(processes.size() > 0) 
		{
			avgTurnaroundTime = turnaroundTime / processes.size();
			avgWaitTime = waitTime / processes.size();
			avgResponseTime = responseTime / processes.size();
			if(timeCount > 0)
				throughput = (float)processes.size() / timeCount;
		}
		
		System.out.println("Avg turnaround time = " + avgTurnaroundTime);
		System.out.println("Avg waiting time = " + avgWaitTime);
		System.out.println("Avg response time = " + avgResponseTime);
		System.out.println("Throughput = " + throughput);
	}
	
	public void calculations(boolean noPrint) 
	{
		float turnaroundTime = 0;
		
		
		for(Process process: processes)
		{
			turnaroundTime = turnaroundTime + process.getCompletionTime() - process.getArrivalTime();
			waitTime = waitTime + (process.getCompletionTime() - process.getArrivalTime()) - process.getExpectedRunTimeForCal();
			responseTime = responseTime + process.getStartTime() - process.getArrivalTime();
		}
		float avgTurnaroundTime = 0;
		float throughput = 0;
		float avgResponseTime = 0;
		float avgWaitTime = 0;
	
		
		if(processes.size() > 0) 
		{
			avgTurnaroundTime = turnaroundTime / processes.size();
			avgWaitTime = waitTime / processes.size();
			avgResponseTime = responseTime / processes.size();
			if(timeCount > 0)
				throughput = (float)processes.size() / timeCount;
		}
		
	}
	
	public float getWaitTime() {
		return waitTime;
	}
	
	public int getTimeCount() { 
		return timeCount;
	}
	
	public float getResponseTime() {
		return responseTime;
	}
	
	 /**
     * prints the process information that was ran
     * @param currentProcess - prints the current process information that it is checking
     * @return information on the process we are lookingat
     */
    private String printProcessesInformation(Process currentProcess) 
    {
       
        
        String processInfo = "\n" + "Process " + currentProcess.getProcessID() +
                " \n" + "Arrival Time of this process is: " + currentProcess.getArrivalTime() +
                " \n" + "Expected Run Time is: " + currentProcess.getExpectedRunTime() +
                " \n" + "Priority " + currentProcess.getPriority() + "\n";
        
    		return processInfo;
    }
	
}