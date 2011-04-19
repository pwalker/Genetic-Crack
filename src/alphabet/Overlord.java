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
	public int runGeneration(){
		// Selection
		//System.err.println("Selecting...");
		this.population.carryOver(this.manipulator.select(this.population,.7));

		//System.err.println(this.population.popString());
		
		// Replication (fill the population back up)
		// TODO should the replicator and selector be the same, i.e. Manipulator?
		//System.err.println("Reproducing...");
		this.manipulator.fillPopulation(this.population);

		//System.err.println(this.population.popString());
	
		// Let's share some DNA! TODO this may be out of order
		//System.err.println("Crossover...");
		this.manipulator.crossOver(this.population, this.geneTool);

		//System.err.println(this.population.popString());
	
		// Let's spread so cosmic rays!
		//System.err.println("Mutation...");
		this.manipulator.mutate(this.population, this.geneTool);

		//System.err.println(this.population.popString());
	

		System.out.println(((Mono)this.population).bestCandidate() + " " + ((Mono)this.population).bestCandidate().getFitness());
		//System.err.println("Done.");
		
		return ((Mono)this.population).bestCandidate().getFitness();
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
