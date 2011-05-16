package nqueen;

import java.util.concurrent.LinkedBlockingQueue;

import distribute.QueueFeed;
import distribute.RegularHelper;
import distribute.Region;

import basic.Candidate;
import basic.CandidateFeed;

public class NQRegion extends Region {

	NQRegion() {
		LinkedBlockingQueue<Candidate> incoming = new LinkedBlockingQueue<Candidate>();
		LinkedBlockingQueue<Candidate> outgoing = new LinkedBlockingQueue<Candidate>();
		
		CandidateFeed feed = new QueueFeed(incoming);
		
		// create helper
		this.h = new RegularHelper(incoming, outgoing);
		
		// create driver
		this.d = new NQDriver(feed, outgoing);	
	}
	
	public void run(){
		// tell helper to connect to a server?
		
		this.d.setDaemon(true);
		this.d.start();
	}
	
	public static void main(String args[]){
		NQRegion r = new NQRegion();
		r.start();
	}
	
}
