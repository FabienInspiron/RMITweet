package InterfaceGraphique;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

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
				TwittImage t = (TwittImage) li.get(i);
				
				if(t.getIc() != null){
					JPanel jp2 = new JPanel();
					jp2.setLayout(new GridLayout(1, 2));
					JLabel label = new JLabel(t.getIc());
					jp2.add(label);
					jp2.add(new JLabel(t.toString()));
					jp.add(jp2);
				}
				else jp.add(new JLabel(t.toString()));
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
