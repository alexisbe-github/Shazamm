package main.java.vue.jeu;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import main.java.model.jeu.Joueur;
import main.java.model.jeu.Pont;
import main.java.model.jeu.carte.Carte;
import main.java.model.jeu.partie.Partie;

/**
 * La fenêtre qui affiche les éléments du modèle sous forme de composants dans
 * une interface graphique.
 */
public class VueJeu extends JFrame {

	private Joueur joueur;
	private Partie partie;
	private JPanel panelPont, panelMain;
	private JLabel logo = new JLabel();
	private List<JLabel> imagesPont, imagesCartesJoueur;

	/**
	 * Construit un objet <code>Fenetre</code> avec le titre spécifié, qui
	 * correspond à l'interface graphique affichant le jeu.
	 */
	public VueJeu(Joueur joueur, Partie partie) {
		this.joueur = joueur;
		this.partie = partie;

		setVisible(true); // Rend la fenêtre visible
		setDefaultCloseOperation(EXIT_ON_CLOSE); // Quitte le programme quand on ferme la fenêtre

		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		setSize(screenSize.width / 2, (screenSize.height * 9) / 10);
		setResizable(true);
		setLocationRelativeTo(null); // Centre la fenêtre par rapport à l'écran

		getContentPane().setBackground(Color.BLACK);
		getContentPane().setLayout(new GridBagLayout());

		GridBagConstraints c = new GridBagConstraints(); // Les contraintes de positionnement des composants
		c.insets = new Insets(5, 10, 5, 10); // Marge autour des éléments en pixels
		c.fill = GridBagConstraints.HORIZONTAL;

		c.weightx = 1;
		c.weighty = 0;
		c.gridx = 0;
		c.gridy = 0;
		getContentPane().add(new JLabel(), c); // Contraint le logo à se déplacer à droite

		c.weightx = 0;
		c.weighty = 0;
		c.gridx = 1;
		c.gridy = 0;
		getContentPane().add(logo, c); // Logo centré

		c.weightx = 1;
		c.weighty = 0;
		c.gridx = 2;
		c.gridy = 0;
		getContentPane().add(new JLabel(), c); // Contraint le logo à se déplacer à gauche

		logo.setIcon(new ImageIcon("src/main/resources/logo_shazamm.gif"));
		logo.setBounds(this.getWidth() / 2 - 201, 0, 402, 100);

		//Affichage du pont
		panelPont = new JPanel();
		panelPont.setBounds(0, this.getHeight() * 2 / 10, this.getWidth(), this.getHeight() * 2 / 10);
		panelPont.setLayout(new GridLayout(1, Pont.TAILLE_PONT, 0, 0));
		panelPont.setBackground(Color.BLACK);
		imagesPont = new ArrayList<>();
		initPont();
		updatePont();
		c.weightx = 1;
		c.weighty = 1/3;
		c.gridx = 0;
		c.gridy = 1;
		c.gridwidth = 3;
		getContentPane().add(panelPont, c);

		//Affichage des cartes de la main du joueur
		panelMain = new JPanel();
		panelMain.setBounds(0, this.getHeight() * 2 / 10, this.getWidth(), this.getHeight() * 2 / 10);
		imagesCartesJoueur = new ArrayList<>();
		this.paintMain();
		c.weightx = 1;
		c.weighty = 0.33;
		c.gridx = 0;
		c.gridy = 2;
		c.gridwidth = 3;
		getContentPane().add(panelMain, c);
	}

	/**
	 * Pose les images du pont sur l'interface graphique
	 */
	private void initPont() {
		for (int i = 0; i < Pont.TAILLE_PONT; i++) {
			JLabel tmp = new JLabel();
			String source = this.getImageCasePont(i);
			tmp.setIcon(new ImageIcon(source));
			imagesPont.add(tmp);
			panelPont.add(tmp);

			panelPont.addComponentListener(new ComponentAdapter() {
				@Override
				public void componentResized(ComponentEvent e) {
					int panelWidth = panelPont.getWidth();
					int panelHeight = panelPont.getHeight();
					int imageWidth = tmp.getIcon().getIconWidth();
					int imageHeight = tmp.getIcon().getIconHeight();
					int imageCount = panelPont.getComponentCount();

					int marginWidth = (panelWidth - imageCount * imageWidth) / 2;
					int marginHeight = (panelHeight - imageHeight) / 2;

					for (int i = 0; i < imageCount; i++) {
						JLabel imageLabel = (JLabel) panelPont.getComponent(i);
						imageLabel.setBounds(marginWidth + i * imageWidth, marginHeight, imageWidth, imageHeight);
					}
				}
			});
		}
	}

	/**
	 * Met à jour les images du pont
	 */
	private void updatePont() {
		for (int i = 0; i < this.imagesPont.size(); i++) {
			JLabel tmp = this.imagesPont.get(i);
			tmp.setIcon(new ImageIcon(this.getImageCasePont(i)));
		}
	}

	/**
	 * Renvoie le chemin vers l'image du pont de la case courante (pont ou lave)
	 * 
	 * @param indexCase
	 * @return String le chemin
	 */
	private String getImageCasePont(int indexCase) {
		String source = "src/main/resources/";
		if (partie.getPont().estUneCaseLave(indexCase)) {
			source += "lave/lave_";
			if (indexCase < 9)
				source += "0";
			source += (indexCase + 1) + ".gif";
		} else {
			source += "pont/pont_";
			if (indexCase < 9)
				source += "0";
			source += (indexCase + 1) + ".png";
		}
		return source;
	}

	private void paintMain() {
		List<Carte> mainJoueur = this.joueur.getMainDuJoueur();
		for (JLabel label : this.imagesCartesJoueur) {
			panelMain.remove(label);
		}

		for (Carte c : mainJoueur) {
			JLabel tmp = new JLabel();
			tmp.setIcon(new ImageIcon(c.getPath()));
			panelMain.add(tmp);
		}
	}
}