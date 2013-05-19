package InterfaceGraphique;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import ClientTwitt.ClientTwitt;
import ServeurTwitt.Personne;
import ServeurTwitt.Twitt;

public class Twitter extends JFrame{
	
	private ClientTwitt client;
	private JButton connexion = new JButton("Connexion");
	private JButton inscription = new JButton("Inscription");
	private JButton visite = new JButton("Visiter (Mode non authentifié)");
	private ActionListenerClient alc = new ActionListenerClient();
	public Twitter(ClientTwitt cl){
		super("Bienvenue sur Twitter");
		this.setSize(400, 400);
		getContentPane().setLayout(null);
		connexion.addActionListener(alc);
		inscription.addActionListener(alc);
		visite.addActionListener(alc);
		connexion.setBounds(100, 50, 200, 50);
		inscription.setBounds(100, 150, 200, 50);
		visite.setBounds(100, 250, 200, 50);
		this.add(connexion);
		this.add(visite);
		this.add(inscription);
		this.setLocationRelativeTo(null);
		this.setVisible(true);
		client = cl;
	}
	
	private class ActionListenerClient implements ActionListener {
		public void actionPerformed(ActionEvent event) {
			Object obj = event.getSource();
	
			if(connexion.equals(obj)){
				//Mode authentifié
				new Connexion(client);
			}	
			if(visite.equals(obj)){
				//Mode non authentifié
				new Visite(client);
			}
			if(inscription.equals(obj)){
				//Ouvrir une nouvelle fenetre
				new Inscription(client);
				dispose();
			}	
		}
	}
}
