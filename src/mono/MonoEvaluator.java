package mono;

import java.util.Random;

import basic.Candidate;
import basic.Evaluator;
import basic.Fitness;
import basic.GeneTool;

public class MonoEvaluator implements GeneTool, Evaluator {

	private Fitness fittor;
	private Random r;
	
	public MonoEvaluator(Fitness f){
		this(f, new Random());
	}
	
	public MonoEvaluator(Fitness f, Random r){
		this.fittor = f;
		this.r = r;
	}
	
	
	@Override
	public int compare(Candidate o1, Candidate o2) {
		if (o1 instanceof MonoCandidate && o2 instanceof MonoCandidate){
			return this.compare((MonoCandidate) o1, (MonoCandidate) o2);
		}
		System.err.println("MonoEvaluator.compare(): Casting didn't work!");
		return -1;
	}
	
	
	public int compare(MonoCandidate c1, MonoCandidate c2){
		// Let's make sure we can actually compare the candidates
		if (!c1.hasFitness()){
			this.evaluate(c1);
		}
		if (!c2.hasFitness()){
			this.evaluate(c2);
		}
		
		return c1.getFitness() - c2.getFitness();
	}

	/* (non-Javadoc)
	 * @see basic.Evaluator#evaluate(basic.MonoCandidate)
	 */
	@Override
	public void evaluate(Candidate c1) {
		// compute the fitness
		int score = this.fittor.fitness(c1);
		
		// cache the score
		c1.setFitness(score);
	}

	/* (non-Javadoc)
	 * @see basic.GeneTool#mutate(java.lang.String)
	 */
	@Override
	public void mutate(Candidate c) {
		this.mutate(c, .5);
	}

	/* (non-Javadoc)
	 * @see basic.GeneTool#mutate(java.lang.String, double)
	 */
	@Override
	public void mutate(Candidate c, double threshold) {
		if (c instanceof MonoCandidate){
			mutate((MonoCandidate) c, threshold);
		} else {
			throw new UnsupportedOperationException("This evaluate can only evaluate a MonoCandidate");
		}
	}

	public void mutate(MonoCandidate c, double threshold) {
		// copy the string
		String retVal = new String(c.getGenes().toCharArray());

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

		// modify the genes in place
		c.setGenes(retVal);
	}
	
	private String swapChars(String retVal, int from, int to) {
		char[] chars = retVal.toCharArray();

		// do the swap
		char tmp = chars[to];
		chars[to] = chars[from];
		chars[from] = tmp;

		return new String(chars);
	}

	/* (non-Javadoc)
	 * @see basic.GeneTool#cross(java.lang.String, java.lang.String)
	 */
	@Override
	public void cross(Candidate c1, Candidate c2) {
		this.cross(c1, c2, .4);
	}

	/* (non-Javadoc)
	 * @see basic.GeneTool#cross(java.lang.String, java.lang.String, double)
	 */
	@Override
	public void cross(Candidate c1, Candidate c2, double threshold) {
		if (c1 instanceof MonoCandidate && c2 instanceof MonoCandidate){
			this.cross((MonoCandidate) c1, (MonoCandidate) c2, threshold);
		}
	}

	public void cross(MonoCandidate c1, MonoCandidate c2, double threshold){
		// copy the string
		String ret1 = new String(c1.getGenes().toCharArray());
		String ret2 = new String(c2.getGenes().toCharArray());

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

		// modify the strings in place
		c1.setGenes(ret1);
		c2.setGenes(ret2);
	}
	
}
