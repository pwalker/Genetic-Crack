package alphabet;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import mono.MonoCipher;

import basic.Trie;

/**
 * 
 * TODO do we even need to have the complete word in the Trie? or can we just
 * put in the first 5 characters
 * 
 * @author peterw
 * 
 */

public class Fitness {

	private static Trie trie;

	String cipherText;

	private MonoCipher cipher;
	
	public Fitness(MonoCipher c, String cText) {
		this("/usr/share/dict/words",c,cText);
	}
	
	/**
	 * Read the words in the given file (must be scanner friendly), into the
	 * cool data structure for easy access
	 * 
	 * @param dictionary
	 */
	public Fitness(String dictionary, MonoCipher c, String cText) {
		this.cipherText = cText;
		this.cipher = c;
		
		Fitness.trie = new Trie();

		File file = new File(dictionary);

		System.err.println("Creating trie from "+dictionary);
		
		try {
			Scanner s = new Scanner(file);
			while (s.hasNext()) {
				String word = s.next().toLowerCase().replaceAll("[^a-z]", "");
				
				// we don't want to add the letters
				if (word.length() > 3){
					Fitness.trie.add(word);
				}
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * Make a gross approximation about the English language, and determine some
	 * value, that only has meaning relative to other values, for how likely the
	 * given plaintext is to be English
	 * 
	 * @param plaintext
	 * @return
	 */
	public int fitness(String dna) {
		String plainText = this.cipher.decrypt(dna, this.cipherText);
		
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
