package distribute;

import java.util.ArrayList;
import java.util.Collection;
import java.util.concurrent.LinkedBlockingQueue;

import basic.Candidate;
import basic.CandidateFeed;

public class RegularHelper extends Thread implements Helper {

	private Helper[] others;
	private LinkedBlockingQueue<Candidate> input;
	private LinkedBlockingQueue<Candidate> output;

	public RegularHelper(LinkedBlockingQueue<Candidate> incoming,
			LinkedBlockingQueue<Candidate> outgoing) {
		// TODO Auto-generated constructor stub
	}

	/**
	 * Receive candidates!
	 */
	@Override
	public void put(Collection<Candidate> candidates) {
		this.input.addAll(candidates);
	}

	public void run() {
		while (true) {
			// prepare a packet to send
			ArrayList<Candidate> packet = new ArrayList<Candidate>();
			this.output.drainTo(packet, 50);

			for (Helper o : this.others) {
				o.put(packet);
			}
		}
	}

}
