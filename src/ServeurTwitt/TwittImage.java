package ServeurTwitt;

import java.awt.GridLayout;
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
		jp2.setLayout(new GridLayout(1, 2));
		JLabel label = new JLabel(this.getIc());
		
		jp2.add(label);
		jp2.add(new JLabel(this.toString()));
		return jp2;
	}
}
