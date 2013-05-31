package InterfaceGraphique;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.RemoteException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import ClientTwitt.ClientTwitt;
import Interfaces.Util;
import ServeurTwitt.Personne;


public class Inscription extends JFrame{
	
	public ClientTwitt client;
	private JTextField loginField = new JTextField();
	private JPasswordField mdp1Field = new JPasswordField();
	private JPasswordField mdp2Field = new JPasswordField();
	private JTextField nomField = new JTextField();
	private JTextField prenomField = new JTextField();
	private JLabel login = new JLabel("Login");
	private JLabel nom = new JLabel("Nom");
	private JLabel prenom = new JLabel("Pr�nom");
	private JLabel mdp1 = new JLabel("Mot de passe");
	private JLabel mdp2 = new JLabel("Retapez-le");
	private JButton inscription = new JButton("Valider");
//	private JButton annuler = new JButton("Annuler");
	private ActionListenerInscription alc = new ActionListenerInscription();
	
	public Inscription(ClientTwitt cl){
		super("Inscription");
		this.setSize(500, 700);
		getContentPane().setLayout(null);
		inscription.addActionListener(alc);
//		annuler.addActionListener(alc);
		
		login.setBounds(100, 50, 80, 50);
		loginField.setBounds(250, 50, 150, 50);
		nom.setBounds(100, 150, 80, 50);
		nomField.setBounds(250, 150, 150, 50);
		prenom.setBounds(100, 250, 80, 50);
		prenomField.setBounds(250, 250, 150, 50);
		mdp1.setBounds(100, 350, 80, 50);
		mdp1Field.setBounds(250, 350, 150, 50);
		mdp2.setBounds(100, 450, 100, 50);
		mdp2Field.setBounds(250, 450, 150, 50);
		inscription.setBounds(170, 550, 160, 50);
		
		this.add(login);
		this.add(loginField);
		this.add(nom);
		this.add(nomField);
		this.add(prenom);
		this.add(prenomField);
		this.add(mdp1);
		this.add(mdp1Field);
		this.add(mdp2);
		this.add(mdp2Field);
		this.add(inscription);
//		jp.add(annuler);
		this.setLocationRelativeTo(null);
		this.setVisible(true);
		client = cl;
	}
	
	private class ActionListenerInscription implements ActionListener {
		public void actionPerformed(ActionEvent event) {
			Object obj = event.getSource();
	
			//Si on a cliqué sur le bouton annuler
//			if(annuler.equals(obj)){
//				//Fermer la fenetre			
//				System.exit(EXIT_ON_CLOSE);
//			}
			
			//Si on a cliqué sur le bouton inscription
			if(inscription.equals(obj)){
				//Créer le ClientTweet
				
				//Ajouter le client à la BD
				if(mdp1Field.getText() == mdp2Field.getText()){
					nomField.getText();
					prenomField.getText();
					loginField.getText();
				}

				//Ouvrir son compte
				Personne p = new Personne(loginField.getText(), nomField.getText(), prenomField.getText(), mdp1Field.getText());
				try {
					client.incription(p);
					new Connexion(client);
					dispose();
				} catch (RemoteException e) {
					Util.message("Inscription impossible");
				}
			}			
		}
	}
}
