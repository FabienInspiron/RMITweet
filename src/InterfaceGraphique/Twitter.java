package InterfaceGraphique;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import ClientTwitt.ClientTwitt;
import ServeurTwitt.Personne;
import ServeurTwitt.Twitt;

public class Twitter extends JFrame{
	
	private ClientTwitt client;
	private JButton connexion = new JButton("Connexion/Inscription");
	private JButton visite = new JButton("Visiter\nMode non authentifié");
	private ActionListenerClient alc = new ActionListenerClient();
	
	public Twitter(ClientTwitt cl){
		super("Bienvenue sur Twitter");
		this.setSize(500, 500);
		getContentPane().setLayout(new BorderLayout());
		connexion.addActionListener(alc);
		visite.addActionListener(alc);
		JPanel jp = new JPanel();
		jp.setLayout(new GridLayout(2, 1));
		jp.add(connexion);
		jp.add(visite);
		this.add(jp);
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
		}
	}
	public static void main(String[] args) {
		//Connexion c = new Connexion();
		//Inscription i = new Inscription();
		//Compte c = new Compte();
		//EcrireTweet ec = new EcrireTweet();
		/*Personne p = new Personne("pseudo", "nom", "prenom", "mdp");
		Tweet t = new Tweet("top", "mess", p);
		Tweet t1 = new Tweet("top", "mess2", p);
		Tweet t2 = new Tweet("top", "mess3", p);
		ArrayList<Tweet> li =  new ArrayList<Tweet>();
		li.add(t);
		li.add(t1);
		li.add(t2);
		new ListeTweets("liste tweet", li);*/
		//Image i = new Image();
		
		//new Twitter();
	}
}
