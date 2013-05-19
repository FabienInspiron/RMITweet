package ServeurTwitt;

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
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Scanner;

import ClientTwitt.ClientTwitt;
import ClientTwitt.InterfaceClient;

public class ServeurTwitt extends UnicastRemoteObject implements InterfacePublic, InterfacePrivee, InterfaceWebService{
	
	private static final long serialVersionUID = 1L;
	
	public static final int PORT = 2001;
	
	private ArrayList<Twitt> listeTweet;
	private ArrayList<Personne> listePersonne;
	private HashSet<String> listeTopic;
	
	/**
	 * Un personne peut suivre ce que fait un autre personne
	 * Ce sont des followers
	 * Le premier paramètre correspond au login de la personne
	 */
	private HashMap<String, ArrayList<InterfaceClient>> listeFollower;
	
	/**
	 *  Liste des fichiers pour la sauvegarde
	 */
	File fichierTweet = new File("tweets.txt");
	File fichierPersonnes = new File("personnes.txt");

	/**
	 * Constructeur normal
	 * @throws RemoteException
	 */
	protected ServeurTwitt(RMISSLClientSocketFactory cl, RMISSLServerSocketFactory sr) throws RemoteException {
		super(0,cl,sr);
		
		listeTweet = new ArrayList<Twitt>();
		listePersonne = new ArrayList<Personne>();
		listeFollower = new HashMap<String, ArrayList<InterfaceClient>>();
		listeTopic = new HashSet<String>();
		loadTweet();
		loadPersonne();
	}

	/**
	 * Ajouter un tweet a la liste en verifiant 
	 * que le client à la possibilité de le faire
	 * 
	 * @param t
	 */
	public void twitter(Twitt t, InterfaceClient c){
		/**
		 * Verifire que le tweet à été envoyé par la bonne personne
		 */
		try {
			if(!exist(c.getPersonne())) {
				System.out.println("Impossible de twitter " + c.getPersonne());
				return;
			}
			
			System.out.println(c.getPersonne().getPseudo() + " a ajouté un nouveau tweet");

			listeTweet.add(t);
			listeTopic.add(t.getTopic());
			
			sendToFollowers(c.getPersonne().getPseudo(), t);
			
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * Ajouter une personne a la liste
	 * @param p
	 */
	public void addPersonne(Personne p){
		listePersonne.add(p);
	}
	
	/**
	 * Demande pour suivre la personne p
	 * @param p est la personne que l'ont veut suivre
	 * @param personneToFollow est la personne a suivre
	 */
	public void addFollower(String login, InterfaceClient cl) throws RemoteException{
		if(!alreadyLogin(login))
			if(listeFollower.get(login) != null){
				ArrayList<InterfaceClient> arr = listeFollower.get(login);
				arr.add(cl);
				System.out.println("Creation");
			}else{
				ArrayList<InterfaceClient> arr = new ArrayList<InterfaceClient>();
				arr.add(cl);
				listeFollower.put(login, arr);
				System.out.println("ajout");
			}
		else
			System.out.println("Cette personne n'existe pas");
		
		System.out.println(cl.getPersonne().getPseudo() + " veut suivre " + login);
	}
	
	/**
	 * Envoi d'un message aux followers de la personne p
	 * @param p
	 */
	public void sendToFollowers(String login, Twitt t){
		ArrayList<InterfaceClient> array = new ArrayList<InterfaceClient>();
		array = listeFollower.get(login);
		if(array != null)
			for (InterfaceClient personne : array) {
				send(personne, t);
			}
	}
	
	/**
	 * Envoi d'un tweet sur le client p
	 * Appel de la fonction afficherTweetRecu du client
	 * 
	 * @param p
	 * @param t
	 */
	public void send(InterfaceClient client, Twitt t){
		try {
			client.afficherTweetRecu(t);
			System.out.print(client.getPersonne().getPseudo());
		} catch (RemoteException e) {
			System.out.println("Impossible d'envoyer aux followers");
			e.printStackTrace();
		}
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
	 * Retourne l'interface de tweet
	 * @param login
	 * @param mdp
	 * @return
	 */
	public InterfacePrivee connexion(String login, String mdp) throws RemoteException, ConnexionException{
		for (Personne p : listePersonne) {
			if(p.connect(login, mdp)){
				p.connect();
				InterfacePrivee rmico = this;
				System.out.println("Connexion de " + login);
				return rmico;
			}
		}
		throw new ConnexionException();
	}
		
	/**
	 * Relayer un tweet permet de faire comme si c'etait lui qui avait envoyé le tweet
	 */
	public void relayerTweet(Twitt t, ClientTwitt p){
		//t.personne = p.getPersonne();
		listeTweet.add(t);
	}
	
	@Override
	public void follower(String login, InterfaceClient cl) throws RemoteException {
		addFollower(login, cl);
	}

	/**
	 * Supprimer un tweet de la liste
	 * @param t
	 */
	private void supTweet(Twitt t){
		listeTweet.remove(t);
	}
	
	private void storeTweet(){
		try {
			ObjectOutputStream os = new ObjectOutputStream(new FileOutputStream(fichierTweet));
			os.writeObject(listeTweet);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private void storePersonne(){
		try {
			ObjectOutputStream os = new ObjectOutputStream(new FileOutputStream(fichierPersonnes));
			os.writeObject(listePersonne);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private void loadTweet(){
		try {
			ObjectInputStream is = new ObjectInputStream(new FileInputStream(fichierTweet));
			
			listeTweet = (ArrayList<Twitt>) is.readObject();
		} catch (IOException e) {
			listeTweet = new ArrayList<Twitt>();
		} catch (ClassNotFoundException e) {
			listeTweet = new ArrayList<Twitt>();
		}

		System.out.println("Fichier tweet chargé : " + listeTweet.size() + " lignes");
	}
	
	
	private void loadPersonne(){
		try {
			ObjectInputStream is = new ObjectInputStream(new FileInputStream(fichierPersonnes));
			listePersonne = (ArrayList<Personne>) is.readObject();
		} catch (IOException e) {
			listePersonne = new ArrayList<Personne>();
		} catch (ClassNotFoundException e) {
			listePersonne = new ArrayList<Personne>();
		}
		
		System.out.println("Fichier personne chargé : " + listePersonne.size() + " lignes");
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
		for (Twitt t : listeTweet) {
			System.out.println(t);
		}
		System.out.println("\n");
	}
	


	/**
	 * Inscription de la personne a la base de donnée des personnes
	 */
	@Override
	public void inscription(Personne p) throws RemoteException {
		//if(!alreadyLogin(p.getPseudo()))
			listePersonne.add(p);
			System.out.println("Inscription d'un client : " + p.getPrenonNom());
	}
	
	/**
	 * Savoir si un personne du même login est present dans la base
	 * @param login
	 * @return
	 */
	public boolean alreadyLogin(String login){
		for (Personne p : listePersonne) {
			if(p.getPseudo().equals(login))
				return false;
		}
		
		return true;
	}
	
	public boolean exist(Personne pers){
		for (Personne p : listePersonne) {
			if(p.is_equals(pers)){
				return true;
			}
		}
		
		return false;
	}

	/**
	 * Retourner la personne ayant les identifiants login et mdp
	 * @param login
	 * @param mdp
	 * @return
	 * @throws RemoteException
	 */
	public Personne getPersonne(String login, String mdp) throws RemoteException{
		for (Personne p : listePersonne) {
			if(p.connect(login, mdp)){
				return p;
			}
		}
		
		return null;
	}

	@Override
	public void logOff(ClientTwitt p) throws RemoteException {
		p.getPersonne().disconect();
	}
	
	public static void main(String[] args) {
		InterfacePublic rm;
		try {
			Registry reg=LocateRegistry.createRegistry(PORT);
			
			 // Assign security manager
		    if (System.getSecurityManager() == null)
		    {
		        System.setSecurityManager   (new RMISecurityManager());
		    }
			
			RMISSLClientSocketFactory clientSocket = new RMISSLClientSocketFactory();
			RMISSLServerSocketFactory serveurSocket = new RMISSLServerSocketFactory();
			
			rm = new ServeurTwitt(clientSocket, serveurSocket);
			
			try {
				Naming.rebind("rmi://localhost:"+PORT+"/MonOD", rm);
				System.out.println("Serveur lancé sur le port " + PORT);
				
				System.out.println("Pour eteindre le serveur touche c: ");
				Scanner sc = new Scanner(System.in);
				sc.next();
				ServeurTwitt s = (ServeurTwitt)rm;
				s.close();
				System.out.println("Serveur éteint");
				
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

	@Override
	public void relayerTweet(Twitt t, InterfaceClient p) throws RemoteException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public ArrayList<Twitt> getTweetTopic(String topic) {
		ArrayList<Twitt> retour = new ArrayList<Twitt>();
		for (Twitt t : listeTweet) {
			if(t.topic.equals(topic))
				retour.add(t);
		}
		
		return retour;
	}

	@Override
	public ArrayList<Twitt> getTweetUtilisateur(String utilisateur) {
		ArrayList<Twitt> retour = new ArrayList<Twitt>();
		for (Twitt t : listeTweet) {
			if(t.personne.getPseudo().equals(utilisateur))
				retour.add(t);
		}
		
		return retour;		
	}

	@Override
	public ArrayList<Personne> getFollowers(InterfaceClient ct) throws RemoteException {
		ArrayList<Personne> a = new ArrayList<Personne>();
		ArrayList<InterfaceClient> ins = listeFollower.get(ct.getPersonne().getPseudo());
		if(ins != null)
			for (InterfaceClient personne : ins) {
				a.add(personne.getPersonne());
			}
		System.out.println("taille" + a.size());
		return a;
	}

	@Override
	public ArrayList<String> getListTopics() throws RemoteException {
		ArrayList<String> listTopics =  new ArrayList<String>();
		Iterator i = listeTopic.iterator(); 
		while(i.hasNext()) 
		{
			listTopics.add((String) i.next());
		}
		return listTopics;
	}

	@Override
	public ArrayList<String> getListUtilisateurs() throws RemoteException {
		ArrayList<String> listUtilisateurs =  new ArrayList<String>();
		for(Personne p : listePersonne){
			listUtilisateurs.add(p.getPseudo());
		}
		return listUtilisateurs;
	}

	@Override
	public String getListTopicsString() throws RemoteException {
		String retour = "";
		for (String tweet : getListTopics()) {
			retour += "\n" + tweet;
		}
		return retour;
	}

}
