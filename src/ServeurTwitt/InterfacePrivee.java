package ServeurTwitt;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;

import ClientTwitt.ClientTwitt;
import ClientTwitt.InterfaceClient;

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
	public void twitter(Twitt t, InterfaceClient p) throws RemoteException;
	
	/**
	 * Relayer un tweet, c'est comme si cette personne l'avait envoyé
	 * @param t
	 * @param p
	 * @throws RemoteException
	 */
	public void relayerTweet(Twitt t, InterfaceClient p) throws RemoteException;
	
	/**
	 * Follower un tweet c'est s'incrire pour suivre ce qu'envoi une personne
	 * @param login est le pseudo de la personne que l'on souhaite suivre
	 * @throws RemoteException
	 */
	public void follower(String login, InterfaceClient c) throws RemoteException;
	
	/**
	 * Retourne la liste des followers d'un client ct
	 * @param ct
	 * @return
	 * @throws RemoteException
	 */
	public ArrayList<Personne> getFollowers(InterfaceClient ct) throws RemoteException;
	
	/**
	 * Permet à une personne de se deconnecter
	 * @param p
	 * @throws RemoteException
	 */
	public void logOff(ClientTwitt p) throws RemoteException;
}
