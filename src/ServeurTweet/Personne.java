package ServeurTweet;

import java.io.Serializable;

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
}
