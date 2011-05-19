package nqueen;

import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.concurrent.LinkedBlockingQueue;

import distribute.IDirectory;
import distribute.QueueFeed;
import distribute.RegularHelper;
import distribute.Region;

import basic.Candidate;
import basic.CandidateFeed;

public class NQRegion extends Region {

	NQRegion(IDirectory dir) throws RemoteException {
		LinkedBlockingQueue<Candidate> incoming = new LinkedBlockingQueue<Candidate>();
		LinkedBlockingQueue<Candidate> outgoing = new LinkedBlockingQueue<Candidate>();
		
		CandidateFeed feed = new QueueFeed(incoming);
		
		// create driver
		this.d = new NQDriver(feed, outgoing);

		// create helper
		this.h = new RegularHelper(dir, this.d, incoming, outgoing);
		
		this.d.giveHelper(this.h);
	}
	
	public void run(){
		// tell helper to connect to a server?
		
		//this.d.setDaemon(true);
		this.d.start();
		if (this.h instanceof RegularHelper){
			Thread t = new Thread((RegularHelper)this.h);
			t.start();
		}
	}
	
	public static void main(String args[]) {
		//String s = "rmi://149.43.80.163/MessageDirectory";
	    String s = "rmi://localhost/RegionDirectory";

		// Look up server at the well-known location
		IDirectory server;
		try {
			server = (IDirectory)java.rmi.Naming.lookup(s);
			// create a local client
			NQRegion r = new NQRegion(server);
			System.err.println("Connected to "+server+", starting thread...");
			r.start();
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NotBoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
