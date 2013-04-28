package ServeurTwitt;

import java.io.Serializable;

/**
 * Definition de la classe tweet
 * qui doit pourvoir sauvergader les tweet dans un fichier
 * @author belli
 *
 */
public class Twitt implements Serializable{
	String topic;
	String message;
	Personne personne;
	
	/**
	 * Constructeur normal pour un tweet
	 * @param topic
	 * @param message
	 * @param personne
	 */
	public Twitt(String topic, String message, Personne personne) {
		this.topic = topic;
		this.message = message;
		this.personne = personne;
	}
	
	public Twitt(String topic, String message) {
		this.topic = topic;
		this.message = message;
		this.personne = null;
	}
	
	@Override
	public String toString() {
		return "Tweet : \n#" + topic + "\nMessage : " + message + "\nPersonne : " + personne.getPseudo();
	}
	
	public void setPersonne(Personne p){
		this.personne = p;
	}
}
