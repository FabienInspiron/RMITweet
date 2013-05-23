package InterfaceGraphique;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.RemoteException;

import javax.security.auth.login.LoginException;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import ServeurTwitt.ConnexionException;

import ClientTwitt.ClientTwitt;

public class Connexion extends JFrame {
	
	public ClientTwitt client;
	
	private JTextField loginField = new JTextField();
	private JPasswordField mdpField = new JPasswordField();
	private JLabel login = new JLabel("Login");
	private JLabel mdp = new JLabel("Mot de passe");
	private JButton connexion = new JButton("Connecter");
	private ActionListenerConnexion alc = new ActionListenerConnexion();
	
	public Connexion(ClientTwitt cl){
		super("Connexion");
		this.setSize(480, 400);
		getContentPane().setLayout(null);
		connexion.addActionListener(alc);
		
		login.setBounds(100, 50, 80, 50);
		loginField.setBounds(230, 50, 150, 50);
		mdp.setBounds(100, 150, 80, 50);
		mdpField.setBounds(230, 150, 150, 50);
		connexion.setBounds(160, 250, 160, 50);
		this.add(login);
		this.add(loginField);
		this.add(mdp);
		this.add(mdpField);
		this.add(connexion);
		this.setLocationRelativeTo(null);
		this.setVisible(true);
		client = cl;
	}
	
	private class ActionListenerConnexion implements ActionListener {
		public void actionPerformed(ActionEvent event) {
			Object obj = event.getSource();
	
			/**
			 * Cliquer sur le bouton connexion
			 */
			if(connexion.equals(obj)){
				//Récupérer un objet de type Client Tweet après vérif
				
				//Ouvrir son compte
				//new Compte(clientTweet);
				try {
					try {
						client.Connexion(loginField.getText(), mdpField.getText());
						new Compte(client);
						dispose();
					} catch (RemoteException e1) {
						System.out.println("Impossible de joindre le serveur");
					} catch (LoginException e1) {
						System.out.println("Login ou mot de passe incorrect");
					}
				} catch (ConnexionException e) {
					// TODO Auto-generated catch block
					System.out.println("Connexion impossible");
				}
			}
			
		
		}
	}
	
	
}
