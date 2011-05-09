package nqueen;

import java.util.Random;

import basic.Candidate;
import basic.Evaluator;
import basic.Fitness;
import basic.GeneTool;

public class NQEvaluator implements Evaluator, GeneTool {

	private Random random;
	private Fitness fittor;

	NQEvaluator(Fitness f) {
		this.fittor = f;
		this.random = new Random();
	}

	@Override
	public int compare(Candidate o1, Candidate o2) {
		if (o1 instanceof NQCandidate && o2 instanceof NQCandidate) {
			return this.compare((NQCandidate) o1, (NQCandidate) o2);
		}
		System.err.println("NQEvaluator.compare(): Casting didn't work!");
		return -1;
	}

	// TODO code is the same as in MonoEvaluator
	public int compare(NQCandidate c1, NQCandidate c2) {
		// Let's make sure we can actually compare the candidates
		if (!c1.hasFitness()) {
			this.evaluate(c1);
		}
		if (!c2.hasFitness()) {
			this.evaluate(c2);
		}

		return c1.getFitness() - c2.getFitness();
	}

	@Override
	public void mutate(Candidate c) {
		mutate(c, Config.MUTATE_THRESHOLD);
	}

	@Override
	public void mutate(Candidate c, double percent) {
		if (c instanceof NQCandidate) {
			mutate((NQCandidate) c, percent);
		} else {
			throw new UnsupportedOperationException(
					"This evaluate can only evaluate a MonoCandidate");
		}
	}

	public void mutate(NQCandidate c, double percent) {
		// figure out our n
		int n = (int) Math.ceil(Config.N * percent);

		// Get a random number
		int mutations = this.random.nextInt(Config.N);

		int[] ints = c.toArray();
		for (int i = 0; i < mutations; i++) {
			int r = this.random.nextInt(Config.N);
			ints[r] = this.random.nextInt(Config.N);
		}
		
		// put it back
		c.setGenes(ints);
	}

	@Override
	public void cross(Candidate c1, Candidate c2) {
		this.cross(c1, c2, Config.CROSS_THRESHOLD);
	}

	@Override
	public void cross(Candidate c1, Candidate c2, double percent) {
		if (c1 instanceof NQCandidate && c2 instanceof NQCandidate) {
			this.cross((NQCandidate) c1, (NQCandidate) c2, percent);
		}
	}

	public void cross(NQCandidate c1, NQCandidate c2, double threshold) {
		// lets pick a point, and then swap halves
		int swapPoint = this.random.nextInt(Config.N);

		// swap everything after that point
		int[] c1arr = c1.toArray();
		int[] c2arr = c2.toArray();
		for (int i = swapPoint; i < Config.N; i++) {
			int temp = c2arr[i];
			c2arr[i] = c1arr[i];
			c1arr[i] = temp;
		}
		
		// put them back
		c1.setGenes(c1arr);
		c2.setGenes(c2arr);
	}

	// TODO same as in MonoEvaluator
	@Override
	public void evaluate(Candidate c1) {
		// compute the fitness
		int score = this.fittor.fitness(c1);
		
		// cache the score
		c1.setFitness(score);
	}

	public int maxFitness(){
		return this.fittor.maxFitness();
	}
	
}
