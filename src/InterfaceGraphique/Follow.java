package InterfaceGraphique;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.RemoteException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import ServeurTwitt.Twitt;

import ClientTwitt.ClientTwitt;

public class Follow extends JFrame{

	private ClientTwitt client;
	
	private ActionListenerTweet alc = new ActionListenerTweet();
	private JTextField topicField = new JTextField();
	private JButton twitter = new JButton("Follower !");
	
	public Follow(ClientTwitt cl){
		super("Tweeter");
		this.setSize(500, 500);
		getContentPane().setLayout(new BorderLayout());
		twitter.addActionListener(alc);
		JPanel jp = new JPanel();
		jp.setLayout(new GridLayout(2, 1));
		jp.add(topicField);
		jp.add(twitter);
		this.add(jp);
		this.setLocationRelativeTo(null);
		this.setVisible(true);
		client = cl;
	}
	
	private class ActionListenerTweet implements ActionListener {
		public void actionPerformed(ActionEvent event) {
			Object obj = event.getSource();
	
			//Si on a cliqué sur le bouton follower
			if(twitter.equals(obj)){
				//Sauvegarder le tweet et l'envoyer aux followers (+ topic ??)
				
				try {
					client.follower(topicField.getText());
					dispose();
				} catch (RemoteException e) {
					System.out.println("Impossible de s'abboner");
				}
			}		
		}
	}
	
}
