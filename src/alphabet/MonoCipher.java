package alphabet;

import java.util.HashMap;

public class MonoCipher {

	String alphabet = "abcdefghijklmnopqrstuvwxyz";

	public String decrypt(String mapping, String cipherText) {
		cipherText = cipherText.toLowerCase().replaceAll("[^a-z]", "");
		// check map
		if (mapping.length() != 26) {
			throw new UnsupportedOperationException("alphabet map isn't right");
		}

		// create conversion map
		HashMap<Character, Character> map = new HashMap<Character, Character>();
		for (int i = 0; i < this.alphabet.length(); i++) {
			// we are mapping cipherText letters to english letters
			map.put(mapping.charAt(i),this.alphabet.charAt(i));
		}
		
		String retVal = "";
		for (int i=0; i < cipherText.length(); i++){
			retVal += map.get(cipherText.charAt(i));
		}

		return retVal;
	}

}
