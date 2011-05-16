package distribute;

import java.rmi.AlreadyBoundException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import basic.EvalBarrier;

public class Directory implements Remote {

	EvalBarrier b;
	private Helper[] helpers;
	
	public Directory(int n){
		this.b = new EvalBarrier();
		b.newMax(n);
		this.helpers = new Helper[n];
	}
	
	public Helper[] register(Helper h){
		// let the barrier know we have registered
		synchronized (this) {
			this.b.inc();
			
			// find an open spot, and put a reference to the given helper there
			for (int i=0; i< this.helpers.length;i++){
				if (this.helpers[i] == null){
					this.helpers[i] = h;
					break;
				}
			}
		}
		
		// pause for others to register
		this.b.pause();
		
		return this.helpers;	
	}
	
	/**
	 * @param args
	 * @throws RemoteException 
	 * @throws AlreadyBoundException 
	 */
	public static void main(String[] args) throws RemoteException, AlreadyBoundException {
		System.err.println("Registry port: "+Registry.REGISTRY_PORT);
		Registry registry=LocateRegistry.createRegistry(Registry.REGISTRY_PORT);

		Directory directory = new Directory(4);
		String s = "RegionDirectory";
		
		registry.bind(s, directory);
		for (String obj : registry.list()) {
			System.out.println(obj +" is bound");
		}
		
	}

}
