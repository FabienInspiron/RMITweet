package ClientTweet;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RMISecurityManager;
import java.rmi.RemoteException;

import ServeurTweet.Personne;
import ServeurTweet.RMITweetInterface;
import ServeurTweet.Tweet;

public class ClientTweet {
	public static final int PORT = 2003;
	
	/**
	 * Le client est une personnne
	 * Il est initialisé lorcequ'il se connect
	 */
	private Personne pers;
	
	private RMITweetInterface interfTweet;
	
	public ClientTweet(){
		try {
			interfTweet = (RMITweetInterface) Naming.lookup("rmi://localhost:"+PORT+"/MonOD");		
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
	}
	
	/**
	 * Connexion au client
	 * @param login
	 * @param mdp
	 */
	public void Connexion(String login, String mdp){
		try {
			pers = interfTweet.connexion(login, mdp);
			pers.setAdresseIp();
			System.out.println("Vous êtes maintenant connecté : " + pers.getPrenonNom());
		} catch (RemoteException e) {
			System.out.println("Impossible de se connecter");
			e.printStackTrace();
		}
	}
	
	/**
	 * Recuperation de l'interface de communication avec le serveur
	 * @return
	 */
	public RMITweetInterface getInterface(){
		return interfTweet;
	}

	
	public static void main(String[] args) {
		ClientTweet cl1 = new ClientTweet();
		cl1.Connexion("f4bien", "1234");
		Tweet t1 = new Tweet("#Rien", "de nouveau", cl1.pers);
		try {
			cl1.getInterface().Tweeter(t1);
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}

