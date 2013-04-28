package ClientTwitt;

import java.rmi.Remote;
import java.rmi.RemoteException;

import ServeurTwitt.Personne;
import ServeurTwitt.Twitt;

public interface InterfaceClient extends Remote{
	
	/**
	 * Afficher les tweets pour les clients
	 * @param t
	 */
	public void afficherTweetRecu(Twitt t) throws RemoteException;
	
	/**
	 * Retourner la personne responsable de ce tweet
	 * @return
	 * @throws RemoteException
	 */
	public Personne getPersonne() throws RemoteException;
	
}
