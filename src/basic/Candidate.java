package basic;

/**
 * A struct-type class. Stores a gene sequence.
 * 
 * @author peterw
 * 
 */

public class Candidate implements Comparable<Candidate> {

	private String genes;
	
	// this is a cached value!
	private int fitness;
	
	public Candidate(String genes) {
		this.genes = genes;
		this.fitness = -1;
	}
	
	public int compareTo(Candidate other) {
		return this.fitness - other.getFitness();
	}

	public Candidate copy() {
		return new Candidate(this.genes.toString());
	}

	public int getFitness() {
		return fitness;
	}

	public String getGenes() {
		return genes;
	}
	
	public boolean hasFitness(){
		return this.fitness >= 0;
	}
	
	public void setFitness(int fitness) {
		this.fitness = fitness;
	}

	public void setGenes(String str) {
		if (!this.genes.equals(str)){
			// since the genes aren't the same, this is outdated.
			this.fitness = -1;
		}
		this.genes = str;
	}

	public String toString(){
		return this.genes;
	}
	
}
