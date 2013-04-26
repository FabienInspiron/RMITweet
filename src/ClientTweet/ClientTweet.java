package ClientTweet;

import java.io.Serializable;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

import ServeurTweet.ConnexionException;
import ServeurTweet.Personne;
import ServeurTweet.RMITweetInterfaceClient;
import ServeurTweet.RMITweetInterfaceDeConnexion;
import ServeurTweet.RMITweetInterfaceTweet;
import ServeurTweet.Tweet;

public class ClientTweet extends UnicastRemoteObject implements Serializable, RMITweetInterfaceClient{
	public static final int PORT = 2003;
	
	/**
	 * Le client est une personnne
	 * Il est initialisé lorcequ'il se connect
	 */
	private Personne pers;
	
	private RMITweetInterfaceDeConnexion interfConnexion;
	private RMITweetInterfaceTweet interfTweet;
	
	public ClientTweet() throws RemoteException{
		super();
		try {
			interfConnexion = (RMITweetInterfaceDeConnexion) Naming.lookup("rmi://localhost:"+PORT+"/MonOD");		
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
		pers = null;
		
		interfTweet = null;
	}
	
	/**
	 * Connexion au client
	 * @param login
	 * @param mdp
	 */
	public void Connexion(String login, String mdp) throws ConnexionException{
		try {
			interfTweet = interfConnexion.connexion(login, mdp, this);
		} catch (RemoteException e) {
			System.out.println("Impossible de se connecter");
			e.printStackTrace();
		}
	}
	
	/**
	 * Recuperation de l'interface de communication avec le serveur
	 * @return
	 */
	public RMITweetInterfaceDeConnexion getInterface(){
		return interfConnexion;
	}
	
	public static void main(String[] args) {
		ClientTweet cl1 = null;
		try {
			cl1 = new ClientTweet();
			cl1.Connexion("f4bien", "12234");
			Tweet t1 = new Tweet("#Rien", "de nouveau", cl1.pers);
			cl1.interfTweet.Tweeter(t1, cl1);
			cl1.interfConnexion.logOff(cl1);
			
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ConnexionException e1) {
			System.out.println("Login ou mot de passe incorrect");
		}
	}
	
	public Personne getPersonne(){
		return pers;
	}

	@Override
	public void afficherTweetRecu(Tweet t) {
		System.out.println("Vous avez reçu un tweet : ");
		System.out.println(t);
	}
}

