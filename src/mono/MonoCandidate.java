package mono;

import java.util.ArrayList;
import java.util.Collections;

import basic.Candidate;

public class MonoCandidate extends Candidate {

	protected String genes;
	
	public MonoCandidate(String genes) {
		super();
		this.genes = genes;
	}

	// The random contructor!
	public MonoCandidate() {
		super();
		this.genes = MonoCandidate.randomAlphabet();
	}
	
	public static String randomAlphabet() {
		String alphabet = "abcdefghijklmnopqrstuvwxyz";
		ArrayList<Character> list = new ArrayList<Character>();
		for (Character c : alphabet.toCharArray()) {
			list.add(c);
		}
		Collections.shuffle(list);
		String newCand = "";
		for (Character c : list) {
			newCand += c;
		}
		return newCand;
	}

	@Override
	public MonoCandidate copy() {
		return new MonoCandidate(this.getGenes().toString());
	}
	
	public String getGenes() {
		return genes;
	}

	public void setGenes(String str) {
		if (!this.genes.equals(str)){
			// since the genes aren't the same, this is outdated.
			this.fitness = -1;
		}
		this.genes = str;
	}

	@Override
	public String toString() {
		return this.getGenes();
	}
}
