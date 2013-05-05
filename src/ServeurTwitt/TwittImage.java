package ServeurTwitt;

import javax.swing.ImageIcon;

public class TwittImage extends Twitt{
	
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
}
