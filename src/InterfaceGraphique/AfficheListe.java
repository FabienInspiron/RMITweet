package InterfaceGraphique;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import ServeurTwitt.Twitt;
import ServeurTwitt.TwittImage;

public class AfficheListe extends JFrame{

	public AfficheListe(String titreFenetre, ArrayList li, boolean tweet){
		super(titreFenetre);

		this.setSize(500, 500);
		getContentPane().setLayout(new BorderLayout());
		JPanel jp = new JPanel();
		jp.setLayout(new GridLayout(li.size(), 1));


		if(tweet){
			for(int i = 0; i < li.size(); i++){
				Twitt t = (Twitt) li.get(i);
				jp.add(t.getImagePanel());
			}
		}
		else {
			for(int i = 0; i < li.size(); i++){
				jp.add(new JLabel(li.get(i).toString()));
			}
		}
	this.add(jp);
	this.setLocationRelativeTo(null);
	this.setVisible(true);
}


}
