package InterfaceGraphique;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.RemoteException;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import ServeurTwitt.Twitt;

import ClientTwitt.ClientTwitt;

public class Compte extends JFrame{

	private ClientTwitt client;
	
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
		
		this.setSize(700, 700);
		getContentPane().setLayout(null);
		ecrire.addActionListener(alc);
		topic.addActionListener(alc);
		utilisateur.addActionListener(alc);
		actu.addActionListener(alc);
		abonnement.addActionListener(alc);
		followers.addActionListener(alc);
		quitter.addActionListener(alc);
		ecrire.setBackground(Color.red);
		ecrire.setBounds(100, 100, 200, 50);
		actu.setBounds(400, 50, 200, 50);
		followers.setBounds(400, 150, 200, 50);
		abonnementField.setBounds(100, 250, 200, 50);
		abonnement.setBounds(400, 250, 200, 50);
		topicField.setBounds(100, 350, 200, 50);
		topic.setBounds(400, 350, 200, 50);
		utilisateurField.setBounds(100, 450, 200, 50);
		utilisateur.setBounds(400, 450, 200, 50);
		quitter.setBounds(250, 550, 200, 50);
		this.add(ecrire);
		this.add(actu);
		this.add(topic);
		this.add(topicField);
		this.add(utilisateur);
		this.add(utilisateurField);
		this.add(abonnement);
		this.add(abonnementField);
		this.add(followers);
		this.add(quitter);
		this.setLocation(100, 100);
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
					new AfficheListe(topicField.getText(), client.getTweetTopic(topicField.getText()), true);
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
					new AfficheListe("Tweets de " + utilisateurField.getText(), client.getTweetUtilisateur(utilisateurField.getText()), true);
				} catch (RemoteException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}	
			}
			if(actu.equals(obj)){
				//Méthode qui récupère une liste de tweet( = tweet des personnes que le ClientTweet follow)
				//new Actualite(client);
				new AfficheListe("Fil d'actualit� ", client.getListReception(), true);
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
					new AfficheListe("Followers de " + client.getPersonne().getPseudo(), client.getFollowers(client), false);
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
