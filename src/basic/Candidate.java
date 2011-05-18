package basic;

import java.io.Serializable;

/**
 * A struct-type class. Stores a gene sequence.
 * 
 * @author peterw
 * 
 */

public abstract class Candidate implements Comparable<Candidate>, Serializable {

	private static final long serialVersionUID = 1L;
	// this is a cached value!
	protected int fitness;
	
	public Candidate() {
		this.fitness = -1;
	}
	
	public int compareTo(Candidate other) {
		return this.getFitness() - other.getFitness();
	}

	public abstract Candidate copy();

	public int getFitness() {
		return fitness;
	}
	
	public boolean hasFitness(){
		return this.fitness >= 0;
	}
	
	public void setFitness(int fitness) {
		this.fitness = fitness;
	}

	public abstract String toString();
	
	public abstract void setGenes(String genes);
}
