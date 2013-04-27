package ClientTweet;

import java.rmi.Remote;
import java.rmi.RemoteException;

import ServeurTweet.Personne;
import ServeurTweet.Tweet;

public interface InterfaceClient extends Remote{
	
	/**
	 * Afficher les tweets pour les clients
	 * @param t
	 */
	public void afficherTweetRecu(Tweet t) throws RemoteException;
	
	/**
	 * Retourner la personne responsable de ce tweet
	 * @return
	 * @throws RemoteException
	 */
	public Personne getPersonne() throws RemoteException;
	
}
