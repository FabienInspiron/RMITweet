package ClientTwitt;

import java.io.Serializable;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;

import javax.security.auth.Subject;
import javax.security.auth.login.LoginException;

import Interfaces.InterfaceClient;
import Interfaces.InterfacePrivee;
import Interfaces.InterfacePublic;
import ServeurTwitt.ConnexionException;
import ServeurTwitt.Personne;
import ServeurTwitt.Twitt;

public class ClientTwitt extends UnicastRemoteObject implements Serializable, InterfaceClient{
	
	public static final int PORT = 2001;
	
	/**
	 * Le client est une personnne
	 * Il est initialisé lorcequ'il se connect
	 */
	private Personne pers;
	
	/**
	 * Liste contenant les tweets recus
	 */
	ArrayList<Twitt> listeRecu;
	
	/**
	 * Identification du client sur le serveur
	 */
	Subject sujet;
	
	/**
	 * Les interfaces qui permettent d'utiliser les m�thodes du serveur
	 */
	private InterfacePublic interPublic;
	private InterfacePrivee interPrivee;
	
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
			
			//Cr�er l'objet personne qui repr�sente le client
			pers = new Personne();
			
			//Initialisation des attributs
			interPrivee = null;
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
	 * 
	 * @param login
	 * @param mdp
	 * @throws LoginException
	 * @throws RemoteException
	 */
	public void Connexion(String login, String mdp) throws ConnexionException,
			RemoteException, LoginException {
		// Ajout du login et du mot de passe pour se souvenir du client
		pers.setLoginMDP(login, mdp);
		sujet = interPublic.logon(login, mdp);
		interPrivee = interPublic.getService(sujet);
	}

	/**
	 * Recuperation de l'interface de communication avec le serveur
	 * @return
	 */
	public InterfacePublic getInterface(){
		return interPublic;
	}
	
	/**
	 * Accesseur en �criture de Personne
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
	 * Obtenir la liste des tweets re�us
	 * @return
	 */
	public ArrayList<Twitt> getListReception(){
		return listeRecu;
	}
	
	// Utilisation de l'interface public
	// pour le client non authentifi� et authentifi�
	
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
	// pour le client authentifi�
	
	/**
	 * Ecrire un tweet
	 * @param t
	 * @throws RemoteException
	 */
	public void twitter(Twitt t) throws RemoteException {
		interPrivee.twitter(t, this, sujet);
	}
	
	/**
	 * Suivre une personne
	 * @param login
	 * @throws RemoteException
	 */
	public void follower(String login) throws RemoteException{
		interPrivee.follower(login, this, sujet);
	}
	
	/**
	 * Obtenir la liste des followers du ClientTwitt
	 * @param ct
	 * @return
	 * @throws RemoteException
	 */
	public ArrayList<Personne> getFollowers(ClientTwitt ct) throws RemoteException{
		return interPrivee.getFollowers(ct, sujet);
	}

}

