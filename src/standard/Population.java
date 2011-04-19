package standard;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;

import alphabet.MonoCandidate;
import alphabet.Fitness;


/**
 * This is kind of like an orderable HashSet
 * @author peterw
 *
 */

public class Population {

	private int size;
	
	private HashSet<MonoCandidate> unordered;

	private ArrayList<MonoCandidate> ordered;
	private boolean orderIsCurrent;
	
	public Population(int size){
		this.unordered = new HashSet<MonoCandidate>();
		this.ordered = new ArrayList<MonoCandidate>();
		this.orderIsCurrent = false;
		
		this.size = size;
	}
	
	public void add(MonoCandidate c){
		if (this.currentSize() > this.idealSize()){
			throw new UnsupportedOperationException("no room in population");
		}
		
		this.unordered.add(c);
		this.orderIsCurrent = false;
	}
	
	public Collection<MonoCandidate> getCandidates(){
		return this.unordered;
	}

	public ArrayList<MonoCandidate> sortCandidates(){
		if (!this.orderIsCurrent){
			// refresh our list
			this.ordered.clear();
			this.ordered.addAll(this.unordered);
			
			// sort it
			Collections.sort(this.ordered);
			
			// update cache indicator
			this.orderIsCurrent = true;
		}
		return this.ordered;
	}
	
	/**
	 * Given a collection of candidates
	 * @param select
	 */
	public void carryOver(Collection<MonoCandidate> select) {
		this.unordered = new HashSet<MonoCandidate>(select);
		this.ordered.clear();
		this.orderIsCurrent = false;
	}

	public int currentSize() {
		return this.unordered.size();
	}
	
	public int idealSize(){
		return this.size;
	}

	public Iterator<MonoCandidate> unOrderedIterator() {
		return this.unordered.iterator();
	}
	
	public String toString(){
		return "Pop has " + this.currentSize() + "/" + this.idealSize();
	}
	
	public String popString(){
		String retVal = "";
		for (MonoCandidate c : this.unordered){
			retVal += c+"\n";
		}
		return retVal;
	}

	public void clearOrdering() {
		this.orderIsCurrent = false;
		this.ordered.clear();
	}
	
}
