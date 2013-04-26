package ServeurTweet;

import java.rmi.Remote;
import java.rmi.RemoteException;

import javax.management.OperationsException;

import ClientTweet.ClientTweet;

public interface InterfacePublic extends Remote{
	/**
	 * Connexion d'un client
	 * @param login
	 * @param mdp
	 * @return
	 * @throws RemoteException
	 */
	public InterfacePrivee connexion(String login, String mdp) throws RemoteException, ConnexionException;
	
	/**
	 * Inscription d'un personne au service de tweet
	 * @param p
	 * @throws RemoteException
	 */
	public void inscription(Personne p) throws RemoteException;
}
