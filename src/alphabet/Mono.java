package alphabet;

import standard.Population;

/**
 * Alphabet mutator will take an alphabet and mutate it! This means that it will
 * not duplicate letters, only swap them around.
 * 
 * @author Peter Walker
 * 
 */
public class Mono extends Population {

	private Fitness fitness;
	
	public Mono(int popSize, Fitness f) {
		super(popSize);
		this.fitness = f;
		
		this.initializePop();
	}

	/**
	 * Generate a enough Candidates to fill the population to the idealSize
	 */
	public void initializePop(){
		// create initial population
		for (int i=0; i<this.idealSize(); i++) {
			this.add(new MonoCandidate(this.fitness));
		}
	}
	
}
