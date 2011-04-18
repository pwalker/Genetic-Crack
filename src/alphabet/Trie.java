package alphabet;

import java.util.HashMap;

// import java.util.Map.Entry;

public class Trie {

	// private char item;
	private boolean wordEnds;
	private HashMap<Character, Trie> children;

	/*
	 * public Trie() { this('X'); }
	 * 
	 * public Trie(char c) {
	 */

	public Trie() {
		// 13*2==26==max & 1 means we can completely fill up the set, which
		// should be possible since each character has a unique hashcode
		this.children = new HashMap<Character, Trie>(13, 1);
		// this.item = c;
		this.wordEnds = false;
	}

	public Trie(boolean b) {
		this();
		this.wordEnds = true;
	}

	public void add(String str) {
		// get the first char
		char first = str.charAt(0);

		// if we haven't seen first before, make a child node for it
		if (!this.children.containsKey(first)) {
			// this.children.put(first, new Trie(first));

			// handle the terminal case
			if (str.length() == 1) {
				this.children.put(first, new Trie(true));
			} else {
				this.children.put(first, new Trie());
			}
		}

		if (str.length() > 1) {
			// since we know a child node for first exists, pass off the work
			this.children.get(first).add(str.substring(1));
		}
	}

	public boolean contains(String needle) {
		// handle the terminal case
		if (needle.equals("")) {
			return this.wordEnds;
		}

		// since we have a string of length > 0
		// is the first character one of the our children?
		char first = needle.charAt(0);
		if (this.children.containsKey(first)) {
			return this.children.get(first).contains(needle.substring(1));
		}

		return false;
	}

	/**
	 * See if the beginning of a given string is a word
	 * 
	 * @param needle
	 * @return
	 */
	public int containsBeginning(String needle) {
		return containsBHelper(needle, -1, 0);
	}

	/**
	 * TODO should I try and find the longest string possible!
	 * TODO Yes! make it greedy!
	 * 
	 * @param needle
	 * @param depth
	 * @return
	 */
	public int containsBHelper(String needle, int previousWord, int depth) {
		// does a word end here? update the previousWord
		if (this.wordEnds) {
			previousWord = depth;
		}
		
		// handle the terminal case. i.e. no more characters left
		if (needle.equals("")) {
			return previousWord;
		}

		// since we have a string of length > 0
		// is the first character one of the our children?
		char first = needle.charAt(0);
		if (this.children.containsKey(first)) {
			return this.children.get(first).containsBHelper(
					needle.substring(1), previousWord, depth + 1);
		}

		// not a word!
		return previousWord;
	}

	public int numContains(String needle, int depth) {
		if (needle.equals("")) {
			return depth;
		}

		char first = needle.charAt(0);
		if (this.children.containsKey(first)) {
			return this.children.get(first).numContains(needle.substring(1),
					depth + 1);
		}

		return depth;
	}

}
