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
	}
	
	public static void main(String[] args) {
		ClientTweet cl1 = new ClientTweet();
	}
	
	public void Connexion(String login, String mdp){
		try {
			pers = interfTweet.connexion(login, mdp);
		} catch (RemoteException e) {
			System.out.println("Impossible de se connecter");
			e.printStackTrace();
		}
	}
}

