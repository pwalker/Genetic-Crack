package basic;

import java.util.Queue;

import distribute.Helper;

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
	protected Helper help;
	protected boolean isDone = false;

	/**
	 * You MUST implement a constructor that sets all this up!
	 */

	public void run() {
		// The Overlord object handles pretty much everything
		this.overlord = new Overlord(this.pop, this.eval, this.geneTool,
				this.b, this.threads);

		int max = 0;
		int i;
		for (i = 0; !this.overlord.isDone(this.fitness.maxFitness()) && !this.isDone ; i++) {
			try {
				this.overlord.runGeneration();
				//System.err.print(".");
				
				Candidate bestCand = (Candidate) this.overlord.getBest();

				if (bestCand.getFitness() >= max) {
					System.err.printf("\n%s\t%s\t%s\t%s\n", i,
							this.overlord.populationFitness(),
							bestCand.getFitness(), /*bestCand.getGenes()*/ "no genes");
					max = bestCand.getFitness();
					
					// put some candidates into the output (top 5% of them)
					if (this.output != null){
						int n = (int) Math.floor(this.pop.getSize() * .05);
						System.err.println("Adding "+n+" to outgoing queue!");
						this.output.addAll(this.overlord.topCopy(n));
					}
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

		this.fitness.printSoln(this.overlord.getBest());

	}

	public void giveHelper(Helper h){
		this.help = h;
	}

	// don't run anymore generations!
	public void finish() {
		System.err.println("finishing");
		this.isDone = true;
	}

}
