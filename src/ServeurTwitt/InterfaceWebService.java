package ServeurTwitt;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface InterfaceWebService extends Remote{
	
	/**
	 * 
	 * @return la liste de tous les topics
	 * @throws RemoteException
	 */
	public String getListTopicsString() throws RemoteException;
}
