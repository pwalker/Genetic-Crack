package distribute;

import java.util.Collection;
import java.util.HashSet;
import java.util.concurrent.LinkedBlockingQueue;

import basic.Candidate;
import basic.CandidateFeed;

public class QueueFeed implements CandidateFeed {

	private LinkedBlockingQueue<Candidate> queue;

	public QueueFeed(LinkedBlockingQueue<Candidate> incoming) {
		this.queue = incoming;
	}

	@Override
	public boolean hasNext() {
		return this.queue.size() > 0;
	}

	@Override
	public Candidate next() {
		// this won't wait, there either is something, or their isn't.
		return this.queue.poll();
	}

	@Override
	public Collection<Candidate> next(int n) {
		HashSet<Candidate> retval = new HashSet<Candidate>();
		
		this.queue.drainTo(retval, n);
		
		return retval;
	}

}
