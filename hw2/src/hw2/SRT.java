package hw2;

import java.util.*;

public class SRTF {

	private int processesFinished;
	private float responseTime;
	private float turnaroundTime;
	private float waitTime;

	ArrayList<Process> readyProcesses;
	ArrayList<Process> processes;
	Queue<Process> processQueue;

	public SRTF(ArrayList<Process> aProcesses) {

		processes = aProcesses;

		// starts the sorting by arrival time then the sorting time
		Collections.sort(processes, new Comparator<Process>() {
			public int compare(Process process1, Process process2) {
				if (process1.getArrivalTime() < process2.getArrivalTime()) {
					return -1;
				} else if (process1.getArrivalTime() > process2.getArrivalTime()) {
					return 0;
				} else {
					if (process1.getExpectedRunTime() > process2.getExpectedRunTime()) {
						return 0;
					}
					return -1;
				}
			}
		});

		processQueue = new LinkedList<Process>();
		for (Process aProcess : processes) {
			processQueue.add(aProcess);
		}
	}

	public void run() {
		// current process
		Process currentProcess = null;
		// readyProcesses is ready and waiting processes
		readyProcesses = new ArrayList<Process>();
		String timeline = "";
		// expected values print
		for (int i = 0; i < processes.size(); i++) {
			System.out.println("Process " + processes.get(i).getProcessID());
			System.out.println("Arrival Time of this process is: " + processes.get(i).getArrivalTime());
			System.out.println("Expected Run Time is: " + processes.get(i).getExpectedRunTime());
			System.out.println("Priority: " + processes.get(i).getPriority() + "\n");

		}
		// processing system.
		for (int j = 0; j <= 100; j++) {
			System.out.println("Time: " + j);
			if (!processQueue.isEmpty()) {

				if (currentProcess == null & processQueue.peek().getArrivalTime() <= j) {
					currentProcess = processQueue.poll();
					currentProcess.setStartTime(j);

				} else if ((processQueue.peek().getArrivalTime() <= j)) {
					if (currentProcess.getBurstTime() > processQueue.peek().getBurstTime()) {

						readyProcesses.add(currentProcess);
						currentProcess = processQueue.poll();
						currentProcess.setStartTime(j);
					}

					else if (currentProcess.getBurstTime() <= processQueue.peek().getBurstTime()) {

						readyProcesses.add(processQueue.poll());
					}

				}
			}
			if (currentProcess == null) {
				timeline = timeline + "NAN ";
			} else {
				timeline = timeline + currentProcess.getProcessID() + " ";
			}

			if (currentProcess != null) {
				System.out.println("----Process #" + currentProcess.getProcessID() + "------");
				currentProcess.decreaseBurstTime();//

				if (currentProcess.getBurstTime() == 0) {
					this.turnaroundTime = this.turnaroundTime + (j + 1) - currentProcess.getArrivalTime();

					this.waitTime = this.waitTime + ((j + 1) - currentProcess.getArrivalTime())
							- (currentProcess.getExpectedRunTime());

					this.responseTime = this.responseTime + currentProcess.getStartTime()
							- currentProcess.getArrivalTime();

					this.processesFinished++;
					if (readyProcesses.size() == 0) {
						currentProcess = null;

					} else {
						currentProcess = getReadyProcessLowBurstTime();

						if (currentProcess.getBurstTime() == currentProcess.getExpectedRunTime()) {
							currentProcess.setStartTime(j);
						}
					}
				}

			}
		}

		System.out.println(timeline);
		float avgTurnaroundTime = this.turnaroundTime / this.processesFinished;
		float avgWaitTime = this.waitTime / this.processesFinished;
		float avgResponseTime = this.responseTime / this.processesFinished;
		System.out.println("\nThe avg turnaround time is: " + avgTurnaroundTime);
		System.out.println("The avg waiting time is: " + avgWaitTime);
		System.out.println("The avg response time is: " + avgResponseTime);
	}

	// returns process with the lowest burst time
	public Process getReadyProcessLowBurstTime() {
		Process currentProcess = null;
		int track = 0;
		for (int i = 0; i < readyProcesses.size(); i++) {
			if (currentProcess == null) {
				currentProcess = readyProcesses.get(i);
				track = i;
			} else if (currentProcess.getBurstTime() > readyProcesses.get(i).getBurstTime()) {
				currentProcess = readyProcesses.get(i);
				track = i;
			}
		}
		readyProcesses.remove(track);
		return currentProcess;
	}

	public String printProcessesInfo(Process currentProcess) {
		int processId = currentProcess.getProcessID();
		int processPriority = currentProcess.getPriority();
		float timeOfArrival = currentProcess.getArrivalTime();
		float expcRunTime = currentProcess.getExpectedRunTime();

		String projectProcessInfo = "\nProcess " + processId + " \nArrival Time of this process is: " + timeOfArrival
				+ " \nExpected Run Time is: " + expcRunTime + " \nPriority " + processPriority + "\n";
		return projectProcessInfo;
	}

}
