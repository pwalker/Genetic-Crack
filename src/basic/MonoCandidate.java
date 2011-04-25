package basic;

import java.util.ArrayList;
import java.util.Collections;

public class MonoCandidate extends Candidate {

	public MonoCandidate(String genes) {
		super(genes);
	}

	// The random contructor!
	public MonoCandidate() {
		super(MonoCandidate.randomAlphabet());
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

	public MonoCandidate copy() {
		return new MonoCandidate(this.getGenes().toString());
	}
	
}
