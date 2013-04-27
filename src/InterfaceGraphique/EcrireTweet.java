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

import ServeurTweet.Tweet;

import ClientTweet.ClientTweet;

public class EcrireTweet extends JFrame{

	private ClientTweet client;
	
	private ActionListenerTweet alc = new ActionListenerTweet();
	private JTextArea textArea = new JTextArea("Tweet...", 10, 5);
	private JTextField topicField = new JTextField();
	private JButton twitter = new JButton("Twitter");
	
	public EcrireTweet(ClientTweet cl){
		super("Tweeter");
		this.setSize(500, 500);
		getContentPane().setLayout(new BorderLayout());
		twitter.addActionListener(alc);
		JPanel jp = new JPanel();
		jp.setLayout(new GridLayout(3, 1));
		jp.add(topicField);
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
				
				Tweet t = new Tweet(topicField.getText(), textArea.getText());
				try {
					client.twitter(t);
				} catch (RemoteException e) {
					System.out.println("Impossible d'envoyer le tweet");
				}
			}		
		}
	}
	
}
