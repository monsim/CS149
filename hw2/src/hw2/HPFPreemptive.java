package hw2;

import java.util.*;
import java.lang.*;

public class HPFPreemptive {

	private String processInfo;
	private String quantaBreakdown;
	
	private float waitTime;
	private float responseTime;
	
	private final static int MAX_QUANTA = 100;
	private static int timeCounter = 0;
//	private Queue<Process> queue;
	private ArrayList<Process> queue1;
	private ArrayList<Process> queue2;
	private ArrayList<Process> queue3; 
	private ArrayList<Process> queue4;
	private ArrayList<Process> processList;

	public HPFPreemptive(ArrayList<Process> processes) {
		this.processInfo = "";
		this.quantaBreakdown = "";
		this.processList = processes;
		
		queue1 = new ArrayList<>();
		queue2 = new ArrayList<>();
		queue3 = new ArrayList<>();
		queue4 = new ArrayList<>();
		
		//puts processes in appropriate queue
		for (int i = 0; i < processList.size(); i++) {
			if (processList.get(i).getPriority() == 1) {
				queue1.add(processList.get(i));
			} else if (processList.get(i).getPriority() == 2) {
				queue2.add(processList.get(i));
			} else if (processList.get(i).getPriority() == 3) {
				queue3.add(processList.get(i));
			} else {		//priority is 4
				queue4.add(processList.get(i));
			}
		}
	}
	
	private String printProcessesInfo(Process process) {

		float arrivalTime = process.getArrivalTime();
		int priority = process.getPriority();
		float runTime = process.getExpectedRunTime();
		int pid = process.getProcessID();
	
		String toReturn = "\n" + "Process " + pid + " \n" + "process arrival time: " + arrivalTime + " \n"
				+ "run time: " + runTime + " \n" + "priority " + priority + "\n";

		return toReturn;
	}

	public void run() {
		boolean queue1Done = false;
		boolean queue2Done = false;
		boolean queue3Done = false;
		boolean queue4Done = false;
		while (!queue4Done) {
			if (!queue1Done) {
				//FCFS on queue1
				FCFS fcfs1 = new FCFS(queue1);
				fcfs1.run(true);
				waitTime += fcfs1.getWaitTime();
				timeCounter += fcfs1.getTimeCount();
				responseTime += fcfs1.getResponseTime();
				queue1Done = true;
			} else if (!queue2Done) {
				//FCFS on queue2
				FCFS fcfs2 = new FCFS(queue2);
				fcfs2.run(true);
				waitTime += fcfs2.getWaitTime();
				timeCounter += fcfs2.getTimeCount();
				responseTime += fcfs2.getResponseTime();
				queue2Done = true;
			} else if (!queue3Done) {
				//FCFS on queue3
				FCFS fcfs3 = new FCFS(queue3);
				fcfs3.run(true);
				waitTime += fcfs3.getWaitTime();
				timeCounter += fcfs3.getTimeCount();
				responseTime += fcfs3.getResponseTime();
				queue3Done = true;
			} else if (!queue4Done) {
				//FCFS on queue4
				FCFS fcfs4 = new FCFS(queue4);
				fcfs4.run(true);
				waitTime += fcfs4.getWaitTime();
				timeCounter += fcfs4.getTimeCount();
				responseTime += fcfs4.getResponseTime();
				queue4Done = true;
			} 
		}
		
		float turnaroundTime =  (this.waitTime + timeCounter) / this.processList.size();
		float avgWaitingTime = this.waitTime / this.processList.size();
		float avgResponseTime = this.responseTime / this.processList.size();
		float throughput = this.processList.size()/ (float) timeCounter;
		
		System.out.println(this.processInfo);
		System.out.println(this.quantaBreakdown);
		
		System.out.println("\nturnaroundtime: " + turnaroundTime);
		System.out.println("average waiting time: " + avgWaitingTime);
		System.out.println("average response time: " + avgResponseTime);
		System.out.println("throughput: " + throughput);
		
	}
}