package InterfaceGraphique;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.RemoteException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import ServeurTweet.Personne;

import ClientTweet.ClientTweet;


public class Inscription extends JFrame{
	
	public ClientTweet client;
	private JTextField loginField = new JTextField();
	private JPasswordField mdp1Field = new JPasswordField();
	private JPasswordField mdp2Field = new JPasswordField();
	private JTextField nomField = new JTextField();
	private JTextField prenomField = new JTextField();
	private JLabel login = new JLabel("Login");
	private JLabel nom = new JLabel("Nom");
	private JLabel prenom = new JLabel("Prénom");
	private JLabel mdp1 = new JLabel("Mot de passe");
	private JLabel mdp2 = new JLabel("Retapez le mot de passe");
	private JButton inscription = new JButton("Valider");
	private JButton annuler = new JButton("Annuler");
	private ActionListenerInscription alc = new ActionListenerInscription();
	
	public Inscription(ClientTweet cl){
		super("Inscription");
		this.setSize(500, 500);
		getContentPane().setLayout(new BorderLayout());
		inscription.addActionListener(alc);
		annuler.addActionListener(alc);
		JPanel jp = new JPanel();
		jp.setLayout(new GridLayout(6, 2));
		jp.add(login);
		jp.add(loginField);
		jp.add(nom);
		jp.add(nomField);
		jp.add(prenom);
		jp.add(prenomField);
		jp.add(mdp1);
		jp.add(mdp1Field);
		jp.add(mdp2);
		jp.add(mdp2Field);
		jp.add(inscription);
		jp.add(annuler);
		this.add(jp);
		this.setLocationRelativeTo(null);
		this.setVisible(true);
		client = cl;
	}
	
	private class ActionListenerInscription implements ActionListener {
		public void actionPerformed(ActionEvent event) {
			Object obj = event.getSource();
	
			//Si on a cliqué sur le bouton annuler
			if(annuler.equals(obj)){
				//Fermer la fenetre			
				System.exit(EXIT_ON_CLOSE);
			}
			
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
					System.out.println("Inscription impossible");
				}
			}			
		}
	}
}
