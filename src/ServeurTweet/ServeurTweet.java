package ServeurTweet;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;

public class ServeurTweet extends UnicastRemoteObject {
	
	private ArrayList<Tweet> listeTweet;
	File fichier = new File("tweets.txt");

	protected ServeurTweet() throws RemoteException {
		listeTweet = new ArrayList<Tweet>();
		loadTweet();
	}

	/**
	 * Ajouter un tweet a la liste
	 * @param t
	 */
	private void addTweet(Tweet t){
		listeTweet.add(t);
	}
	
	/**
	 * Supprimer un tweet de la liste
	 * @param t
	 */
	private void supTweet(Tweet t){
		listeTweet.remove(t);
	}
	
	/**
	 * Sauvergarder les tweets dans le fichier
	 */
	private void storeTweet(){
		try {
			ObjectOutputStream os = new ObjectOutputStream(new FileOutputStream(fichier));
			os.writeObject(listeTweet);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * Recuperer les tweet dans le fichiers pour initialiser la liste
	 */
	private void loadTweet(){
		try {
			ObjectInputStream is = new ObjectInputStream(new FileInputStream(fichier));
			listeTweet = (ArrayList<Tweet>) is.readObject();
		} catch (IOException e) {
			listeTweet = new ArrayList<Tweet>();
		} catch (ClassNotFoundException e) {
			listeTweet = new ArrayList<Tweet>();
		}
	}
	
	/**
	 * Fermeture du serveur
	 * Cela a pour effet de sauvergarder la liste des tweets dans un fichier
	 */
	public void close(){
		storeTweet();
	}
	
	
	public void displayAllTweets(){
		for (Tweet t : listeTweet) {
			System.out.println(t);
		}
		System.out.println("\n");
	}
	
	public static void main(String[] args) {
		ServeurTweet serveur = null;
		try {
			serveur = new ServeurTweet();
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		Personne p1 = new Personne("fabien", "fab", "1234");
		Tweet t1 = new Tweet("relartion", "Voici un nouveau message", p1);
		serveur.addTweet(t1);
		serveur.displayAllTweets();
		serveur.close();
	}
}
