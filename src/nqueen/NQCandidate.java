package nqueen;

import java.util.Arrays;
import java.util.Random;

import basic.Candidate;

public class NQCandidate extends Candidate {

	public static final int N = 25;
	
	private int[] genes;
	
	public NQCandidate(int[] genes) {
		super("haha");
		this.genes = genes;
	}

	// The random contructor!
	public NQCandidate() {
		super("haha");
		this.genes = NQCandidate.randomGenes();
	}
	
	private static int[] randomGenes() {
		int[] retVal = new int[NQCandidate.N];
		Random r = new Random();
		for (int i = 0; i < retVal.length; i++){
			retVal[i] = r.nextInt(NQCandidate.N);
		}
		return retVal;
	}

	@Override
	public String getGenes(){
		return null;
		
	}
	
	@Override
	public void setGenes(){
		
	}
	
	public NQCandidate copy() {
		return new NQCandidate(Arrays.copyOf(this.genes, this.genes.length));
	}
	
}
