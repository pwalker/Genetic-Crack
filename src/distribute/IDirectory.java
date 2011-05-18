package distribute;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface IDirectory extends Remote {

	public abstract Helper[] register(Helper h) throws RemoteException;

}