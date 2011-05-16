package basic;

import java.util.Collection;

public interface CandidateFeed {

	public abstract boolean hasNext();
	
	public abstract Candidate next();
	
	public abstract Collection<Candidate> next(int n);
	
}
