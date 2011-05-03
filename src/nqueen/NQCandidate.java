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
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setGenes(String genes) {
		// TODO Auto-generated method stub
		
	}
	
}
