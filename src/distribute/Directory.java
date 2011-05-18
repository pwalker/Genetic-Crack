package distribute;

import java.io.Serializable;
import java.rmi.AlreadyBoundException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

import basic.EvalBarrier;

public class Directory extends UnicastRemoteObject implements Remote, Serializable, IDirectory {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	EvalBarrier b;
	private Helper[] helpers;

	public Directory(int n) throws RemoteException {
		this.b = new EvalBarrier();
		b.newMax(n);
		this.helpers = new Helper[n];
	}

	/* (non-Javadoc)
	 * @see distribute.IDirectory#register(distribute.Helper)
	 */
	@Override
	public Helper[] register(Helper h) {
		// let the barrier know we have registered
		synchronized (this) {
			this.b.inc();

			// find an open spot, and put a reference to the given helper there
			for (int i = 0; i < this.helpers.length; i++) {
				if (this.helpers[i] == null) {
					this.helpers[i] = h;
					break;
				}
			}
			
			System.err.println(h+" registerd!");
		}

		// pause for others to register
		this.b.pause();
		
		return this.helpers;
	}

	/**
	 * @param args
	 * @throws RemoteException
	 * @throws AlreadyBoundException
	 * @throws InterruptedException
	 */
	public static void main(String[] args) throws RemoteException,
			AlreadyBoundException, InterruptedException {
		System.err.println("Registry port: " + Registry.REGISTRY_PORT);
		Registry registry = LocateRegistry
				.createRegistry(Registry.REGISTRY_PORT);

		IDirectory directory = new Directory(2);
		String s = "RegionDirectory";

		registry.bind(s, directory);
		for (String obj : registry.list()) {
			System.out.println(obj + " is bound");
		}

		// keep running till we are done.
		((Directory)directory).allConnect();

	}

	// block until all the threads have connected.
	// TODO do I need to register to be synchronized or something? will this
	// ever terminate before the other register methods have finished?
	private void allConnect() {
		this.b.pause();
	}

}
