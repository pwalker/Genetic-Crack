package basic;

import java.util.Comparator;

public interface Evaluator extends Comparator<Candidate> {

	public abstract void evaluate(Candidate c1);

}