package main.java.vue.jeu;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Toolkit;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import main.java.model.jeu.Joueur;
import main.java.model.jeu.Pont;
import main.java.model.jeu.partie.Partie;
import javax.swing.SwingConstants;

/**
 * La fenêtre qui affiche les éléments du modèle sous forme de composants dans
 * une interface graphique.
 */
public class VueJeu extends JFrame {

	private Joueur joueur;
	private Partie partie;
	private JPanel panelPont;

	/**
	 * Construit un objet <code>Fenetre</code> avec le titre spécifié, qui
	 * correspond à l'interface graphique affichant le jeu.
	 */
	public VueJeu(Joueur joueur, Partie partie) {
		getContentPane().setBackground(Color.BLACK);
		getContentPane().setLayout(null);

		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		setSize(screenSize.width / 2, (screenSize.height * 9) / 10);
		setResizable(false);

		JLabel label = new JLabel();
		label.setBounds(this.getWidth() / 2 - 201, 0, 402, 100);
		label.setIcon(new ImageIcon("src/main/resources/logo_shazamm.gif"));
		getContentPane().add(label);
		
		panelPont = new JPanel();
		panelPont.setBounds(0,this.getHeight()*2/10,this.getWidth(),this.getHeight()*2/10);
		panelPont.setLayout(new GridLayout(1,Pont.TAILLE_PONT));
		panelPont.setBackground(Color.WHITE);
		paintPont();
		
		getContentPane().add(panelPont);
		
		setVisible(true); // Rend la fenêtre visible
		setDefaultCloseOperation(EXIT_ON_CLOSE); // Quitte le programme quand on ferme la fenêtre
		setLocationRelativeTo(null); // Centre la fenêtre par rapport à l'écran
	}
	
	public void paintPont() {
		for(int i=0;i<Pont.TAILLE_PONT;i++) {
			JLabel tmp = new JLabel();
			String source = "src/main/resources/pont/pont_";
			if(i<9) source += "0";
			source += (i+1) + ".gif";
			tmp.setIcon(new ImageIcon(source));
			panelPont.add(tmp);
		}
	}
}