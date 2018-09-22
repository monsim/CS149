package hw2;

import java.io.*;

//group 10 CS149

/**
 * Prints outputs to txt file
 */
public class Tester {

	public static void main(String[] args) {
		testsFCFS();
		Process.resetRandomGen();
		testsSJF();
		Process.resetRandomGen();
		testsSRT();
		Process.resetRandomGen();
		testsRR();
		Process.resetRandomGen();
		testsHPFNonP();
		Process.resetRandomGen();
		testsHPFP();
		Process.resetRandomGen();
	}

	public static void testsFCFS() {
		try {
			PrintStream streamToFile = new PrintStream("FCFS.txt");
			System.setOut(streamToFile);
			for (int i = 0; i < 5; i++) {
				System.out.println("Now Running First-Come First-Served: Test " + (i + 1));
				FCFS fcfs = new FCFS(Process.generateProcesses(50));
				fcfs.run();
			}
		} catch (IOException e) {
			System.out.println("\n! Error running FCFS ! \n" + e);
		}
	}

	public static void testsSJF() {
		try {
			PrintStream streamToFile = new PrintStream("SJF.txt");
			System.setOut(streamToFile);
			for (int i = 0; i < 5; i++) {
				System.out.println("\nNow Running Shortest Job First: Test " + (i + 1));
				SJF sjf = new SJF(Process.generateProcesses(50));
				sjf.run();
			}
		} catch (IOException e) {
			System.out.println("\n! Error running SJF ! \n" + e);
		}
	}

	public static void testsSRT() {
		try {
			PrintStream streamToFile = new PrintStream("SRT.txt");
			System.setOut(streamToFile);
			for (int i = 0; i < 5; i++) {
				System.out.println("\nNow Running Shortest Remaining Time: Test " + (i + 1));
				SRTF srt = new SRTF(Process.generateProcesses(50));
				srt.run();
			}
		} catch (IOException e) {
			System.out.println("\n! Error running SRT ! \n" + e);
		}
	}

	public static void testsRR() {
		try {
			PrintStream streamToFile = new PrintStream("RR.txt");
			System.setOut(streamToFile);
			for (int i = 0; i < 5; i++) {
				System.out.println("\nNow Running Round Robin: Test " + (i + 1));
				RoundRobin rr = new RoundRobin(Process.generateProcesses(50));
				rr.run();
				System.out.println();
				rr.calculations();
				System.out.println();
			}
		} catch (IOException e) {
			System.out.println("\n! Error running Round Robin ! \n" + e);
		}
	}

	public static void testsHPFNonP() {
		try {
			PrintStream streamToFile = new PrintStream("HPF-NONPREEMTIVE.txt");
			System.setOut(streamToFile);
			for (int i = 0; i < 5; i++) {
				System.out.println("\nNow Running HPF Non-Preemptive: Test " + (i + 1));
				HPFNonpreemptive hpf = new HPFNonpreemptive(Process.generateProcesses(50));
				hpf.run();
			}
		} catch (IOException e) {
			System.out.println("\n! Error running HPF-Non-Preemptive ! \n" + e);
		}
	}

	public static void testsHPFP() {
		try {
			PrintStream streamToFile = new PrintStream("HPF-PREEMTIVE.txt");
			System.setOut(streamToFile);
			for (int i = 0; i < 5; i++) {
				System.out.println("\nNow Running HPF Preemptive: Test " + (i + 1));
				HPFPreemptive hpf = new HPFPreemptive(Process.generateProcesses(50));
				hpf.run();
			}
		} catch (IOException e) {
			System.out.println("\n! Error running HPF-PRE ! \n" + e);
		}
	}
}