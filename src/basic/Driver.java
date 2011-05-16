package basic;

import java.util.Queue;

import nqueen.NQCandidate;

public class Driver extends Thread {

	protected Fitness fitness;
	protected Evaluator eval;
	protected EvalBarrier b;
	protected CandidateFeed feed;
	protected Queue<Candidate> output;
	protected Population pop;
	protected GeneTool geneTool;
	protected Overlord overlord;
	protected int threads = 1;

	/**
	 * You MUST implement a constructor that sets all this up!
	 */

	public void run() {
		// The Overlord object handles pretty much everything
		this.overlord = new Overlord(this.pop, this.eval, this.geneTool,
				this.b, this.threads);

		int max = 0;
		int i;
		for (i = 0; !overlord.isDone(this.fitness.maxFitness()); i++) {
			try {
				overlord.runGeneration();

				NQCandidate bestCand = (NQCandidate) overlord.getBest();

				if (bestCand.getFitness() >= max) {
					System.err.printf("%s\t%s\t%s\t%s\n", i,
							overlord.populationFitness(),
							bestCand.getFitness(), bestCand.getGenes());
					max = bestCand.getFitness();
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

		NQCandidate bestCand = (NQCandidate) overlord.getBest();
		System.out.printf("%s\t%s\t%s\t%s\n", i, overlord.populationFitness(),
				bestCand.getFitness(), bestCand.getGenes());
	}

}
