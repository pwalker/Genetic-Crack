package alphabet;

import java.util.Random;

import standard.GeneTool;

public class MonoGeneTool implements GeneTool {

	Random r;
	
	public MonoGeneTool(Random rand){
		this.r = rand;
	}
	
	public MonoGeneTool (){
		this(new Random());
	}
	
	@Override
	public String mutate(String str) {
		return (this.mutate(str, .2));
	}

	/**
	 * IMPORTANT: no matter what the randomness decides, or how low the
	 * threshold, we will always perform at least 1 swap! but we may swap a
	 * letter with itself.
	 */
	@Override
	public String mutate(String str, double threshold) {
		// Make sure string is the right length
		if (str.length() != 26) {
			throw new UnsupportedOperationException(
					"Alphabet is not the right size");
		}

		// copy the string
		String retVal = new String(str.toCharArray());

		// figure out the maximum number of swaps
		// TODO do I need to check and make sure that the swaps <
		// retVal.length()
		int swapsPossible = (int) Math.floor((retVal.length() * threshold) / 2);

		// figure out the number of swaps
		//int swaps = Math.max(this.r.nextInt(swapsPossible + 1), 1);
		int swaps = this.r.nextInt(swapsPossible + 1);

		// do the swaps
		for (int i = 0; i < swaps; i++) {
			// pick a number to swap from
			int from = this.r.nextInt(26);

			// pick a number to swap to
			int to = this.r.nextInt(26);

			// perform the swap
			retVal = this.swapChars(retVal, from, to);
		}

		return retVal;
	}
	
	private String swapChars(String retVal, int from, int to) {
		char[] chars = retVal.toCharArray();

		// do the swap
		char tmp = chars[to];
		chars[to] = chars[from];
		chars[from] = tmp;

		return new String(chars);
	}

	@Override
	public String cross(String str1, String str2) {
		return this.cross(str1, str2, .4);
	}

	/**
	 * This threshold works in a very similar way to mutate This will return
	 * "newStr1 newStr2" and the values must be split after a method call
	 */
	@Override
	public String cross(String str1, String str2, double threshold) {
		// Make sure string is the right length
		if (str1.length() != 26 || str2.length() != str1.length()) {
			throw new UnsupportedOperationException(
					"Alphabet is not the right size");
		}

		// copy the string
		String ret1 = new String(str1.toCharArray());
		String ret2 = new String(str2.toCharArray());

		// figure out the maximum number of swaps
		// TODO do I need to check and make sure that the swaps <
		// ret1.length()
		int swapsPossible = (int) Math.floor((ret1.length() * threshold) / 2);

		// figure out the number of swaps
		//int swaps = Math.max(this.r.nextInt(swapsPossible + 1), 1);
		int swaps = this.r.nextInt(swapsPossible + 1);
		
		for (int i = 0; i < swaps; i++) {
			// switch the references at random
			if (this.r.nextBoolean()) {
				String tmp = ret2;
				ret2 = ret1;
				ret1 = tmp;
			}

			// take a letter from ret1 and give it to ret2
			int index = this.r.nextInt(26);

			char letter = ret1.charAt(index);

			// give letter to ret2
			// where is the letter currently in ret2?
			int current = ret2.indexOf(letter);

			// we will swap the letters at current and index
			ret2 = this.swapChars(ret2, current, index);

		}

		// pack the strings in the return string
		return ret1 + " " + ret2;
	}
	
}
