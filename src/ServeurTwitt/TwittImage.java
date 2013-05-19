package ServeurTwitt;

import java.awt.FlowLayout;
import java.io.Serializable;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class TwittImage extends Twitt implements Serializable{
	
	/**
	 * Permet de stocker l'image associée à un tweet
	 */
	private ImageIcon ic;
	
	/**
	 * Constructeur normal d'un twitt
	 * @param topic
	 * @param message
	 * @param personne
	 */
	public TwittImage(String topic, String message, Personne personne) {
		super(topic, message, personne);
	}
	
	/**
	 * Constructeur normal d'un twitt avec une image
	 * @param topic
	 * @param message
	 * @param personne
	 * @param img
	 */
	public TwittImage(String topic, String message, Personne personne, ImageIcon img) {
		super(topic, message, personne);
		ic = img;
	}
	
	/**
	 * Accesseur en lecture de l'ImageIcon
	 */
	public ImageIcon getIc() {
		return ic;
	}

	/**
	 * Accesseur en écriture de l'ImageIcon
	 * @param ic
	 */
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
		
		// FlowLayout => les components sont positionnés les uns à la suite des autres de gauche à droite
		jp2.setLayout(new FlowLayout());
		
		// Redimensionner la taille de l'image
		JLabel label = new JLabel(new ImageIcon(((this.getIc())).getImage().getScaledInstance(110, 110, java.awt.Image.SCALE_SMOOTH)));
		
		jp2.add(label);
		jp2.add(new JLabel(this.toStringHTML()));
		return jp2;
	}
}
