package ServeurTweet;

import java.rmi.Remote;
import java.rmi.RemoteException;

import ClientTweet.ClientTweet;

/**
 * Interface disponible lorsque le client est connecté
 * @author belli
 *
 */
public interface RMITweetInterfaceTweet extends Remote{
	/**
	 * Envoyer un tweet
	 * @param t
	 * @throws RemoteException
	 */
	public void Tweeter(Tweet t) throws RemoteException;
	
	/**
	 * Relayer un tweet, c'est comme si cette personne l'avait envoyé
	 * @param t
	 * @param p
	 * @throws RemoteException
	 */
	public void relayerTweet(Tweet t, Personne p) throws RemoteException;
	
	/**
	 * Follower un tweet c'est s'incrire pour suivre ce qu'envoi une personne
	 * @param p
	 * @throws RemoteException
	 */
	public void Follower(Personne p, ClientTweet c) throws RemoteException;
}
