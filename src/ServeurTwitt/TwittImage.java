package ServeurTwitt;

import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.io.Serializable;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class TwittImage extends Twitt implements Serializable{
	
	private ImageIcon ic;
	
	public TwittImage(String topic, String message, Personne personne) {
		super(topic, message, personne);
	}
	
	public TwittImage(String topic, String message, Personne personne, ImageIcon img) {
		super(topic, message, personne);
		ic = img;
	}
	
	public ImageIcon getIc() {
		return ic;
	}

	public void setIc(ImageIcon ic) {
		this.ic = ic;
	}
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		String s= super.toString();
		s += "\n Image : " + ic.toString();
		return s;
	}
	
	@Override
	public JPanel getImagePanel(){
		JPanel jp2 = new JPanel();
		jp2.setLayout(new FlowLayout());
		//changer la taille de l'image
		JLabel label = new JLabel(new ImageIcon(((this.getIc())).getImage().getScaledInstance(110, 110, java.awt.Image.SCALE_SMOOTH)));
		
		jp2.add(label);
		jp2.add(new JLabel(this.toStringHTML()));
		return jp2;
	}
}
