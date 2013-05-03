package InterfaceGraphique;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.RemoteException;

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
	private JButton quitter = new JButton("Quitter");
	
	private JTextField topicField = new JTextField();
	private JTextField utilisateurField = new JTextField();
	private JTextField abonnementField = new JTextField();
	private ActionListenerCompte alc = new ActionListenerCompte();

	
	public Compte(ClientTwitt ct) throws HeadlessException, RemoteException{
		
		super(ct.getPersonne().getPseudo());
		
		this.client = ct;
		
		this.setSize(500, 500);
		getContentPane().setLayout(new BorderLayout());
		ecrire.addActionListener(alc);
		topic.addActionListener(alc);
		utilisateur.addActionListener(alc);
		actu.addActionListener(alc);
		abonnement.addActionListener(alc);
		followers.addActionListener(alc);
		quitter.addActionListener(alc);
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
		jp.add(quitter);
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
				if(topicField.getText().equals(""))
					return;
				
				//Méthode qui prend en param un topic et retourne une liste de tweet à afficher
				try {
					new AfficheListe(topicField.getText(), client.getTweetTopic(topicField.getText()));
				} catch (RemoteException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}				
			}
			if(utilisateur.equals(obj)){
				//Méthode qui prend en param un login et retourne une liste de tweet à afficher
				if(utilisateurField.getText().equals(""))
					return;
				
				try {
					new AfficheListe("Tweets de a" + utilisateurField.getText(), client.getTweetUtilisateur(utilisateurField.getText()));
				} catch (RemoteException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}	
			}
			if(actu.equals(obj)){
				//Méthode qui récupère une liste de tweet( = tweet des personnes que le ClientTweet follow)
				new Actualite(client);
			}	
			if(abonnement.equals(obj)){
				if(abonnementField.getText().equals(""))
					return;
				
				//S'abonner à un compte d'un ClientTweet 
				//Méthode qui prend en paramètre un pseudo 				
				try {
					if(abonnementField.getText() != null)
						client.follower(abonnementField.getText());
				} catch (RemoteException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}			
			}
			if(followers.equals(obj)){
				//Afficher tous les followers de ClientTweet 	
			
				try {
					new AfficheListe("Followers de " + client.getPersonne().getPseudo(), client.getFollowers(client));
				} catch (RemoteException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}	
			if(quitter.equals(obj)){
				dispose();
			}
		}
	}
}
