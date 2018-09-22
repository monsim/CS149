package hw2;

import java.util.*;
import java.lang.*;

public class HPFNonpreemptive {
	
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

	public HPFNonpreemptive(ArrayList<Process> processes) {
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

	private String printProcessesInfo(Process currentProcess) {
		float arrivalTime = currentProcess.getArrivalTime();
		int priority = currentProcess.getPriority();
		float runTime = currentProcess.getExpectedRunTime();
		int pid = currentProcess.getProcessID();

		String toReturn = "\n" + "Process " + pid + " \n" + "process arrival time: "
				+ arrivalTime + " \n" + "run time: " + runTime + " \n" + "priority " + priority + "\n";

		return toReturn;
	}

	public void run() {
		boolean queue1Done = false;
		boolean queue2Done = false;
		boolean queue3Done = false;
		boolean queue4Done = false;
		while (!queue4Done) {
			if (!queue1Done) {
				//RR on queue1
				RoundRobin rr1 = new RoundRobin(queue1);
				rr1.run();
				rr1.calculations(true);
				waitTime += rr1.getWaitTime();
				timeCounter += rr1.getTimeCount();
				responseTime += rr1.getResponseTime();
				queue1Done = true;
			} else if (!queue2Done) {
				//RR on queue
				RoundRobin rr2 = new RoundRobin(queue2);
				rr2.run();
				rr2.calculations(true);
				waitTime += rr2.getWaitTime();
				timeCounter += rr2.getTimeCount();
				responseTime += rr2.getResponseTime();
				queue2Done = true;
			} else if (!queue3Done) {
				//RR on queue3
				RoundRobin rr3 = new RoundRobin(queue3);
				rr3.run();
				rr3.calculations(true);
				timeCounter += rr3.getTimeCount();
				waitTime += rr3.getWaitTime();
				responseTime += rr3.getResponseTime();
				queue3Done = true;
			} else if (!queue4Done) {
				//RR on queue4
				RoundRobin rr4 = new RoundRobin(queue4);
				rr4.run();
				rr4.calculations(true);
				waitTime += rr4.getWaitTime();
				timeCounter += rr4.getTimeCount();
				responseTime += rr4.getResponseTime();
				queue4Done = true;
			} 
		}

		float turnaroundTime = (this.waitTime + timeCounter) / this.processList.size();
		float avgWaitingTime = this.waitTime / this.processList.size();
		float avgResponseTime = this.responseTime / this.processList.size();
		float throughput = this.processList.size() / (float) timeCounter;
		
		System.out.println(this.processInfo);
		System.out.println(this.quantaBreakdown);
		
		System.out.println("\naverage turnaroundtime: " + turnaroundTime);
		System.out.println("average waiting time: " + avgWaitingTime);
		System.out.println("average response time: " + avgResponseTime);
		System.out.println("throughput: " + throughput);
		
	}

	
}