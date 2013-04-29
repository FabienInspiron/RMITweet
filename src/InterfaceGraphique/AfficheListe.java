package InterfaceGraphique;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import ServeurTwitt.Twitt;

public class AfficheListe extends JFrame{
	
	public AfficheListe(String titreFenetre, ArrayList li){
		super(titreFenetre);
		
		this.setSize(500, 500);
		getContentPane().setLayout(new BorderLayout());
		JPanel jp = new JPanel();
		jp.setLayout(new GridLayout(li.size(), 1));
		for(int i = 0; i < li.size(); i++)
			jp.add(new JLabel(li.get(i).toString()));

		this.add(jp);
		this.setLocationRelativeTo(null);
		this.setVisible(true);
	}

}
