package Interfaces;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;

import javax.security.auth.Subject;
import javax.security.auth.login.LoginException;

import ServeurTwitt.ConnexionException;
import ServeurTwitt.Personne;
import ServeurTwitt.Twitt;

public interface InterfacePublic extends Remote{
	/**
	 * Connexion d'un client
	 * @param login
	 * @param mdp
	 * @return
	 * @throws RemoteException
	 */
	public InterfacePrivee connexion(String login, String mdp) throws ConnexionException, RemoteException;
	
	/**
	 * Inscription d'un personne au service de tweet
	 * @param p
	 * @throws RemoteException
	 */
	public void inscription(Personne p) throws RemoteException;
	
	/**
	 * Retourner tous les tweets ayant le sujet topic
	 * @param topic
	 * @return
	 */
	public ArrayList<Twitt> getTweetTopic(String topic) throws RemoteException;
	
	/**
	 * 
	 * @param utilisateur
	 * @throws RemoteException
	 * @return tweets d'un utilisateur
	 */
	public ArrayList<Twitt> getTweetUtilisateur(String utilisateur) throws RemoteException;
	

	/**
	 * 
	 * @return la liste de tous les topics
	 * @throws RemoteException
	 */
	public ArrayList<String> getListTopics() throws RemoteException;
	
	/**
	 * 
	 * @return la liste de tous les utilisateurs
	 * @throws RemoteException
	 */
	public ArrayList<String> getListUtilisateurs() throws RemoteException;
	
	/**
	 * Métode d'authentification avec JAAS
	 * @param username
	 * @param passwd
	 * @return
	 * @throws RemoteException
	 * @throws LoginException
	 */
	Subject logon(String username, String passwd)
			throws RemoteException, LoginException;
	
	/**
	 * Retourn le subject du client
	 * @return
	 * @throws RemoteException
	 */
	public InterfacePrivee getService(Subject s) throws RemoteException;
}
