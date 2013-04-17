package ServeurTweet;

import java.io.Serializable;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * Definition de la classe personne
 * @author belli
 *
 */
public class Personne implements Serializable{
	private String pseudo;
	private String nom;
	private String prenom;
	private String mdp;
	private boolean IsConnect;
	private InetAddress Ip;

	/**
	 * Constructeur normal de personne
	 * @param nom
	 * @param prenom
	 * @param mdp
	 */
	public Personne(String pseudo, String nom, String prenom, String mdp) {
		this.pseudo = pseudo;
		this.nom = nom;
		this.prenom = prenom;
		this.mdp = mdp;
	}
	
	@Override
	public String toString() {
		return "Pseudo : " + pseudo + "\nNom : " + nom + "\nPrenom : " + prenom;
	}
	
	/**
	 * Demande de connexion pour une personne
	 * @param login
	 * @param mdp
	 * @return
	 */
	public boolean connect(String login, String mdp){
		if(this.pseudo.equals(login))
			if(this.mdp.equals(mdp)){
				IsConnect = true;
				return true;
			}
		
		return false;
		
	}
	
	/**
	 * Demande de deconnexion
	 */
	public void disconect(){
		IsConnect= false;
	}
	
	/**
	 * Modification de l'adresse IP du client
	 * @param adresseIP
	 */
	public void setAdresseIp(){
		try {
			Ip =InetAddress.getLocalHost();
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
