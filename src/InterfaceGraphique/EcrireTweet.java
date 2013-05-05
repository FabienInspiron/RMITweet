package InterfaceGraphique;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.rmi.RemoteException;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import ServeurTwitt.Twitt;
import ServeurTwitt.TwittImage;

import ClientTwitt.ClientTwitt;

public class EcrireTweet extends JFrame{

	private ClientTwitt client;
	private Twitt t;
	private ImageIcon ic; 
	private ActionListenerTweet alc = new ActionListenerTweet();
	private JTextArea textArea = new JTextArea("Tweet...", 10, 5);
	private JTextField topicField = new JTextField("Topic");
	private JButton twitter = new JButton("Twitter");
	private JFileChooser fc;
	private JButton openButton = new JButton("Ajouter une image");
	private JPanel jp;
	private boolean asImage;
	
	public EcrireTweet(ClientTwitt cl){
		super("Tweeter");
		this.setSize(500, 500);
		fc = new JFileChooser();
		getContentPane().setLayout(new BorderLayout());
		
		twitter.addActionListener(alc);
		openButton.addActionListener(alc);
		
		jp = new JPanel();
		jp.setLayout(new GridLayout(4, 1));
		jp.add(topicField);
		jp.add(textArea);
		jp.add(twitter);
		jp.add(openButton);
		this.add(jp);
		this.setLocationRelativeTo(null);
		this.setVisible(true);
		client = cl;
		asImage = false;
	}
	
	private class ActionListenerTweet implements ActionListener {
		public void actionPerformed(ActionEvent event) {
			Object obj = event.getSource();
	
			//Si on a cliqu� sur le bouton twitter
			if(twitter.equals(obj)){
				//Sauvegarder le tweet et l'envoyer aux followers (+ topic ??)
				
				try {
					t = new Twitt(topicField.getText(), textArea.getText(), client.getPersonne());
					
					
					if(asImage){
						t = new TwittImage(topicField.getText(), textArea.getText(), client.getPersonne(), ic);
					}
					
					client.twitter(t);
					dispose();
				} catch (RemoteException e) {
					System.out.println("Impossible d'envoyer le tweet");
				}
			}
			if(openButton.equals(obj)){
				asImage = true;
				
				//Ouvrir une fenetre qui permet d'aller choisir un fichier
				int returnVal = fc.showOpenDialog(jp);
				if (returnVal == JFileChooser.APPROVE_OPTION) {
	                File file = fc.getSelectedFile();
	                
	                ic = new ImageIcon(file.getAbsolutePath());
	                //t.setIc(ic);
	                
	                /*JLabel label = new JLabel(ic);
	                jp.add(label);
	                jp.repaint();
	                setVisible(true);
	                System.out.println(file.getAbsolutePath());*/
	                
	                System.out.println("Opening: " + file.getName() + ".\n");
	            } else {
	            	System.out.println("Open command cancelled by user.\n");
	            }
			}
		}
	}
	
}
