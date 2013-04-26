package ServeurTweet;

public interface RMITweetInterfaceClient {
	
	/**
	 * Afficher les tweets pour les clients
	 * @param t
	 */
	public void afficherTweetRecu(Tweet t);
}
