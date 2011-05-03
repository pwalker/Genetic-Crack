package nqueen;

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
		int maxNAPairs = 0;
		// for (int i = Config.N-1; i>1; i--){
		// maxNAPairs += i;
		// }
		maxNAPairs = Config.N * (Config.N - 1);

		int tally = maxNAPairs;
		// subtract attacking pairs from the tally
		// HINT: no need to check for vertical attacks, just horizontal
		// count how many are on the same level
		int[] cand = c.toArray();
		for (int i = 0; i < Config.N; i++) {
			// current level to check
			int level = cand[i];

			// check every square on the level
			for (int j = 0; j < Config.N; j++) {
				if (cand[j] == level) {
					tally--;
				}
			}

			tally -= this.diagonalScan(cand, i, level, 1, 1);
			tally -= this.diagonalScan(cand, i, level, -1, -1);
			tally -= this.diagonalScan(cand, i, level, 1, -1);
			tally -= this.diagonalScan(cand, i, level, -1, 1);
		}

		return tally;
	}

	private int diagonalScan(int[] board, int x, int y, int x_dir, int y_dir) {
		int tally = 0;
		// check the up and to the right diagonal
		int curr_x = x + x_dir;
		int curr_y = y + y_dir;
		while ((curr_x < Config.N) && (curr_y < Config.N) && (curr_y >= 0) && (curr_x >= 0)){
			// is there a piece here?
			if (board[curr_x] == curr_y){
				tally++;
			}
			
			// move pointer
			curr_x += x_dir;
			curr_y += y_dir;
		}
		return tally;
	}
}
