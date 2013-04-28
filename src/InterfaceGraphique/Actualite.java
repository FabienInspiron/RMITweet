package InterfaceGraphique;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.RemoteException;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import ServeurTwitt.Twitt;

import ClientTwitt.ClientTwitt;

public class Actualite extends JFrame{

	private ClientTwitt client;
	
	private ActionListenerTweet alc = new ActionListenerTweet();
	private JTextArea textArea = new JTextArea("Vos tweets", 10, 5);
	private JButton twitter = new JButton("Actualiser ()");
	
	public Actualite(ClientTwitt cl){
		super("Vos actulités");
		this.setSize(500, 500);
		getContentPane().setLayout(new BorderLayout());
		twitter.addActionListener(alc);
		JPanel jp = new JPanel();
		jp.setLayout(new GridLayout(2, 1));
		jp.add(textArea);
		jp.add(twitter);
		this.add(jp);
		this.setLocationRelativeTo(null);
		this.setVisible(true);
		client = cl;
	}
	
	private class ActionListenerTweet implements ActionListener {
		public void actionPerformed(ActionEvent event) {
			Object obj = event.getSource();
	
			//Si on a cliqué sur le bouton twitter
			if(twitter.equals(obj)){
				//Sauvegarder le tweet et l'envoyer aux followers (+ topic ??)
				ArrayList<Twitt> l = client.getListReception();
				String contenu = "";
				
				for (Twitt tweet : l) {
					contenu += tweet.toString() + "\n";
				}
				
				textArea.setText(contenu);
			}		
		}
	}
	
}
