package distribute;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.Collection;

import basic.Candidate;

/**
 * This class will create a driver thread, giving references to i/o queues that
 * it also has. It will register with the server, and handle sending and
 * receiving candidates from the other populations
 * 
 * @author pwalker
 * 
 */
public interface Helper extends Remote {

	void put (Collection<Candidate> candidates) throws RemoteException;

	void kill() throws RemoteException;
	
}
