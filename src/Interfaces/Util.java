package Interfaces;

import javax.swing.JOptionPane;

public class Util {
	/***
	 * Afficher un message à l'utilisateur
	 * @param text
	 */
	public static void message(String text){
		JOptionPane.showMessageDialog(null, text);
		System.out.println(text);
	}
}
