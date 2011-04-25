package basic;

public interface GeneTool {

	public abstract void mutate(Candidate c);

	/**
	 * Mutate a candidate in place, with no more than the given percentage of
	 * the genes changing
	 * 
	 * @param c
	 *            the Candidate to perform the mutation on
	 * @param percent
	 *            the maximum percentage of the genes to be modified
	 */
	public abstract void mutate(Candidate c, double percent);

	public abstract void cross(Candidate c1, Candidate c2);

	/**
	 * Crossover genes from both candidates to each other, with no more than the
	 * given percentage of the genes changing
	 * 
	 * @param c1
	 *            the first Candidate to give/receive genetic data
	 * @param c2
	 *            the second Candidate to give/receive genetic data
	 * @param percent
	 *            the maximum percentage of the genes to be changed
	 */
	public abstract void cross(Candidate c1, Candidate c2, double percent);

}