package alphabet;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Random;
import java.util.TreeSet;

import standard.Manipulator;
import standard.GeneTool;
import standard.Population;

public class MonoManipulator implements Manipulator {

	Random r;
	
	public MonoManipulator(Random rand){
		this.r = rand;
	}
	
	/**
	 * Wrapper for select(pop, n)
	 */
	@Override
	public Collection<MonoCandidate> select(Population population, double percentage) {
		int n = (int) Math.floor(population.idealSize() * percentage);
		return this.select(population, n);
	}

	/**
	 * TODO Ideas on selection: get a total fitness score, and generate a random
	 * number in the range 0-totalFitness. Then iterate over the list,
	 * subtracting the current candidates fitness from that number, when that
	 * numbers turns to zero return that random number. Do this an appropriate
	 * number of times till the size is right. In short: the size of each
	 * candidates place on the dart board is their fitness. The dart can hit
	 * anywhere, but probably those with a high fitness. Although, how to deal
	 * with a fitness of 0? maybe add 1 to all scores, although is this fair?
	 */
	@Override
	public Collection<MonoCandidate> select(Population pop, int n) {
		// accumulator
		ArrayList<MonoCandidate> retVal = new ArrayList<MonoCandidate>();

		// get the ordered list of candidates
		// ArrayList<MonoCandidate> list = pop.sortCandidates();
		Collection<MonoCandidate> set = pop.getCandidates();
		
		// sum the fitness
		double totalFit = 0;
		for (MonoCandidate c : set) {
			totalFit += Math.max(c.getFitness(),1);
		}

		// round it
		int totalFitness = (int) Math.floor(totalFit);

		// we want to pick n candidates from the population
		// TODO this is O(n^2)
		while (retVal.size() < n) {
			int x = this.r.nextInt(totalFitness);
			spinWheel: for (MonoCandidate c : set) {
				// did we land on the right one?
				if (x < c.getFitness()) {
					retVal.add(c.copy());
					break spinWheel;
				}

				// skip to the next wheel position.
				x = x - Math.max(c.getFitness(),1);
			}
		}

		return retVal;
	}

	@Override
	public void fillPopulation(Population pop) {
		int growth = pop.idealSize() - pop.currentSize();

		// get that many candidates
		Collection<MonoCandidate> newCands = this.select(pop, growth);
		for (MonoCandidate c : newCands) {
			pop.add(c.copy());
		}
	}

	/**
	 * This is from Manipulator. This method will just arbitrarily cross things
	 * over, since theoretically in the fillPopulation step, the good ones are
	 * already over represented
	 * 
	 * @param pop
	 * @param crossover
	 */
	@Override
	public void crossOver(Population pop, GeneTool crossover) {
		Iterator<MonoCandidate> it = pop.unOrderedIterator();
		while (it.hasNext()){
			MonoCandidate next1 = it.next();
			if (!it.hasNext()){
				break;
			}
			MonoCandidate next2 = it.next();
			String[] result = crossover.cross(next1.getGenes(), next2.getGenes()).split(" ");
			next1.updateGenes(result[0]);
			next2.updateGenes(result[1]);
		}
		
		pop.clearOrdering();
	}

	@Override
	public void mutate(Population pop, GeneTool geneTool) {
		for (MonoCandidate c : pop.getCandidates()){
			c.updateGenes(geneTool.mutate(c.getGenes()));
		}
		pop.clearOrdering();
	}

}
