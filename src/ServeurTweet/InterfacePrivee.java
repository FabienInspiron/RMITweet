package ServeurTweet;

import java.rmi.Remote;
import java.rmi.RemoteException;

import ClientTweet.ClientTweet;
import ClientTweet.InterfaceClient;

/**
 * Interface disponible lorsque le client est connecté
 * @author belli
 *
 */
public interface InterfacePrivee extends Remote{
	/**
	 * Envoyer un tweet
	 * @param t
	 * @throws RemoteException
	 */
	public void Tweeter(Tweet t, InterfaceClient p) throws RemoteException;
	
	/**
	 * Relayer un tweet, c'est comme si cette personne l'avait envoyé
	 * @param t
	 * @param p
	 * @throws RemoteException
	 */
	public void relayerTweet(Tweet t, ClientTweet p) throws RemoteException;
	
	/**
	 * Follower un tweet c'est s'incrire pour suivre ce qu'envoi une personne
	 * @param p
	 * @throws RemoteException
	 */
	public void Follower(Personne p, ClientTweet c) throws RemoteException;
	
	/**
	 * Permet à une personne de se deconnecter
	 * @param p
	 * @throws RemoteException
	 */
	public void logOff(ClientTweet p) throws RemoteException;
}
