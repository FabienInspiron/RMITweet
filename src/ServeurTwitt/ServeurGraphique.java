package ServeurTwitt;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.RemoteException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class ServeurGraphique extends ServeurTwitt{
	
	protected ServeurGraphique(RMISSLClientSocketFactory cl,
			RMISSLServerSocketFactory sr) throws RemoteException {
		super(cl, sr);
		// TODO Auto-generated constructor stub
	}

	/**
	 * El�ments utilis�s pour l'interface graphique
	 */
	private ActionListenerServeur alc = new ActionListenerServeur();
	private JButton stop = new JButton("Stop");
	private JButton refresh = new JButton("Refresh");
	protected JFrame jf = new JFrame("Serveur Twitter");
	
	/**
	 * Fen�tre graphique
	 */
	public void graphicFrame(){
		jf = new JFrame("Serveur Twitter");
		stop.addActionListener(alc);
		refresh.addActionListener(alc);
		jf.setSize(300, 150);
		JPanel jp = new JPanel();
		JPanel jp2 = new JPanel();
		jp.setLayout(new GridLayout(1, 2));
		jp.add(new JLabel("Serveur lanc�"));
		jp2.setLayout(new GridLayout(2, 1));
		jp2.add(stop);
		jp2.add(refresh);
		jp.add(jp2);
		jf.add(jp);                          
		jf.setLocationRelativeTo(null);
		jf.setVisible(true);	
	}
	
	/**
	 * ActionListener sur les boutons de l'interface graphique
	 * @author user
	 *
	 */
	private class ActionListenerServeur implements ActionListener {
		public void actionPerformed(ActionEvent event) {
			Object obj = event.getSource();
	
			if(stop.equals(obj)){
				
			}
			
			if(refresh.equals(obj)){

			}			
		}
	}

}
