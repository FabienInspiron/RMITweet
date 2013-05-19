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
import javax.swing.JTextField;

import ClientTwitt.ClientTwitt;

public class Visite extends JFrame{

	private ClientTwitt client;
	private JButton topic = new JButton("OK");
	private JButton utilisateur = new JButton("OK");
	private JButton listetopic = new JButton("Voir tous les topics");
	private JButton listeutilisateur = new JButton("Voir tous les utilisateurs");
	private JTextField topicField = new JTextField();
	private JTextField utilisateurField = new JTextField();
	private ActionListenerVisite alc = new ActionListenerVisite();
	private JLabel labelUtilisateur = new JLabel("Chercher un utilisateur");
	private JLabel labelTopic = new JLabel("Chercher un topic");
	
	public Visite(ClientTwitt cl){

		super("Visite sur Twitter");
		
		this.client = cl;
		
		this.setSize(500, 555);
		getContentPane().setLayout(null);

		topic.addActionListener(alc);
		utilisateur.addActionListener(alc);
		listetopic.addActionListener(alc);
		listeutilisateur.addActionListener(alc);

		listeutilisateur .setBounds(100, 50, 300, 50);
		labelUtilisateur.setBounds(100, 150, 200, 30);
		utilisateurField.setBounds(100, 185, 200, 50);
		utilisateur.setBounds(320, 185, 80, 50);
		
		listetopic.setBounds(100, 285, 300, 50);
		labelTopic.setBounds(100, 385, 200, 30);
		topicField.setBounds(100, 415, 200, 50);
		topic.setBounds(320, 415, 80, 50);
		
		this.add(topic);
		this.add(topicField);
		this.add(utilisateur);
		this.add(utilisateurField);
		this.add(listetopic);
		this.add(listeutilisateur);
		this.add(labelTopic);
		this.add(labelUtilisateur);
		this.setLocationRelativeTo(null);
		this.setVisible(true);
	}
	
	private class ActionListenerVisite implements ActionListener {
		public void actionPerformed(ActionEvent event) {
			Object obj = event.getSource();
	
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
			if(listeutilisateur.equals(obj)){
				try {
					new AfficheListe("Liste des utilisateurs", client.getListUtilisateurs(), false);
				} catch (RemoteException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if(listetopic.equals(obj)){
				try {
					new AfficheListe("Liste des topics", client.getListTopics(), false);
				} catch (RemoteException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}
}
