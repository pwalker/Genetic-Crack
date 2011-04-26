package basic;

import java.util.Comparator;

public class Overlord {

	private GeneTool geneTool;
	private Population population;
	private Evaluator eval;
	private EvalRunner[] runners;
	private EvalBarrier barrier;
	//private Thread[] threads;
	
	public Overlord(Population population, Evaluator eval, GeneTool geneTool, EvalBarrier b, int threads) {
		this.geneTool = geneTool;
		this.population = population;
		this.eval = eval;
		this.barrier = b;
		
		// TODO should this be in MonoDriver?
		// create our worker threads
		this.runners = new EvalRunner[threads];
		//this.threads = new Thread[threads];
		for (int i=0; i<threads; i++) {
			this.runners[i] = new EvalRunner(this.eval, this.population.getWorkQueue(), this.barrier);
			//this.threads[i] = new Thread(this.runners[i]);
			this.runners[i].start();
		}
		
	}
	
	/**
	 * Do the steps necessary for a generic Genetic algorithm generation
	 * @throws Exception 
	 * TODO I might want to handle this exception here
	 */
	public int runGeneration() throws Exception{
		// Select the members of the population as candidates for reproduction
		this.population.select(.8);
		
		// Crossover this selected population to replace the current population (This may "select" further, since chance effects being picked to crossover with someone else)
		this.population.crossoverFill(this.geneTool);
		
		// Apply some random mutations!
		this.population.mutate(this.geneTool, .3);
	
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
