package ServeurTweet;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.HashMap;

public class ServeurTweet extends UnicastRemoteObject implements RMITweetInterface{
	
	private static final long serialVersionUID = 1L;

	public static final int PORT = 2003;
	
	private ArrayList<Tweet> listeTweet;
	private ArrayList<Personne> listePersonne;
	
	/**
	 * Un personne peut suivre ce que fait un autre personne
	 * Ce sont des followers
	 */
	private HashMap<Personne, ArrayList<Personne>> listeFollower;
	
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
	 * Gestion des followers
	 */
	
	/**
	 * Demande pour suivre la personne p
	 * @param p est la personne que l'ont veut suivre
	 * @param personneToFollow est la personne a suivre
	 */
	public void addFollower(Personne p, Personne personneToFollow){
		if(listeFollower.containsKey(personneToFollow)){
			ArrayList<Personne> arr = listeFollower.get(personneToFollow);
			arr.add(p);
		}else{
			ArrayList<Personne> arr = new ArrayList<Personne>();
			arr.add(p);
			listeFollower.put(personneToFollow, arr);
		}
	}
	
	/**
	 * Envoi d'un message aux followers de la personne p
	 * @param p
	 */
	public void sendToFollowers(Personne p, Tweet t){
		ArrayList<Personne> array = listeFollower.get(p);
		for (Personne personne : array) {
			send(personne, t);
		}
	}
	
	/**
	 * Envoi d'un tweet a la personne p
	 * @param p
	 * @param t
	 */
	public void send(Personne p, Tweet t){
		System.out.println("Envoi d'un tweet");
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
	 * Demande de connexion de la part d'un client
	 * Le serveur verifie sa persence dans la base de donnée
	 * @param login
	 * @param mdp
	 * @return
	 */
	public Personne connexion(String login, String mdp){
		for (Personne p : listePersonne) {
			if(p.connect(login, mdp))
				return p;
		}
		
		return null;
	}
		
	/**
	 * Relayer un tweet permet de faire comme si c'etait lui qui avait envoyé le tweet
	 */
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
