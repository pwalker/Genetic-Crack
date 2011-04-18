package standard;

public interface GeneTool {

	/**
	 * mutate(str) will do a mutation, that could conceivably return an entirely
	 * new string, that is wholly different from the given string.
	 * 
	 * @param str
	 *            the string to be mutated
	 * @return the mutated string
	 */
	public String mutate(String str);

	/**
	 * This method will return a mutated string, but unless the threshold is 1,
	 * the string cannot be completely mutated. The threshold indicates the
	 * percentage of mutations to do out of the total possible mutations. What I
	 * am intending this to be a way for us to specify, only mutation 5% of the
	 * alphabet (i.e. make around 3 swaps, so 6 characters have changed place)
	 * 
	 * @param str
	 *            the string to be mutated
	 * @param threshold
	 *            percentage of mutations to do out of the total needed to
	 *            completely change the string
	 * @return
	 */
	public String mutate(String str, double threshold);
	
	// from CrossOver
	
	public String cross(String str1, String str2);
	
	public String cross(String str1, String str2, double threshold);

}
