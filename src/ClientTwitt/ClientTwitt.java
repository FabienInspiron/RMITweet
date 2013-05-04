package ClientTwitt;

import java.awt.List;
import java.io.Serializable;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;

import ServeurTwitt.ConnexionException;
import ServeurTwitt.InterfacePrivee;
import ServeurTwitt.InterfacePublic;
import ServeurTwitt.Personne;
import ServeurTwitt.RMISSLClientSocketFactory;
import ServeurTwitt.RMISSLServerSocketFactory;
import ServeurTwitt.Twitt;

public class ClientTwitt extends UnicastRemoteObject implements Serializable, InterfaceClient{
	public static final int PORT = 2001;
	
	/**
	 * Le client est une personnne
	 * Il est initialisé lorcequ'il se connect
	 */
	private Personne pers;
	
	/*
	 * Liste contenant les tweets recus
	 */
	ArrayList<Twitt> listeRecu;
	
	private InterfacePublic interPublic;
	private InterfacePrivee interfPrivee;
	
	public ClientTwitt() throws RemoteException{
		super();
		try {
			
			if (System.getSecurityManager() == null) { 
				System.setSecurityManager(new java.rmi.RMISecurityManager()); 
			}
			
			interPublic = (InterfacePublic) Naming.lookup("rmi://localhost:"+PORT+"/MonOD");		
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NotBoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("Client connecté");
		
		pers = new Personne();
		interfPrivee = null;
		listeRecu = new ArrayList<Twitt>();
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
	
	public static void main(String[] args) {
		ClientTwitt cl1 = null;
		try {
			cl1 = new ClientTwitt();
			//new InterfaceGraphique.Connexion(cl1);
			new InterfaceGraphique.Twitter(cl1);

		} catch (RemoteException e) {
			e.printStackTrace();
		} 
	}
	
	public void setPersonne(Personne p){
		pers = p;
	}

	public Personne getPersonne() throws RemoteException{
		return pers;
	}
	
	@Override
	public void afficherTweetRecu(Twitt t) throws RemoteException{
		listeRecu.add(t);
	}
	
	public ArrayList<Twitt> getListReception(){
		return listeRecu;
	}
	
	//Utilisation de l'interface public
	
	public void incription(Personne p) throws RemoteException{
		interPublic.inscription(p);
	}
	
	public ArrayList<Twitt> getTweetTopic(String topic) throws RemoteException{
		return interPublic.getTweetTopic(topic);
	}
	
	public ArrayList<Twitt> getTweetUtilisateur(String utilisateur) throws RemoteException{
		return interPublic.getTweetUtilisateur(utilisateur);
	}
	
	public ArrayList<String> getListTopics() throws RemoteException{
		return interPublic.getListTopics();
	}
	
	public ArrayList<String> getListUtilisateurs() throws RemoteException{
		return interPublic.getListUtilisateurs();
	}
	
	//Utilisation de l'interface privee
	
	public void twitter(Twitt t) throws RemoteException {
		interfPrivee.twitter(t, this);
	}
	
	public void follower(String login) throws RemoteException{
		interfPrivee.follower(login, this);
	}
		
	public ArrayList<Personne> getFollowers(ClientTwitt ct) throws RemoteException{
		return interfPrivee.getFollowers(ct);
	}

}

