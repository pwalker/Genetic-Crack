package mono;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.TreeMap;

import basic.Candidate;
import basic.Fitness;
import basic.Trie;

/**
 * 
 * TODO do we even need to have the complete word in the Trie? or can we just
 * put in the first 5 characters
 * 
 * @author peterw
 * 
 */

public class MonoFitness extends Fitness {

	private static Trie trie;

	String cipherText;

	private MonoCipher cipher;
	
	public MonoFitness(MonoCipher c, String cText) {
		this("/usr/share/dict/words",c,cText);
	}
	
	/**
	 * Read the words in the given file (must be scanner friendly), into the
	 * cool data structure for easy access
	 * 
	 * @param dictionary
	 */
	public MonoFitness(String dictionary, MonoCipher c, String cText) {
		this.cipherText = cText;
		this.cipher = c;
		
		MonoFitness.trie = new Trie();

		File file = new File(dictionary);

		System.err.println("Creating trie from "+dictionary);
		
		try {
			Scanner s = new Scanner(file);
			while (s.hasNext()) {
				String word = s.next().toLowerCase().replaceAll("[^a-z]", "");
				
				// we don't want to add the letters
				if (word.length() >= 3){
					MonoFitness.trie.add(word);
				}
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}


	public int fitness(Candidate cand) {
		// We should only be evaluating MonoCandidates
		if (cand instanceof MonoCandidate){
			MonoCandidate c = (MonoCandidate) cand;
		
			int acc = 0;
			
			// does the plaintext contain any words?
			acc += this.wordPercent(c);
			
			// how well do the frequencies line up with what's expected?
			//acc += freqAnalyze(c);
			
			return acc;
		}
		
		// return the error fitness
		return -1;
	}
	
	private int freqAnalyze(MonoCandidate c) {
		// Here is the expected order of the alphabet by frequency according to Wikipedia
		String freq = "etaoinshrdlcumwfqypbvkjxqz";
		
		String cFreq = this.freqString(c);
		
		return this.numSimilar(freq, cFreq);
	}

	private int numSimilar(String freq, String cFreq) {
		int acc = 0;
		
		for (int i=0; i < freq.length() && i < cFreq.length(); i++) {
			if (freq.charAt(i) == cFreq.charAt(i)) {
				acc = acc + 3;
			}
		}
		
		return acc;
	}

	private String freqString(MonoCandidate c) {
		// load the plaintext
		String plainText = this.cipher.decrypt(c.getGenes(), this.cipherText);
		
		// count the frequencies
		TreeMap<Character,Integer> freqs = this.freqs(plainText);
		
		// sort em
		ArrayList<Character> retVal = new ArrayList<Character>();
		characters: for (Character ch : freqs.keySet()) {
			// are we looking at the first character?
			if (retVal.size() == 0) {
				retVal.add(ch);
				continue characters;
			}
			
			// put ch in the list somewhere
			for (int i=0; i<retVal.size(); i++) {
				Character current = retVal.get(i);
				if (freqs.get(ch) > freqs.get(current)) {
					// make an insertion
					retVal.add(i, ch);
					
					continue characters;
				} // else we move on to the next character
			}
			// if we got here, we didn't insert it anywhere, so lets append it
			retVal.add(ch);
		}
		
		// construct our string
		String ret = "";
		for (Character next : retVal) {
			ret += next;
		}
		return ret;
	}

	private TreeMap<Character,Integer> freqs (String plainText){
		TreeMap<Character,Integer> freqs = new TreeMap<Character,Integer>();
		for (int i=0; i<plainText.length(); i++) {
			Character ch = plainText.charAt(i);
			
			// have we seen this letter before?
			if (!freqs.containsKey(ch)) {
				freqs.put(ch, 0);
			}
			
			// increment for the letter
			freqs.put(ch, freqs.get(ch) + 1);
		}
		return freqs;
	}
	
	/**
	 * Make a gross approximation about the English language, and determine some
	 * value, that only has meaning relative to other values, for how likely the
	 * given plaintext is to be English
	 * 
	 * @param plaintext
	 * @return
	 */
	public int letterCount(MonoCandidate c) {
		String plainText = this.cipher.decrypt(c.getGenes(), this.cipherText);
		
		int acc = 0;
		
		for (int i=0; i<plainText.length();){
			String sub = plainText.substring(i);
			int n = trie.containsBeginning(sub);
			if (n > 0){
				// skip over the word
				i += n;
				
				acc += n;
			} else {
				i++;
			}
		}
		
		return acc;
	}

	/*
	 * Calculate the number of letters that contribute to a word
	 */
	public int wordPercent (MonoCandidate c) {
		double wc = this.letterCount(c);
		
		if (wc < 0) return 0;
		
		double len = this.cipherText.length();

		return (int) Math.floor((wc/len)*100);
	}
	
	@Override
	public int maxFitness() {
		// TODO Auto-generated method stub
		return 78;
	}

	@Override
	public void printSoln(Candidate best) {
		if (best instanceof MonoCandidate) {
			System.out.printf("Final solution: %s",((MonoCandidate) best).getGenes());
		} else {
			throw new UnsupportedOperationException("candidate is not a MonoCandidate");
		}
	}

}
