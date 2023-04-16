package main.java.vue;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import main.java.model.jeu.Joueur;
import main.java.model.jeu.partie.Partie;

/**
 * La fenêtre qui affiche les éléments du modèle sous forme de composants dans
 * une interface graphique.
 */
public class VueJeu extends JFrame {

	private Joueur joueur;
	private Partie partie;

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

		setVisible(true); // Rend la fenêtre visible
		setDefaultCloseOperation(EXIT_ON_CLOSE); // Quitte le programme quand on ferme la fenêtre
		setLocationRelativeTo(null); // Centre la fenêtre par rapport à l'écran
		setAlwaysOnTop(false); // Empêche la fenêtre de rester au-dessus

		try { // Utilise l'apparence par défaut des applications système
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException
				| UnsupportedLookAndFeelException e) {
		}

	}
}