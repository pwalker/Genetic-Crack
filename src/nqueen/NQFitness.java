package nqueen;

import java.util.HashSet;

import basic.Candidate;
import basic.Fitness;

public class NQFitness extends Fitness {

	/**
	 * We are trying to maximize the number of non-attacking pairs.
	 */
	@Override
	public int fitness(Candidate c) {
		if (c instanceof NQCandidate) {
			return this.fitness((NQCandidate) c);
		}
		// TODO error!
		return -1;
	}

	public int fitness(NQCandidate c) {
		// Ideally, given Config.N, what is the best we can do?
		int maxNAPairs = this.maxFitness();

		//int tally = maxNAPairs;
		// subtract attacking pairs from the tally
		// HINT: no need to check for vertical attacks, just horizontal
		// count how many are on the same level
		HashSet<String> attackingPairs = new HashSet<String>();
		
		int[] cand = c.toArray();
		for (int i = 0; i < Config.N; i++) {
			// current level to check
			int level = cand[i];

			// check every square on the level
			for (int j = 0; j < Config.N; j++) {
				if (cand[j] == level && j != i) {
					// forwards
					attackingPairs.add(i+","+level+" "+j+","+level);
					// backwards
					attackingPairs.add(j+","+level+" "+i+","+level);
				}
			}

			//tally -= this.diagonalScan(cand, i, level, 1, 1);
			//tally -= this.diagonalScan(cand, i, level, -1, -1);
			//tally -= this.diagonalScan(cand, i, level, 1, -1);
			//tally -= this.diagonalScan(cand, i, level, -1, 1);
			attackingPairs.addAll(this.diagonalScan(cand, i, level, 1, 1));
			attackingPairs.addAll(this.diagonalScan(cand, i, level, -1, -1));
			attackingPairs.addAll(this.diagonalScan(cand, i, level, 1, -1));
			attackingPairs.addAll(this.diagonalScan(cand, i, level, -1, 1));
		}

		//return tally;
		return maxNAPairs-(attackingPairs.size()/2);
	}

	private HashSet<String> diagonalScan(int[] board, int x, int y, int x_dir, int y_dir) {
		//int tally = 0;
		HashSet<String> retVal = new HashSet<String>();
		// check the up and to the right diagonal
		int curr_x = x + x_dir;
		int curr_y = y + y_dir;
		while ((curr_x < Config.N) && (curr_y < Config.N) && (curr_y >= 0) && (curr_x >= 0)){
			// is there a piece here?
			if (board[curr_x] == curr_y){
				//tally++;
				retVal.add(x+","+y+" "+curr_x+","+curr_y); // forwards
				retVal.add(curr_x+","+curr_y+" "+x+","+y); // backwards
			}
			
			// move pointer
			curr_x += x_dir;
			curr_y += y_dir;
		}
		//return tally;
		return retVal;
	}
	
    private int factSum(int n) {
    	if (n <= 1) {
    	    return 1;
    	} else {
    	    return n + this.factSum(n-1);
    	}
    }

	@Override
	public int maxFitness() {
		return this.factSum(Config.N-1);
	}
}
