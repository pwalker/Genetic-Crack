package nqueen;

import java.util.Arrays;
import java.util.Random;

import basic.Candidate;

public class NQCandidate extends Candidate {
	
	private int[] genes;
	
	public NQCandidate(int[] genes) {
		super();
		this.genes = genes;
	}

	// The random contructor!
	public NQCandidate() {
		super();
		this.genes = NQCandidate.randomGenes();
	}
	
	private static int[] randomGenes() {
		int[] retVal = new int[Config.N];
		Random r = new Random();
		for (int i = 0; i < retVal.length; i++){
			retVal[i] = r.nextInt(Config.N);
		}
		return retVal;
	}

	public String getGenes(){
		// TODO
		return "TODO";
	}
	
	public int[] toArray(){
		return this.genes;
	}
	
	public NQCandidate copy() {
		return new NQCandidate(Arrays.copyOf(this.genes, this.genes.length));
	}

	@Override
	public String toString() {
		String retVal = "";
		for (int i : this.genes) {
			retVal += i+" ";
		}
		return retVal.trim();
	}

	@Override
	public void setGenes(String genes) {
		// TODO Auto-generated method stub
		System.err.println("Don't do this!");
	}

	public void setGenes(int[] ints) {
		this.genes = Arrays.copyOf(ints, ints.length);
		this.fitness = -1;
	}
	
}
