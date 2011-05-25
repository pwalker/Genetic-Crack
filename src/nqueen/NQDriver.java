package nqueen;

import java.util.concurrent.LinkedBlockingQueue;

import distribute.Helper;

import basic.Candidate;
import basic.CandidateFeed;
import basic.EvalBarrier;
import basic.Population;
import basic.Driver;

public class NQDriver extends Driver {

	public NQDriver() {
		this.fitness = new NQFitness();
		NQEvaluator nqeval = new NQEvaluator(fitness);
		this.eval = nqeval;
		this.geneTool = nqeval;
		this.b = new EvalBarrier();
		//this.feed = new DummyNQFileFeed("nq_candidates.txt");
		this.feed = null;
		this.output = null;
		this.pop = new Population(100, eval, b, feed);
		this.threads = 2;
		this.help = null;
		
		// initialize the population
		for (int i=0;i<Config.POPULATION_SIZE;i++){
			// use the random constructor
			this.pop.add(new NQCandidate());
		}
	}
	
	public NQDriver(CandidateFeed feed, LinkedBlockingQueue<Candidate> outgoing) {
		this();
		this.pop.setFeed(feed);
		this.output = outgoing;
	}

	// just so you can run this by itself
	public static void main(String args[]){
		NQDriver driver = new NQDriver();
		driver.start();
	}

}
