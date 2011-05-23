package basic;

public abstract class Fitness {

	public abstract int fitness(Candidate c);

	public abstract int maxFitness();

	public abstract void printSoln(Candidate best);
	
}
