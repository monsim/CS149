package hw2;

import java.util.*;

import hw2.Process;

public class SJF {
	
	public String CPUStatus; // status of CPU is recorded at every quanta
	public final static int MAX_TOTAL_RUN_TIME = 100;
	public static int timeCount = 0; // tracks time
	// process
	public int processDone;
	public String processInfo;
	public ArrayList<Process> processList;
	public Queue<Process> processQueue;
	
	// time
	public double waitTime;
	public double responseTime;
	public double turnAroundTime;
	
	// processList is sorted based on arrivalTime and xpectedRunTime
	public SJF(ArrayList<Process> processes) {
		this.processInfo = "";
		this.CPUStatus = "";
		this.processList = processes;
		Collections.sort(processList, new Comparator<Process>() {
			public int compare(Process p1, Process p2) {
				if (p1.getArrivalTime() < p2.getArrivalTime())
					return -1;
				else if(p1.getArrivalTime() > p2.getArrivalTime())
					return 1;
				else if(p1.getExpectedRunTime() < p2.getExpectedRunTime())
					return -1;
				else if(p1.getExpectedRunTime() > p2.getExpectedRunTime())
					return 1;
				else 
					return 0;		
			}
		});
		
		// The processes in the queue are ready to be ran by the CPU
		ArrayList<Process> sjfProcessList = new ArrayList<>();
		ArrayList<Process> processListCopy = (ArrayList<Process>) processes.clone();
		processListCopy.remove(0);
		processQueue = new LinkedList<Process>();
		processQueue.add(processList.get(0));

		// the total runtime of quanta
		int time = (int) Math.ceil(processList.get(0).getArrivalTime())
				+ (int) Math.ceil(processList.get(0).getExpectedRunTime());

		boolean finished = false;
		while (!finished) {
			// gets the newly queued up jobs if any
			// else, the next job is taken from processListCopy
			boolean jobArrived = false;

			for (int j = 0; j < processListCopy.size(); j++) {
				if (processListCopy.get(j).getArrivalTime() < time) {
					sjfProcessList.add(processListCopy.get(j));
					jobArrived = true;
				}
				else if (!jobArrived) {
					sjfProcessList.add(processListCopy.get(j));
					processListCopy.remove(j);
					break;
				} else
					break;
			}

			// the next shortest job is found
			Collections.sort(sjfProcessList, new Comparator<Process>() {
				@Override
				public int compare(Process p1, Process p2) {
					if(p1.getExpectedRunTime() < p2.getExpectedRunTime())
						return -1;
					else if( p1.getExpectedRunTime() > p2.getExpectedRunTime())
						return 1;
					else 
						return 0;
				}
			});

			// the shortest job is added to the Queue and removed from the copy
			processQueue.add(sjfProcessList.get(0));
			processListCopy.remove(sjfProcessList.get(0));
			time += (int) Math.ceil(sjfProcessList.get(0).getExpectedRunTime());
			sjfProcessList = new ArrayList<>();

			// finished when all of the processList is queued
			if (processQueue.size() == processList.size())
				finished = true;
		}
	}

	// runs the implemented SJF
	public void run() {
		boolean isExecuting = false; // process is running or not
		Process tempProcess = null;
		double readyForNext = 0.0; // tracks running time of process
		for (timeCount = 0; timeCount < MAX_TOTAL_RUN_TIME; timeCount++) {
			// process is only done if readyForNext exceeds timeCount
			if (readyForNext <= timeCount) {
				isExecuting = false;
				if (tempProcess != null) {
					processDone++;
					processInfo += this.toString(tempProcess);
					tempProcess = null;
				}
			}

			// calculates turnAroundTime, waitTime, and responseTime based on the job from the processList in the queue
			if (!processQueue.isEmpty() && processQueue.peek().getArrivalTime() <= timeCount && !isExecuting) {
				tempProcess = processQueue.poll();
				isExecuting = true;
				readyForNext = timeCount + tempProcess.getExpectedRunTime();
				CPUStatus += timeCount + ": Process " + tempProcess.getProcessID() + "\n";
				
				this.turnAroundTime += readyForNext - tempProcess.getArrivalTime();
				this.waitTime += timeCount - (tempProcess.getArrivalTime() + tempProcess.getExpectedRunTime());
				this.responseTime += timeCount - tempProcess.getArrivalTime();
				
			// finds if CPU is busy or free, waits accordingly 
			} else {
				if (tempProcess != null) 
				{
					this.CPUStatus += timeCount + ": Process " + tempProcess.getProcessID() + "\n";
				} else
				{
					this.CPUStatus += timeCount + ": Waiting for a process\n";
				}

			}

			// finds if process proceeds the time slice of 100 quantas
			if (isExecuting) {
				this.processDone++;
				for (; timeCount < Math.round(readyForNext); timeCount++) {
					this.CPUStatus += timeCount + ": Process " + tempProcess.getProcessID() + "\n";
					this.responseTime += timeCount;
				}
				this.processInfo += this.toString(tempProcess);
			}

			// processList info printed to the txt file
			System.out.println(processInfo);
			System.out.println(CPUStatus);
			System.out.println("\nAverage Turn-Around time: " + turnAroundTime / processDone);
			System.out.println("Average Wait time: " + waitTime / processDone);
			System.out.println("Average Response time: " + responseTime / processDone);
			System.out.println("Throughput: " + processDone / (float) timeCount);
		}
	}
	
	private String toString(Process currProcess) {
		return "\nProcess " + currProcess.getProcessID() + " \nArrival Time of current process: "
				+ currProcess.getArrivalTime() + " \nRun time of current process: " + currProcess.getExpectedRunTime()
				+ " \nPriority of current process: " + currProcess.getPriority() + "\n";
	}
}