package hw2;

import java.util.*;

public class Process {

	public final static int MAX_QUANTA = 99;
	public final static int MIN_QUANTA = 0;
	public final static float MIN_TOTAL_RUN_TIME = (float) 0.1;
	public final static float MAX_TOTAL_RUN_TIME = 10;
	
	private int priority;
	private float arrivalTime;
	private float burstTime;
	private float completionTime;
	private float expectedRunTime;
	private float startTime;
	private int processID;
	private final float expectedRunTimeForCal;
	
	private static Random randomGen = new Random(0);		

	public Process(int pid) {
		processID = pid;
		arrivalTime = generateRandomArrivalTime();
		expectedRunTime = generateRandomExpectedRunTime();
		expectedRunTimeForCal = expectedRunTime;
		startTime = -1;
		completionTime = -1;
		burstTime = expectedRunTime;
		priority = assignRandomPriority();
	}

	// returns pid
	public int getProcessID() {
		return processID;
	}

	public static void resetRandomGen() {
		randomGen = new Random(0);
	}

	// makes random arrival time
	private float generateRandomArrivalTime() {
		return MAX_QUANTA * randomGen.nextFloat();
	}

	// makes random expected runtime
	private float generateRandomExpectedRunTime() {
		return MIN_TOTAL_RUN_TIME + (MAX_TOTAL_RUN_TIME - MIN_TOTAL_RUN_TIME) * randomGen.nextFloat();
	}

	// assigns priority
	private int assignRandomPriority() {
		return randomGen.nextInt(4) + 1; // random number between 1 and 4
	}

	// generates a list of processes
	public static ArrayList<Process> generateProcesses(int numOfProcesses) {
		ArrayList<Process> processes = new ArrayList<>();
		for (int i = 0; i < numOfProcesses; i++) {
			Process toAdd = new Process(i);
			processes.add(toAdd);
		}
		return processes;
	}

	// returns arrival time
	public float getArrivalTime() {
		return arrivalTime;
	}

	// sets the expected run time
	public void setExpectedRunTime(float time) {
		expectedRunTime = time;
	}

	// returns expected run time
	public float getExpectedRunTime() {
		return expectedRunTime;
	}

	// returns initial expected run time of a process
	public float getExpectedRunTimeForCal() {
		return expectedRunTimeForCal;
	}

	// returns burst time
	public float getBurstTime() {
		return burstTime;
	}

	// returns priority of the process
	public int getPriority() {
		return priority;
	}

	// returns completion time
	public float getCompletionTime() {
		return completionTime;
	}

	// sets completion time
	public void setCompletionTime(float completionTime) {
		this.completionTime = completionTime;
	}

	// returns start time
	public float getStartTime() {
		return startTime;
	}

	// sets start time
	public void setStartTime(float startTime) {
		this.startTime = startTime;
	}

	// decrements burst time
	public void decreaseBurstTime() {
		if (burstTime < 1)
			burstTime = 0;
		else
			burstTime--;
	}

}