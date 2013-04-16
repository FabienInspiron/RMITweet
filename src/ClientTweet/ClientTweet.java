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
	
	public static void main(String[] args) {
		try {
			/*
			 // Assign security manager
		    if (System.getSecurityManager() == null)
		    {
		        System.setSecurityManager   (new RMISecurityManager());
		    }
		    */
			
			RMITweetInterface cd = (RMITweetInterface) Naming.lookup("rmi://localhost:"+PORT+"/MonOD");
			
			System.out.println("Lancement du client ...");
			
			Personne p = new Personne("f4bien", "fabien", "trux", "1234");
			Tweet t1 = new Tweet("test", "Voici un message", p);
			
			cd.addTweet(t1);
			
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
}

