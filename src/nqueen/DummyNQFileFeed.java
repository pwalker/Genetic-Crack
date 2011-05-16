package nqueen;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Scanner;

import basic.Candidate;
import basic.CandidateFeed;

public class DummyNQFileFeed implements CandidateFeed {

	private Scanner s;

	public DummyNQFileFeed(String filename) {
		try {
			this.s = new Scanner(new File(filename));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			System.exit(1);
		}
	}

	@Override
	public boolean hasNext() {
		return this.s.hasNext();
	}

	@Override
	public NQCandidate next() {
		String nextLine = this.s.nextLine();
		while (nextLine.equals("")) {
			nextLine = this.s.nextLine();
		}
		return new NQCandidate(nextLine);
	}

	/**
	 * This will grab up N Candidates from the file. If less than N are
	 * returned, we ran out by empty line or by EOF
	 */
	@Override
	public Collection<Candidate> next(int n) {
		Collection<Candidate> retVal = new ArrayList<Candidate>();
		for (int i = 0; i < n && this.s.hasNextLine(); i++) {
			String nextLine = this.s.nextLine();
			if (nextLine.equals("")) {
				return retVal;
			}
			retVal.add(new NQCandidate(nextLine));
		}
		return retVal;
	}

}
