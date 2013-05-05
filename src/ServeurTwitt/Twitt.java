package ServeurTwitt;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.ImageIcon;

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
	Date date;
	
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
		date = new Date();
	}
	
	public Twitt(String topic, String message) {
		this.topic = topic;
		this.message = message;
		this.personne = null;
	}
	
	public String getTopic() {
		return topic;
	}

	@Override
	public String toString() {
		String retour = "Tweet : \t#" + topic + "\tMessage : ";
		retour += message + "\tPersonne : " + personne.getPseudo();
		
		DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		String dateString = dateFormat.format(date);
		retour += "\n Date : " + dateString;
		return retour;
	}
	
	public void setPersonne(Personne p){
		this.personne = p;
	}	
}
