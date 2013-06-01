package security.module;

import java.util.Random;

public class TwitterPrincipal implements java.security.Principal,
		java.io.Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	String name = null;
	
	/**
	 * Identifiant unique de la connexion
	 * Nombre aléatoire générer entre 0 et 100 000
	 */
	int id = 0;

	public TwitterPrincipal(String name) {
		this.name = name;
		Random r = new Random();
		id = r.nextInt(100000);

	}

	@Override
	public String getName() {
		return name;
	}
	
	public int getID(){
		return id;
	}

	public String toString() {
		return "ce principal a ce nom " + name;
	}

	public boolean equals(Object o) {
		TwitterPrincipal twit = (TwitterPrincipal)o;
		
		if (o == null)
			return false;

		/**
		 * Verifier le nombre aléatoire
		 */
		if(this.id != twit.getID())
			return false;
		
		if (this == o)
			return true;

		if (!(o instanceof TwitterPrincipal))
			return false;
		TwitterPrincipal that = (TwitterPrincipal) o;

		if (this.getName().equals(that.getName()))
			return true;
		return false;
	}

	public int hashCode() {
		return name.hashCode();
	}
}
