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
import ServeurTwitt.Twitt;

public class ClientTwitt extends UnicastRemoteObject implements Serializable, InterfaceClient{
	public static final int PORT = 2000;
	
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
			new InterfaceGraphique.Connexion(cl1);
			
			/*
			Personne p = new Personne("f4bien", "fabien", "tutu", "1234");
			cl1.interPublic.inscription(p);
			
			cl1.Connexion("f4bien", "1234");
			
			Tweet t1 = new Tweet("#Rien", " de nouveau", cl1.pers);
			cl1.interfPrivee.Tweeter(t1, cl1);
			*/
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
	
	public void incription(Personne p) throws RemoteException{
		interPublic.inscription(p);
	}
	
	public void twitter(Twitt t) throws RemoteException {
		interfPrivee.twitter(t, this);
	}
	
	public void follower(String login) throws RemoteException{
		interfPrivee.follower(login, this);
	}
	
	public ArrayList<Twitt> getListReception(){
		return listeRecu;
	}
}

