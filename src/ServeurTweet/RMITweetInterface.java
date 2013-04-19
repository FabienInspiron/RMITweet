package ServeurTweet;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface RMITweetInterface extends Remote{
	public void Tweeter(Tweet t) throws RemoteException;
	public void relayerTweet(Tweet t, Personne p) throws RemoteException;
	public Personne connexion(String login, String mdp) throws RemoteException;
	public void inscription(Personne p) throws RemoteException;
}
