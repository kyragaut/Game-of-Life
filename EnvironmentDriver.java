/**
 *
 * @author Kyra GauthierDickey
 * Project 1
 * January 12, 2018
 * This part of the program tests if my Cell and Environment classes work
 * 
 */

public class EnvironmentDriver {
	
	public static void main(String[] args) {
		String filename = "GameOfLife4.txt"; 
		Environment e = new Environment(filename); 
		e.runSimulation();
		}

}
