package ServeurTweet;

import java.awt.List;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.Array;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.RMISecurityManager;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;

import org.w3c.dom.ls.LSInput;

public class ServeurTweet extends UnicastRemoteObject implements RMITweetInterface{
	
	public static final int PORT = 2003;
	
	private ArrayList<Tweet> listeTweet;
	private ArrayList<Personne> listePersonne;
	
	File fichierTweet = new File("tweets.txt");
	File fichierPersonnes = new File("personnes.txt");

	protected ServeurTweet() throws RemoteException {
		listeTweet = new ArrayList<Tweet>();
		listePersonne = new ArrayList<Personne>();
		loadTweet();
		loadPersonne();
	}

	/**
	 * Ajouter un tweet a la liste
	 * @param t
	 */
	public void addTweet(Tweet t){
		System.out.println("Ajout d'un tweet");
		listeTweet.add(t);
	}
	
	/**
	 * Ajouter une personne a la liste
	 * @param p
	 */
	public void addPersonne(Personne p){
		listePersonne.add(p);
	}
	
	/**
	 * Savoir si la personne peut se connecter
	 * @param p
	 * @return
	 */
	public boolean connexion(Personne p){
		return listePersonne.contains(p);
	}
	
	/**
	 * Savoir si la personne avec le pseudo login peut se connecter
	 * @param login
	 * @param mdp
	 * @return
	 */
	public boolean connexion(String login, String mdp){
		for (Personne p : listePersonne) {
			if(p.getPseudo().equals(login))
				if(p.getMdp().equals(mdp))
					return true;
		}
		
		return false;
	}
		
	public void relayerTweet(Tweet t, Personne p){
		t.personne = p;
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
	private void store(File fichier, ArrayList array){
		try {
			ObjectOutputStream os = new ObjectOutputStream(new FileOutputStream(fichier));
			os.writeObject(array);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * Recuperer les tweet dans le fichiers pour initialiser la liste
	 */
	private void load(File fichier, ArrayList array){
		try {
			ObjectInputStream is = new ObjectInputStream(new FileInputStream(fichier));
			array = (ArrayList) is.readObject();
		} catch (IOException e) {
			array = new ArrayList();
		} catch (ClassNotFoundException e) {
			array = new ArrayList();
		}
	}
	
	private void storeTweet(){
		store(fichierTweet, listeTweet);
	}
	
	private void storePersonne(){
		store(fichierPersonnes, listePersonne);
	}
	
	private void loadTweet(){
		load(fichierTweet, listeTweet);
	}
	
	private void loadPersonne(){
		load(fichierPersonnes, listePersonne);
	}
	
	/**
	 * Fermeture du serveur
	 * Cela a pour effet de sauvergarder la liste des tweets dans un fichier
	 */
	public void close(){
		storeTweet();
		storePersonne();
	}
	
	/**
	 * Afficher tous les tweets present dans la table
	 */
	public void displayAllTweets(){
		for (Tweet t : listeTweet) {
			System.out.println(t);
		}
		System.out.println("\n");
	}
	
	public static void main(String[] args) {
		ServeurTweet s;
		try {
			s = new ServeurTweet();
			
			Personne p1 = new Personne("f4bien", "fabien", "tutu", "1234");
			Tweet t1 = new Tweet("topic","message", p1);
			
			s.addPersonne(p1);
			s.addTweet(t1);
			
			System.out.println(s.listePersonne.size());
			System.out.println(s.listeTweet.size());
			s.close();
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public static void main2(String[] args) {
		RMITweetInterface rm;
		try {
			Registry reg=LocateRegistry.createRegistry(PORT);
			
			/*
			 // Assign security manager
		    if (System.getSecurityManager() == null)
		    {
		        System.setSecurityManager   (new RMISecurityManager());
		    }
		    */
			
			rm = new ServeurTweet();
			
			try {
				Naming.rebind("rmi://localhost:"+PORT+"/MonOD", rm);
				
				System.out.println("Serveur lancé sur le port " + PORT);
				
			} catch (MalformedURLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		} catch (RemoteException e) {
			e.printStackTrace();
		}finally{
			//rm.close();
		}
	}
}
