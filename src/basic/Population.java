package basic;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Random;
import java.util.concurrent.LinkedBlockingQueue;

import nqueen.NQCandidate;
import nqueen.NQEvaluator;

public class Population {

	private int popSize;
	private ArrayList<Candidate> list;
	private Comparator<Candidate> comparator;
	private LinkedBlockingQueue<Candidate> work;
	private EvalBarrier barrier;
	private CandidateFeed feed;

	public Population(int size, Comparator<Candidate> comp, EvalBarrier b, CandidateFeed feed) {
		this.popSize = size;

		// make our ArrayList, to size
		this.list = new ArrayList<Candidate>();
		this.list.ensureCapacity(size);

		this.comparator = comp;

		this.work = new LinkedBlockingQueue<Candidate>();

		this.barrier = b;

		// we aren't using a feed

		this.feed = feed;
	}

	public void add(Candidate c) {
		// TODO check to make sure we don't go over size!

		this.list.add(c);
	}

	public LinkedBlockingQueue<Candidate> getWorkQueue() {
		return this.work;
	}

	public void crossoverFill(GeneTool geneTool) throws Exception {
		int n = this.popSize - this.list.size();

		// let's see if we can fill up the population from the feed, before our
		// selection
		if (this.feed != null) {
			// save the original size;
			int originalSize = this.list.size();

			// take as many as we can from the feed
			this.list.addAll(this.feed.next(n));

			if ((this.list.size() - originalSize) > 0){
				System.err.println("Trying to incorporate "+(this.list.size() - originalSize)+" new candidates");
			}
			
			// trim the fat
			// put the weak candidates at the end of the list
			//Collections.sort(this.list, this.comparator);
			this.sortCandidates();
			Collections.reverse(this.list);
			// remove down to the original selected size!
			while (this.list.size() > originalSize) {
				this.list.remove(originalSize);
			}
		}

		// since we want duplicates, make some copies
		for (Candidate c : this.throwDarts(n)) {
			this.list.add(c.copy());
		}

		// perform some crossovers
		Collections.shuffle(this.list);
		for (int i = 0; i < (this.list.size() - 1); i += 2) {
			geneTool.cross(this.list.get(i), this.list.get(i + 1));
		}

	}

	public Collection<Candidate> getCandidates() {
		return this.list;
	}

	public int getSize() {
		return this.popSize;
	}

	// TODO these methods should be elsewhere
	/*
	 * public void loadPopulation(String file) { // get rid of our current
	 * population this.list.clear();
	 * 
	 * try { File input = new File(file);
	 * 
	 * Scanner s = new Scanner(input); // TODO check to make sure we don't go
	 * over size! or should we load // everything, and then update the size
	 * while (s.hasNext()) { // TODO how can make this be a MonoCandidate?
	 * Candidate c = new Candidate(s.next()); c.setFitness(s.nextInt());
	 * this.add(c); }
	 * 
	 * s.close();
	 * 
	 * } catch (Exception e) { }
	 * 
	 * this.list.trimToSize(); }
	 */

	public void mutate(GeneTool geneTool, double percent) throws Exception {
		// how many do we want?
		int n = (int) Math.floor(this.list.size() * percent);

		// lets get what we want to mutate
		ArrayList<Candidate> toMutate = this.throwDarts(n);

		for (Candidate c : toMutate) {
			geneTool.mutate(c);
		}

	}

	/*
	 * public void savePopulation(String file) { // sort things so we are
	 * guaranteed a fitness //Collections.sort(this.list);
	 * this.evaluateCandidates();
	 * 
	 * // try and open the file try { File output = new File(file);
	 * output.createNewFile();
	 * 
	 * PrintWriter out = new PrintWriter(output);
	 * 
	 * out.println("Genes,fitness");
	 * 
	 * for (Candidate c : this.list) {
	 * out.println(c.getGenes()+","+c.getFitness()); }
	 * 
	 * out.close();
	 * 
	 * } catch (Exception e) { } }
	 */

	public void select(double percent) throws Exception {
		// lets sort our list
		// this.sortCandidates();
		// TODO I don't think we need to sort anything

		// how many do we want?
		int n = (int) Math.floor(this.list.size() * percent);

		// make a new list
		ArrayList<Candidate> newList = new ArrayList<Candidate>();

		// will want it to be at least as big as this one
		newList.ensureCapacity(this.list.size());

		// fill it up
		newList.addAll(throwDarts(n));

		this.list = newList;
	}

	public ArrayList<Candidate> sortCandidates() {
		// give them all a fitness
		this.evaluateCandidates();

		Collections.sort(this.list, this.comparator);
		return this.list;
	}

	private ArrayList<Candidate> throwDarts(int n) throws Exception {
		// Let's sort the list, so we candidates have valid fitness numbers
		// Collections.sort(this.list);
		// Lets add all the Candidate's with no fitness numbers to the queue, I
		// think we need to block on this
		this.evaluateCandidates();
		// mix them for random's sake
		Collections.shuffle(this.list);

		// lets get the total fitness
		int totalFit = 0;
		for (Candidate c : this.list) {
			// lets make sure it all works right.
			if (c.getFitness() < 0) {
				throw new Exception("Not all candidates have been evaluated");
			}
			// totalFit += Math.max(c.getFitness(), 1);
			// totalFit += c.getFitness()+1;
			totalFit += c.getFitness();
		}

		// setup our new population
		ArrayList<Candidate> retVal = new ArrayList<Candidate>();
		retVal.ensureCapacity(this.list.size());

		// throw some darts
		Random r = new Random();
		while (retVal.size() < n) {
			int rand = r.nextInt(Math.max(2,totalFit));

			throwDart: for (Candidate c : this.list) {
				// did we land on the right one?
				if (rand < c.getFitness()) {
					// retVal.add(c.copy());
					retVal.add(c);
					break throwDart;
				}

				// skip to the next wheel position.
				// rand = rand - Math.max(c.getFitness(), 1);
				// rand = rand - c.getFitness() + 1;
				rand = rand - c.getFitness();
			}
		}

		return retVal;
	}

	private void evaluateCandidates() {
		// put candidates in the queue!
		try {
			// figure out what we are doing
			ArrayList<Candidate> queue = this.queueCandidates(this.list);

			this.barrier.reset(queue.size());

			// generate our work
			for (Candidate c : queue) {
				this.work.put(c);
			}

			// wait till the queue gets done
			this.barrier.pause();

		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private ArrayList<Candidate> queueCandidates(ArrayList<Candidate> incoming)
			throws InterruptedException {
		ArrayList<Candidate> acc = new ArrayList<Candidate>();
		for (Candidate c : incoming) {
			if (c.getFitness() < 0) {
				acc.add(c);
			}
		}
		return acc;
	}

	public int totalFitness() {
		// ensure that everyon has a fitness
		this.evaluateCandidates();

		// sum them up!
		int retVal = 0;
		for (Candidate c : this.list) {
			retVal += c.getFitness();
		}
		return retVal;
	}

	public boolean containsFitness(int fit) {
		// ensure we have some kind of ordering
		this.evaluateCandidates();

		for (Candidate c : this.list) {
			if (c.getFitness() >= fit) {
				return true;
			}
		}
		return false;
	}

	public void setFeed(CandidateFeed feed2) {
		this.feed = feed2;
	}

}
