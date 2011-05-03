package nqueen;

import java.util.Random;

import basic.Candidate;
import basic.Evaluator;
import basic.GeneTool;

public class NQEvaluator implements Evaluator, GeneTool {

	private Random random;

	NQEvaluator(){
		this.random = new Random();
	}
	
	@Override
	public int compare(Candidate o1, Candidate o2) {
		if (o1 instanceof NQCandidate && o2 instanceof NQCandidate){
			return this.compare((NQCandidate) o1, (NQCandidate) o2);
		}
		System.err.println("NQEvaluator.compare(): Casting didn't work!");
		return -1;
	}
	
	// TODO code is the same as in MonoEvaluator
	public int compare(NQCandidate c1, NQCandidate c2){
		// Let's make sure we can actually compare the candidates
		if (!c1.hasFitness()){
			this.evaluate(c1);
		}
		if (!c2.hasFitness()){
			this.evaluate(c2);
		}
		
		return c1.getFitness() - c2.getFitness();
	}

	@Override
	public void mutate(Candidate c) {
		// TODO make this amount configurable, maybe just with a configuration class with a bunch of static finals
		mutate(c, .3);
	}

	@Override
	public void mutate(Candidate c, double percent) {
		// figure out our n
		int n = (int) Math.ceil(c.getGenes().length() * percent);
		
		// Get a random number
		int mutations = this.random.nextInt(n);
		
		char[] chars = c.getGenes().toCharArray();
		for (int i=0; i < mutations; i++){
			int r = this.random.nextInt(c.getGenes().length());
			chars[r] = this.random.nextInt(c.getGenes().length());
		}
	}

	@Override
	public void cross(Candidate c1, Candidate c2) {
		// TODO Auto-generated method stub

	}

	@Override
	public void cross(Candidate c1, Candidate c2, double percent) {
		// TODO Auto-generated method stub

	}

	@Override
	public void evaluate(Candidate c1) {
		// TODO Auto-generated method stub

	}

}
