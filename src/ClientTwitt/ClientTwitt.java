package ClientTwitt;

import java.io.Serializable;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;

import ServeurTwitt.ConnexionException;
import ServeurTwitt.InterfacePrivee;
import ServeurTwitt.InterfacePublic;
import ServeurTwitt.Personne;
import ServeurTwitt.Twitt;

public class ClientTwitt extends UnicastRemoteObject implements Serializable, InterfaceClient{
	
	public static final int PORT = 2001;
	
	/**
	 * Le client est une personnne
	 * Il est initialisÃ© lorcequ'il se connect
	 */
	private Personne pers;
	
	/**
	 * Liste contenant les tweets recus
	 */
	ArrayList<Twitt> listeRecu;
	
	/**
	 * Les interfaces qui permettent d'utiliser les méthodes du serveur
	 */
	private InterfacePublic interPublic;
	private InterfacePrivee interfPrivee;
	
	/**
	 * Constructeur normal
	 * @throws RemoteException
	 */
	public ClientTwitt() throws RemoteException{
		super();
		try {
			
			if (System.getSecurityManager() == null) { 
				System.setSecurityManager(new java.rmi.RMISecurityManager()); 
			}
			
			// 
			interPublic = (InterfacePublic) Naming.lookup("rmi://localhost:"+PORT+"/MonOD");
			
			System.out.println("Client connecté");
			
			//Créer l'objet personne qui représente le client
			pers = new Personne();
			
			//Initialisation des attributs
			interfPrivee = null;
			listeRecu = new ArrayList<Twitt>();
			
			//Lancer l'interface graphique du client
			new InterfaceGraphique.Twitter(this);
			
		}catch(Exception e){
			System.err.println("--- Veuillez lancer le serveur en premier ---");
		}
	}
	
	/**
	 * Lancer le client
	 * @param args
	 */
	public static void main(String[] args) {
		ClientTwitt cl = null;
		try {
			cl = new ClientTwitt();

		} catch (RemoteException e) {
			e.printStackTrace();
		} 
	}
	
	/**
	 * Connexion au client
	 * @param login
	 * @param mdp
	 */
	public void Connexion(String login, String mdp) throws ConnexionException{
		try {
			// Ajout du login et du mot de passe pour se souvenir du client
			pers.setLoginMDP(login, mdp);
			
			interfPrivee = interPublic.connexion(login, mdp);
		} catch (RemoteException e) {
			System.out.println("Impossible de se connecter");
			e.printStackTrace();
		}
	}
	
	/**
	 * Recuperation de l'interface de communication avec le serveur
	 * @return
	 */
	public InterfacePublic getInterface(){
		return interPublic;
	}
	
	/**
	 * Accesseur en écriture de Personne
	 * @param p
	 */
	public void setPersonne(Personne p){
		pers = p;
	}

	/**
	 * Accesseur en lecture de Personne
	 */
	public Personne getPersonne() throws RemoteException{
		return pers;
	}
	
	/**
	 * 
	 */
	@Override
	public void afficherTweetRecu(Twitt t) throws RemoteException{
		listeRecu.add(t);
	}

	/**
	 * Obtenir la liste des tweets reçus
	 * @return
	 */
	public ArrayList<Twitt> getListReception(){
		return listeRecu;
	}
	
	// Utilisation de l'interface public
	// pour le client non authentifié et authentifié
	
	/**
	 * S'incrire
	 * @param p
	 * @throws RemoteException
	 */
	public void incription(Personne p) throws RemoteException{
		interPublic.inscription(p);
	}
	
	/**
	 * Obtenir la liste des tweets sur un topic
	 * @param topic
	 * @return
	 * @throws RemoteException
	 */
	public ArrayList<Twitt> getTweetTopic(String topic) throws RemoteException{
		return interPublic.getTweetTopic(topic);
	}
	
	/**
	 * Obtenir la liste des tweets d'un utilisateur
	 * @param utilisateur
	 * @return
	 * @throws RemoteException
	 */
	public ArrayList<Twitt> getTweetUtilisateur(String utilisateur) throws RemoteException{
		return interPublic.getTweetUtilisateur(utilisateur);
	}
	
	/**
	 * Obtenir la liste de tous les topics
	 * @return
	 * @throws RemoteException
	 */
	public ArrayList<String> getListTopics() throws RemoteException{
		return interPublic.getListTopics();
	}
	
	/**
	 * Obtenir la liste de tous les utilisateurs (ClientTwitt)
	 * @return
	 * @throws RemoteException
	 */
	public ArrayList<String> getListUtilisateurs() throws RemoteException{
		return interPublic.getListUtilisateurs();
	}
	
	// Utilisation de l'interface privee
	// pour le client authentifié
	
	/**
	 * Ecrire un tweet
	 * @param t
	 * @throws RemoteException
	 */
	public void twitter(Twitt t) throws RemoteException {
		interfPrivee.twitter(t, this);
	}
	
	/**
	 * Suivre une personne
	 * @param login
	 * @throws RemoteException
	 */
	public void follower(String login) throws RemoteException{
		interfPrivee.follower(login, this);
	}
	
	/**
	 * Obtenir la liste des followers du ClientTwitt
	 * @param ct
	 * @return
	 * @throws RemoteException
	 */
	public ArrayList<Personne> getFollowers(ClientTwitt ct) throws RemoteException{
		return interfPrivee.getFollowers(ct);
	}

}

