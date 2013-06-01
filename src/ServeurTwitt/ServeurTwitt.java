package ServeurTwitt;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.RMISecurityManager;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.ExportException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;

import javax.security.auth.Subject;
import javax.security.auth.login.LoginContext;
import javax.security.auth.login.LoginException;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;

import ClientTwitt.ClientTwitt;
import Interfaces.InterfaceClient;
import Interfaces.InterfacePrivee;
import Interfaces.InterfacePublic;
import Interfaces.InterfaceWebService;
import Interfaces.Util;

public class ServeurTwitt extends UnicastRemoteObject implements InterfacePublic, InterfacePrivee, InterfaceWebService {
	
	private static final long serialVersionUID = 1L;
	
	public static final int PORT = 2001;
	
	private ArrayList<Twitt> listeTweet;
	private ArrayList<Personne> listePersonne;
	private HashSet<String> listeTopic; // le HashSet permet de ne pas stocker deux fois le m�me topic
	
	LoginContext lc;
	
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
	 * Liste des Subjects
	 */
	private ArrayList<Subject> listSubject;
	
	/**
	 * El�ments utilis�s pour l'interface graphique
	 */
	private ActionListenerServeur alc = new ActionListenerServeur();
	private JButton stop = new JButton("Stop");
	private JButton refresh = new JButton("Refresh");
	protected JFrame jf = new JFrame("Serveur Twitter");
	private JTextArea textArea = new JTextArea("Serveur lancé... \nEvenements : \n", 10, 5);
	
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
		listSubject = new ArrayList<Subject>();
		loadTweet();
		loadPersonne();
		graphicFrame();
	}
	
	/**
	 * Fen�tre graphique
	 */
	public void graphicFrame(){
		jf = new JFrame("Serveur Twitter");
		stop.addActionListener(alc);
		refresh.addActionListener(alc);
		jf.setSize(300, 150);
		JPanel jp = new JPanel();
		JPanel jp2 = new JPanel();
		jp.setLayout(new GridLayout(1, 2));
		textArea.setEditable(false);
		jp.add(textArea);
		jp2.setLayout(new GridLayout(2, 1));
		jp2.add(stop);
		jp2.add(refresh);
		jp.add(jp2);
		jf.add(jp);                          
		jf.setLocationRelativeTo(null);
		jf.setVisible(true);
	}
	
	/**
	 * ActionListener sur les boutons de l'interface graphique
	 * @author user
	 *
	 */
	private class ActionListenerServeur implements ActionListener {

		public void actionPerformed(ActionEvent event) {
			Object obj = event.getSource();
	
			if(stop.equals(obj)){
			}
			
			if(refresh.equals(obj)){
			}			
		}
	}

	
	/**
	 * Lancer le serveur
	 * @param args
	 */
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
			
			// Instanciantion du stub
			rm = new ServeurTwitt(clientSocket, serveurSocket);
			
			try {
				Naming.rebind("rmi://localhost:"+PORT+"/MonOD", rm);
				System.out.println("Serveur lancé sur le port " + PORT);
				
			} catch (MalformedURLException e) {
				e.printStackTrace();
				Util.message("Impossible de reconnaitre l'URL");
			} catch(ExportException e) {
				System.err.println("Port "+ PORT +" already in use");
			}
			
		} catch (RemoteException e) {
			e.printStackTrace();
		}finally{
			//rm.close();
		}
	}
	
	@Override
	public Subject logon(String username, String passwd) throws RemoteException, LoginException{
		// Verifier si l'utilisateur a bien donn� un login et passwd egaux � testUser et testPasswd
		// Si non, renvoyer une instance de LoginException 
		
		lc = new LoginContext("MonServeur", new security.module.TwitterCallbackHandler(username, passwd));
		
		try{
			lc.login();
		}
		catch (LoginException e){
			System.out.println("Identifiants incorrect");
			throw e;
		}
		
		/**
		 * Ajout du Subject dans la liste
		 */
		listSubject.add(lc.getSubject());
		
		displayMSG("Connexion de " + username);
		
		// Retourner l'interface privee
		return lc.getSubject();
	}	
	
	/**
	 * Ajouter un tweet a la liste en verifiant 
	 * que le client à la possibilité de le faire
	 * 
	 * @param t
	 */
	public void twitter(Twitt t, InterfaceClient c, Subject s){
		if(!subjectPresent(s))
			return;
		
		/**
		 * Verifire que le tweet à été envoyé par la bonne personne
		 */
		try {
			if(!exist(c.getPersonne())) {
				displayMSG("Impossible de twitter " + c.getPersonne());
				return;
			}
			
			displayMSG(c.getPersonne().getPseudo() + " a ajouté un nouveau tweet");
			
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
		else{
			System.out.println("Cette personne n'existe pas");
		}
		
		displayMSG(cl.getPersonne().getPseudo() + " veut suivre " + login);
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
	public InterfacePrivee connexion(String login, String mdp) throws ConnexionException, RemoteException{
		for (Personne p : listePersonne) {
			if(p.connect(login, mdp)){
				p.connect();
				InterfacePrivee rmico = this;
				displayMSG("Connexion de " + login);
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
	public void follower(String login, InterfaceClient cl, Subject s) throws RemoteException {
		if(subjectPresent(s))
			addFollower(login, cl);
	}

	/**
	 * Supprimer un tweet de la liste
	 * @param t
	 */
	private void supTweet(Twitt t){
		listeTweet.remove(t);
	}
	
	/**
	 * S�rialiser un tweet dans un fichier
	 */
	private void storeTweet(){
		try {
			ObjectOutputStream os = new ObjectOutputStream(new FileOutputStream(fichierTweet));
			os.writeObject(listeTweet);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * S�rialiser une personne dans un fichier
	 */
	private void storePersonne(){
		try {
			ObjectOutputStream os = new ObjectOutputStream(new FileOutputStream(fichierPersonnes));
			os.writeObject(listePersonne);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * D�serialiser les tweets
	 */
	private void loadTweet(){
		try {
			ObjectInputStream is = new ObjectInputStream(new FileInputStream(fichierTweet));
			
			listeTweet = (ArrayList<Twitt>) is.readObject();
		} catch (IOException e) {
			listeTweet = new ArrayList<Twitt>();
		} catch (ClassNotFoundException e) {
			listeTweet = new ArrayList<Twitt>();
		}

		System.out.println("Fichier tweet charg� : " + listeTweet.size() + " lignes");
	}
	
	
	/**
	 * D�serialiser les personnes
	 */
	private void loadPersonne(){
		try {
			ObjectInputStream is = new ObjectInputStream(new FileInputStream(fichierPersonnes));
			listePersonne = (ArrayList<Personne>) is.readObject();
		} catch (IOException e) {
			listePersonne = new ArrayList<Personne>();
		} catch (ClassNotFoundException e) {
			listePersonne = new ArrayList<Personne>();
		}
		
		System.out.println("Fichier personne charg� : " + listePersonne.size() + " lignes");
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
	 * Remet le fichier à jour
	 */
	@Override
	public void inscription(Personne p) throws RemoteException {
		//if(!alreadyLogin(p.getPseudo()))
			listePersonne.add(p);
			storePersonne();
			displayMSG("Inscription d'un client : " + p.getPrenonNom());
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
	
	
	/**
	 * Savoir si une personne existe
	 * @param pers
	 * @return
	 */
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
	public void logOff(ClientTwitt p, Subject s) throws RemoteException {
		if(!subjectPresent(s))
			return;
					
		p.getPersonne().disconect();
		try {
			lc.logout();
		} catch (LoginException e) {
			System.out.println("Impossible de se deconnecter");
			e.printStackTrace();
		}
	}

	@Override
	public void relayerTweet(Twitt t, InterfaceClient p, Subject s) throws RemoteException {
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
	public ArrayList<Personne> getFollowers(InterfaceClient ct, Subject s) throws RemoteException {
		if(!subjectPresent(s))
			return null;
		
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
	public InterfacePrivee getService(Subject s) throws RemoteException {
		if(listSubject.contains(s))
			return this;
		else 
			return null;
	}

	@Override
	public String getListTopicsString() throws RemoteException {
		String retour = "";
		for (String tweet : getListTopics()) {
			retour += "\n" + tweet;
		}
		return retour;
	}
	
	/**
	 * Savoir si un utilisateur est connecté
	 * @param s
	 * @return
	 */
	public boolean subjectPresent(Subject s){
		boolean result = listSubject.contains(s);
		
		if(result) return true;
		else {
			displayMSG("tantive de connexion d'un supbject inconnu");
			return false;
		}
		
	}
	
	public void displayMSG(String msg){
		textArea.setText(textArea.getText() + "\n - " +msg);
	}
}
