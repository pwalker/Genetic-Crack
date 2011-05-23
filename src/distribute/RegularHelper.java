package distribute;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.Collection;
import java.util.concurrent.LinkedBlockingQueue;

import basic.Candidate;
import basic.Driver;

public class RegularHelper extends UnicastRemoteObject implements Runnable, Helper, Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Helper[] others;
	private LinkedBlockingQueue<Candidate> input;
	private LinkedBlockingQueue<Candidate> output;
	private Driver d;
	private boolean cont = true;

	public RegularHelper(IDirectory dir, Driver d, LinkedBlockingQueue<Candidate> incoming,
			LinkedBlockingQueue<Candidate> outgoing) throws RemoteException {
		this.others = dir.register(this);
		System.err.println(others);
		
		this.input = incoming;
		this.output = outgoing;
		
		this.d = d;
	}

	/**
	 * Receive candidates!
	 */
	@Override
	public void put(Collection<Candidate> candidates) {
		this.input.addAll(candidates);
		//System.err.println("Received "+candidates.size()+" new candidates");
	}

	public void run() {
		while (this.cont) {
			// prepare a packet to send
			ArrayList<Candidate> packet = new ArrayList<Candidate>();
			//System.err.println("Draining...");
			this.createPacket(this.output, packet, 30);

			for (Helper o : this.others) {
				if (o != this){
					//System.err.println("Sending to "+o);
					try {
						o.put(packet);
					} catch (RemoteException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		}
	}

	/**
	 * This will block, until it can construct a complete packet
	 * @param queue
	 * @param packet
	 * @param n
	 */
	private void createPacket(LinkedBlockingQueue<Candidate> queue,
			ArrayList<Candidate> packet, int n) {
		for (int i=0; i<n; i++) {
			try {
				packet.add(queue.take().copy());
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public void done(){
		System.err.println("Telling others to stop");
		for (Helper o : this.others) {
			System.err.println("Sending STOP to "+o);
			if (o != this){
				try {
					o.kill();
				} catch (RemoteException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}

	@Override
	public void kill() throws RemoteException {
		System.err.println("Recieved kill");
		this.cont = false;
		this.d.finish();
	}

}
