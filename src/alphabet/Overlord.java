package alphabet;

import standard.Manipulator;
import standard.GeneTool;
import standard.Population;

public class Overlord {

	private GeneTool geneTool;
	private Population population;
	private Manipulator manipulator;
	
	public Overlord(Population population, GeneTool geneTool, Manipulator manipulator) {
		// super();
		this.geneTool = geneTool;
		this.population = population;
		this.manipulator = manipulator;
		
		// create the initial generation
		// TODO initializing should happen in a Driver class
		// population.initialize(Candidate.generateRandom());
	}
	
	/**
	 * Do the steps necessary for a generic Genetic algorithm generation
	 */
	public void runGeneration(){
		// Selection
		System.err.println("Selecting...");
		this.population.carryOver(this.manipulator.select(this.population,.7));
	
		// Replication (fill the population back up)
		// TODO should the replicator and selector be the same, i.e. Manipulator?
		System.err.println("Reproducing...");
		this.manipulator.fillPopulation(this.population);
	
		// Let's share some DNA! TODO this may be out of order
		System.err.println("Crossover...");
		this.manipulator.crossOver(this.population, this.geneTool);
		
		// Let's spread so cosmic rays!
		System.err.println("Mutation...");
		this.manipulator.mutate(this.population, this.geneTool);
		
	}

	/**
	 * Do we have a sufficient candidate?
	 * TODO we need some idea of expected value
	 * @return
	 */
	public boolean isDone() {
		return false;
	}
	
}
