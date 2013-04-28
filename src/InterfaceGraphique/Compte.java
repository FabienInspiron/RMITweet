package InterfaceGraphique;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;

import ClientTwitt.ClientTwitt;

public class Compte extends JFrame{

	ClientTwitt client;
	
	private JButton ecrire = new JButton("Ecrire un tweet");
	private JButton topic = new JButton("Voir un topic");
	private JButton utilisateur = new JButton("Voir un utilisateur");
	private JButton actu = new JButton("Voir mon fil d'actualité");
	private JButton abonnement = new JButton("M'abonner");
	private JButton followers = new JButton("Afficher mes followers");
	
	private JTextField topicField = new JTextField();
	private JTextField utilisateurField = new JTextField();
	private JTextField abonnementField = new JTextField();
	private ActionListenerCompte alc = new ActionListenerCompte();
	private ClientTwitt ct;
	
	public Compte(ClientTwitt cl){
		
		/*super(ct.getPersonne().getPseudo());*/
		
		this.client = cl;
		
		this.setSize(500, 500);
		getContentPane().setLayout(new BorderLayout());
		ecrire.addActionListener(alc);
		topic.addActionListener(alc);
		utilisateur.addActionListener(alc);
		actu.addActionListener(alc);
		abonnement.addActionListener(alc);
		followers.addActionListener(alc);
		JPanel jp = new JPanel();
		jp.setLayout(new GridLayout(5, 2));
		jp.add(ecrire);
		jp.add(actu);
		jp.add(topic);
		jp.add(topicField);
		jp.add(utilisateur);
		jp.add(utilisateurField);
		jp.add(abonnement);
		jp.add(abonnementField);
		jp.add(followers);
		this.add(jp);
		this.setLocationRelativeTo(null);
		this.setVisible(true);
		
	}
	
	private class ActionListenerCompte implements ActionListener {
		public void actionPerformed(ActionEvent event) {
			Object obj = event.getSource();
	
			
			if(ecrire.equals(obj)){
				new EcrireTweet(client);
			}
			if(topic.equals(obj)){
				//Méthode qui prend en param un topic et retourne une liste de tweet à afficher
				topicField.getText();
				//new ListeTweets(topicField.getText(), li);				
			}
			if(utilisateur.equals(obj)){
				//Méthode qui prend en param un login et retourne une liste de tweet à afficher
				utilisateurField.getText();
				//new ListeTweets(utilisateurField.getText(), li);	
			}
			if(actu.equals(obj)){
				//Méthode qui récupère une liste de tweet( = tweet des personnes que le ClientTweet follow)
				new Actualite(client);
			}	
			if(abonnement.equals(obj)){
				//S'abonner à un compte d'un ClientTweet 
				//Méthode qui prend en paramètre un pseudo 				
				new Follow(client);				
			}
			if(followers.equals(obj)){
				//Afficher tous les followers de ClientTweet 
				//Méthode qui récupère une liste de ClientTWeet et affiche leur pseudo
				//ct.getFollowers();
			}	
		}
	}
}
