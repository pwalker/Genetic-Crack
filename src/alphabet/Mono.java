package alphabet;

import java.util.ArrayList;
import java.util.Iterator;

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
	public void initializePop() {
		// create initial population
		for (int i = 0; i < this.idealSize(); i++) {
			this.add(new MonoCandidate(this.fitness));
		}
	}

	public MonoCandidate bestCandidate() {
		// return the last candidate in the list, which should have the highest
		// fitness
		ArrayList<MonoCandidate> sorted = this.sortCandidates();
		Iterator<MonoCandidate> it = sorted.listIterator(sorted.size()-1);
		
		return it.next();
	}

}
