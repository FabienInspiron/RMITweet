package InterfaceGraphique;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.rmi.RemoteException;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;

import ServeurTwitt.Twitt;


public class Image extends JFrame{
	JFileChooser fc;
	JButton openButton;
	JPanel jp;
	private ActionListenerChoose alc = new ActionListenerChoose();
	public Image(){
		super();
		
		fc = new JFileChooser();
		openButton = new JButton("Open a File...");
		this.setSize(500, 500);
		getContentPane().setLayout(new BorderLayout());
		openButton.addActionListener(alc);
		jp = new JPanel();
		jp.setLayout(new GridLayout(3, 1));
		jp.add(openButton);
		this.add(jp);
		this.setLocationRelativeTo(null);
		this.setVisible(true);
	}
	
	private class ActionListenerChoose implements ActionListener {
		public void actionPerformed(ActionEvent event) {
			Object obj = event.getSource();

			if(openButton.equals(obj)){
				int returnVal = fc.showOpenDialog(jp);
				if (returnVal == JFileChooser.APPROVE_OPTION) {
	                File file = fc.getSelectedFile();
	                System.out.println(file.getName());
	                System.out.println("Opening: " + file.getName() + ".\n");
	            } else {
	            	System.out.println("Open command cancelled by user.\n");
	            }
			}

		}
	}
	

}
