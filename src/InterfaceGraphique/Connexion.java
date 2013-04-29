package InterfaceGraphique;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.RemoteException;

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
	private JButton connexion = new JButton("Connexion");
	private JButton inscription = new JButton("Inscription");
	private ActionListenerConnexion alc = new ActionListenerConnexion();
	
	public Connexion(ClientTwitt cl){
		super("Connexion");
		this.setSize(500, 500);
		getContentPane().setLayout(new BorderLayout());
		connexion.addActionListener(alc);
		inscription.addActionListener(alc);
		loginField.setPreferredSize(new Dimension(2, 2));
		JPanel jp = new JPanel();
		jp.setLayout(new GridLayout(3, 2));
		jp.add(login);
		jp.add(loginField);
		jp.add(mdp);
		jp.add(mdpField);
		jp.add(connexion);
		jp.add(inscription);
		this.add(jp);
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
					client.Connexion(loginField.getText(), mdpField.getText());
					try {
						new Compte(client);
					} catch (HeadlessException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (RemoteException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					dispose();
				} catch (ConnexionException e) {
					// TODO Auto-generated catch block
					System.out.println("Connexion impossible");
				}
			}
			
			/**
			 * Cliquer sur le bouton inscription
			 */
			if(inscription.equals(obj)){
				//Ouvrir une nouvelle fenetre
				new Inscription(client);
				dispose();
			}			
		}
	}
	
	
}
