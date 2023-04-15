package main.java.vue;

import javax.swing.JFrame;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

/**
 * La fenêtre qui affiche les éléments du modèle sous forme de composants dans
 * une interface graphique.
 */
public class VueJeu extends JFrame {

	/**
	 * Construit un objet <code>Fenetre</code> avec le titre spécifié, qui
	 * correspond à l'interface graphique affichant le jeu.
	 * 
	 * @param titre Le titre de la fenêtre
	 */
	public VueJeu(String titre) {

		super(titre);

		setVisible(true); // Rend la fenêtre visible
		setDefaultCloseOperation(EXIT_ON_CLOSE); // Quitte le programme quand on ferme la fenêtre
		setLocationRelativeTo(null); // Centre la fenêtre par rapport à l'écran
		setAlwaysOnTop(false); // Empêche la fenêtre de rester au-dessus
		setExtendedState(MAXIMIZED_BOTH); // Met la fenêtre en plein écran

		try { // Utilise l'apparence par défaut des applications système
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException
				| UnsupportedLookAndFeelException e) {
		}

	}

}