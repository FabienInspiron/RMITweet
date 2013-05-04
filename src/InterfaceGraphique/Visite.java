package InterfaceGraphique;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.RemoteException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;

import ClientTwitt.ClientTwitt;

public class Visite extends JFrame{

	private ClientTwitt client;
	private JButton topic = new JButton("Voir un topic");
	private JButton utilisateur = new JButton("Voir un utilisateur");
	private JButton listetopic = new JButton("Voir tous les topics");
	private JButton listeutilisateur = new JButton("Voir tous les utilisateurs");
	private JTextField topicField = new JTextField();
	private JTextField utilisateurField = new JTextField();
	private ActionListenerVisite alc = new ActionListenerVisite();
	
	public Visite(ClientTwitt cl){
		super("Visite sur Twitter");
		
		this.client = cl;
		
		this.setSize(500, 500);
		getContentPane().setLayout(new BorderLayout());

		topic.addActionListener(alc);
		utilisateur.addActionListener(alc);
		listetopic.addActionListener(alc);
		listeutilisateur.addActionListener(alc);

		JPanel jp = new JPanel();
		jp.setLayout(new GridLayout(5, 2));

		jp.add(topic);
		jp.add(topicField);
		jp.add(utilisateur);
		jp.add(utilisateurField);
		jp.add(listetopic);
		jp.add(listeutilisateur);
		
		this.add(jp);
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
					new AfficheListe("Tweets de " + utilisateurField.getText(), client.getTweetUtilisateur(utilisateurField.getText()));
				} catch (RemoteException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}	
			}
			if(listeutilisateur.equals(obj)){
				try {
					new AfficheListe("Liste des utilisateurs", client.getListUtilisateurs());
				} catch (RemoteException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if(listetopic.equals(obj)){
				try {
					new AfficheListe("Liste des topics", client.getListTopics());
				} catch (RemoteException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}
}
