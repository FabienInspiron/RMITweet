package ServeurTweet;

import java.rmi.Remote;
import java.rmi.RemoteException;

import javax.management.OperationsException;

import ClientTweet.ClientTweet;

public interface RMITweetInterfaceDeConnexion extends Remote{
	/**
	 * Connexion d'un client
	 * @param login
	 * @param mdp
	 * @return
	 * @throws RemoteException
	 */
	public RMITweetInterfaceTweet connexion(String login, String mdp, ClientTweet cl) throws RemoteException, ConnexionException;
	
	/**
	 * Retourne l'interface nécessaire pour le client
	 * @return
	 * @throws RemoteException, OperationException
	 */
	public ClientTweet getPersonne(String login, String mdp) throws RemoteException, ConnexionException;
	
	/**
	 * Inscription d'un personne au service de tweet
	 * @param p
	 * @throws RemoteException
	 */
	public void inscription(ClientTweet p) throws RemoteException;
	
	/**
	 * Permet à une personne de se deconnecter
	 * @param p
	 * @throws RemoteException
	 */
	public void logOff(ClientTweet p) throws RemoteException;
}
