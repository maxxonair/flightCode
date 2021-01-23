package main;

import Simulator_main.Simulation;

public class Main {

	
	public static void main(String[] args) {
		// Create Simulation 
		Simulation simulation = new Simulation(true, 10);
		// Run Simulation>
		simulation.launch();
	}

}
