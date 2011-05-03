package mono;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Map.Entry;
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
				if (word.length() > 3){
					MonoFitness.trie.add(word);
				}
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}


	public int fitness(Candidate c) {
		int acc = 0;
		
		// does the plaintext contain any words?
		acc += wordCount(c);
		
		// how well do the frequencies line up with what's expected?
		acc += freqAnalyze(c);
		
		return acc;
	}
	
	private int freqAnalyze(Candidate c) {
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

	private String freqString(Candidate c) {
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
			insert: for (int i=0; i<retVal.size(); i++) {
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
	public int wordCount(Candidate c) {
		String plainText = this.cipher.decrypt(c.getGenes(), this.cipherText);
		
		int acc = 0;
		
		for (int i=0; i<plainText.length(); i++){
			int n = trie.containsBeginning(plainText.substring(i));
			if (n > 0){
				// skip over the word
				i += n;
				
				// acc += n;
				// try and make the fitness weight longer words more
				if (n > 4){
					acc += n * 2;
				} else {
					acc += 1;
				}
			}
			// System.err.println(plainText.substring(i)+" - "+trie.containsBeginning(plainText.substring(i)));
		}
		
		return acc;
	}

	/**
	 * Make an even grosser approximation of English, and do a brief cursory
	 * examination of common words or something like that
	 * 
	 * @param plaintext
	 * @return
	 */
	public double quickFitness(String plaintext) {
		// TODO
		return 0.0;
	}

}
