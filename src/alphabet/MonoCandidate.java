package alphabet;

import java.util.ArrayList;
import java.util.Collections;


/**
 * A struct-type class. Stores a gene sequence.
 * 
 * @author peterw
 * 
 */

public class MonoCandidate implements Comparable<MonoCandidate> {

	private String genes;
	private int fitness;
	private Fitness fittor;

	public MonoCandidate(Fitness f, String genes) {
		this.genes = genes;
		this.fitness = -1;
		this.fittor = f;
	}

	// The random contructor!
	public MonoCandidate(Fitness f) {
		this(f, MonoCandidate.randomAlphabet());
	}

	public MonoCandidate(String next) {
		// TODO Auto-generated constructor stub
	}

	public static String randomAlphabet() {
		// TODO random!
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

	public String getGenes() {
		return genes;
	}

	@Override
	public int compareTo(MonoCandidate other) {
		if (this.getFitness() > other.getFitness()) {
			return 2;
		} else if (this.getFitness() < other.getFitness()) {
			return -2;
		}
		return 0;
	}

	public int getFitness() {
		if (this.fitness == -1) {
			this.fitness = this.fittor.fitness(this.genes);
		}
		return this.fitness;
	}

	public void updateGenes(String str) {
		// lets make sure these are new before we recalculate
		if (!str.equals(this.getGenes())){
			// reset the fitness
			this.fitness = -1;
			
			this.genes = str;
		}
	}

	public String toString(){
		return this.genes;
	}

	public MonoCandidate copy() {
		return new MonoCandidate(this.fittor,this.genes.toString());
	}
	
}
