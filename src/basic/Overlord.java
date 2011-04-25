package basic;

import java.util.Comparator;

public class Overlord {

	private GeneTool geneTool;
	private Population population;
	private Comparator<Candidate> eval;
	
	public Overlord(Population population, Comparator<Candidate> eval, GeneTool geneTool) {
		this.geneTool = geneTool;
		this.population = population;
		this.eval = eval;
	}
	
	/**
	 * Do the steps necessary for a generic Genetic algorithm generation
	 */
	public int runGeneration(){
		// Select the members of the population as candidates for reproduction
		this.population.select(.8);
		
		// Crossover this selected population to replace the current population (This may "select" further, since chance effects being picked to crossover with someone else)
		this.population.crossoverFill(this.geneTool);
		
		// Apply some random mutations!
		this.population.mutate(this.geneTool, .1);
	
		// lets get something to return
		// which is the fitness of the last element in the list, which should be the most fit candidate
		return this.population.sortCandidates().get(this.population.getSize()-1).getFitness();
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
