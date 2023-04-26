package main.java.vue.jeu;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;

import main.java.exceptions.PositionInaccessibleException;
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
	private JPanel panelLogo, panelPont, panelSorciers, panelJeu, panelMain, panelAction;
	private JProgressBar barreMana;
	private JLabel logo = new JLabel();
	private List<JLabel> imagesPont, imagesCartesJoueur;
	private List<Integer> cartesJouees;
	private int positionSorcierVert, positionSorcierRouge, positionMurFeu;
	private final String CHEMIN_SORCIER_ROUGE = "src/main/resources/perso/rouge.gif",
			CHEMIN_SORCIER_VERT = "src/main/resources/perso/vert.gif",
			CHEMIN_MUR_FEU = "src/main/resources/perso/mur.gif";

	/**
	 * Construit un objet <code>Fenetre</code> avec le titre spécifié, qui
	 * correspond à l'interface graphique affichant le jeu.
	 */
	public VueJeu(Joueur joueur, Partie partie) {
		this.joueur = joueur;
		this.partie = partie;

		cartesJouees = new ArrayList<>();

		setVisible(true); // Rend la fenêtre visible
		setDefaultCloseOperation(EXIT_ON_CLOSE); // Quitte le programme quand on ferme la fenêtre

		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		setSize(screenSize.width / 2, (screenSize.height * 9) / 10);
		setResizable(false);

		getContentPane().setBackground(Color.BLACK);
		getContentPane().setLayout(new GridBagLayout());

		GridBagConstraints c = new GridBagConstraints(); // Les contraintes de positionnement des composants
		c.insets = new Insets(5, 10, 5, 10); // Marge autour des éléments en pixels
		c.fill = GridBagConstraints.BOTH;

		panelLogo = new JPanel(new GridBagLayout());
		panelLogo.setBackground(Color.BLACK);

		c.weightx = 1;
		c.weighty = 0;
		c.gridx = 0;
		c.gridy = 0;
		panelLogo.add(new JLabel(), c); // Contraint le logo à se déplacer à droite

		c.weightx = 0;
		c.weighty = 0;
		c.gridx = 1;
		c.gridy = 0;
		panelLogo.add(logo, c); // Logo centré

		c.weightx = 1;
		c.weighty = 0;
		c.gridx = 2;
		c.gridy = 0;
		panelLogo.add(new JLabel(), c); // Contraint le logo à se déplacer à gauche

		logo.setIcon(VueJeu.redimensionnerImage(new ImageIcon("src/main/resources/logo_shazamm.gif"), 290, 85));
		logo.setBounds(this.getWidth() / 2 - 201, 0, 402, 100);

		c.weightx = 1;
		c.gridx = 0;
		c.gridy = 0;
		getContentPane().add(panelLogo, c);

		// Affichage du panneau contenant le pont, les sorciers et le mur
		panelJeu = new JPanel(new GridBagLayout());
		panelJeu.setBackground(Color.BLACK);
		c.weightx = 1;
		c.weighty = 0.5;
		c.gridx = 0;
		c.gridy = 1;
		getContentPane().add(panelJeu, c);

		// Affichage des sorciers et du mur de feu
		panelSorciers = new JPanel(new GridBagLayout());
		panelSorciers.setBackground(Color.BLACK);
		// TODO Peut-être qu'il faut remplir toutes les cases avec des labels vides et
		// les remplacer quand on met une vraie image
		c.gridx = Pont.TAILLE_PONT - 1;
		panelSorciers.add(new JLabel(), c); // Label vide qui occupe le dernier index
		c.gridx = 0;
		panelSorciers.add(new JLabel(), c); // Label vide qui occupe le premier index
		// Positions initiales
		positionSorcierRouge = 6;
		positionMurFeu = 9;
		positionSorcierVert = 12;
		// Décalage des images vers le bas
		c.insets = new Insets(50, 0, 0, 0);
		// Ajout des images
		c.gridx = positionSorcierRouge;
		panelSorciers.add(new JLabel(new ImageIcon(CHEMIN_SORCIER_ROUGE)), c);
		c.gridx = positionMurFeu;
		panelSorciers.add(new JLabel(new ImageIcon(CHEMIN_MUR_FEU)), c);
		c.gridx = positionSorcierVert;
		panelSorciers.add(new JLabel(new ImageIcon(CHEMIN_SORCIER_VERT)), c);
		// Ajout du panel
		c.insets = new Insets(0, 20, 0, 20);
		c.weightx = 1;
		c.weighty = 2;
		c.gridx = 0;
		c.gridy = 0;
		panelJeu.add(panelSorciers, c);

		// Affichage du pont
		panelPont = new JPanel();
		panelPont.setBounds(0, this.getHeight() * 2 / 10, this.getWidth(), this.getHeight() * 2 / 10);
		panelPont.setBackground(Color.BLACK);
		imagesPont = new ArrayList<>();
		initPont();
		updatePont();
		c.weightx = 1;
		c.weighty = 0.5;
		c.gridx = 0;
		c.gridy = 1;
		c.insets = new Insets(-10, 10, 5, 10);
		panelJeu.add(panelPont, c);

		// Affichage des cartes de la main du joueur
		panelMain = new JPanel(new GridLayout(1, 0, 10, 10));
		panelMain.setBackground(Color.BLACK);
		imagesCartesJoueur = new ArrayList<>();
		this.paintMain();
		c.weightx = 1;
		c.weighty = 0;
		c.gridx = 0;
		c.gridy = 2;
		c.insets = new Insets(5, 10, 5, 10);
		getContentPane().add(panelMain, c);

		// Affichage du panel d'actions
		panelAction = new JPanel();
		panelAction.setBackground(Color.BLACK);
		JButton boutonJouer = new JButton("Jouer le tour");
		JButton historique = new JButton("Historique de la partie");
		JLabel mise = new JLabel();
		mise.setIcon(new ImageIcon("src/main/resources/fr_votremise_"
				+ Character.toLowerCase(joueur.getCouleur().toString().charAt(0)) + ".gif"));
		panelAction.add(boutonJouer);
		panelAction.add(historique);
		panelAction.add(mise);
		c.weightx = 1;
		c.weighty = 0;
		c.gridx = 0;
		c.gridy = 3;
		getContentPane().add(panelAction, c);

		// Affichage du mana
		barreMana = new JProgressBar();
		barreMana.setStringPainted(true);
		barreMana.setForeground(Color.MAGENTA);
		barreMana.setBackground(Color.BLACK);
		barreMana
				.setString("Mana : " + String.valueOf(Joueur.MANA_MAXIMUM) + "/" + String.valueOf(Joueur.MANA_MAXIMUM));
		barreMana.setValue(100);
		c.weightx = 1;
		c.weighty = 0;
		c.gridx = 0;
		c.gridy = 4;
		getContentPane().add(barreMana, c);
	}

	/**
	 * Pose les images du pont sur l'interface graphique
	 */
	private void initPont() {
		panelPont.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 0;
		c.weightx = 1;

		for (int i = 0; i < Pont.TAILLE_PONT; i++) {
			JLabel tmp = new JLabel();
			String source = this.getImageCasePont(i);
			tmp.setIcon(new ImageIcon(source));
			imagesPont.add(tmp);
			panelPont.add(tmp, c.gridx);
			c.gridx++;

			// Empêche les pièces du pont de se séparer les unes des autres
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

		for (int i = 0; i < mainJoueur.size(); i++) {
			Carte c = mainJoueur.get(i);
			JLabel tmp = new JLabel();
			ImageIcon image = new ImageIcon(c.getPath());
			tmp.setIcon(VueJeu.redimensionnerImage(image, 140, 250));
			tmp.setHorizontalAlignment(JLabel.CENTER);
			tmp.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1)); // passer les borders en constantes ?
			imagesCartesJoueur.add(tmp);
			panelMain.add(tmp);

			tmp.addMouseListener(new MouseListener() {
				@Override
				public void mouseClicked(MouseEvent e) {
				}

				@Override
				public void mouseEntered(MouseEvent e) {
					tmp.setBorder(BorderFactory.createLineBorder(Color.WHITE, 1));
				}

				@Override
				public void mouseExited(MouseEvent e) {
					Integer index = (Integer) panelMain.getComponentZOrder(tmp);
					if (cartesJouees.contains(index)) {
						tmp.setBorder(BorderFactory.createLineBorder(Color.RED, 1));
					} else {
						tmp.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));
					}
				}

				@Override
				public void mousePressed(MouseEvent e) {
					Integer index = (Integer) panelMain.getComponentZOrder(tmp);
					if (!cartesJouees.contains(index)) {
						cartesJouees.add(index);
					} else {
						cartesJouees.remove(index);
					}
					displayCartesJouees();
				}

				@Override
				public void mouseReleased(MouseEvent e) {
				}
			});
		}
	}

	private void displayCartesJouees() {
		for (int i = 0; i < this.imagesCartesJoueur.size(); i++) {
			if (this.cartesJouees.contains((Integer) i)) {
				this.imagesCartesJoueur.get(i).setBorder(BorderFactory.createLineBorder(Color.RED, 1));
			} else {
				this.imagesCartesJoueur.get(i).setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));
			}
		}
	}

	// TODO Mettre dans le modèle

	/**
	 * ---------------------------------------- --- (TEST) À déplacer dans le modèle
	 * --- ----------------------------------------
	 * 
	 * Déplace le sorcier vert à la position spécifiée (entre <code>0</code> et
	 * <code>Pont.TAILLE_PONT - 1</code>).
	 * 
	 * @param nvPosition La nouvelle position du sorcier
	 * @throws PositionInaccessibleException Exception levée si
	 *                                       <code>nvPosition</code> n'est pas
	 *                                       compris entre <code>0</code> et
	 *                                       <code>Pont.TAILLE_PONT - 1</code>.
	 * @see Pont
	 */
	private void deplacerSorcierVert(int nvPosition) {
		try {
			if (nvPosition < 0 || nvPosition >= Pont.TAILLE_PONT) {
				throw new PositionInaccessibleException();
			}

			try {
				panelSorciers.remove(panelSorciers.getComponent(0));
				;
			} catch (ArrayIndexOutOfBoundsException ex) {

			}

			JLabel labelSorcier = new JLabel(new ImageIcon("src/main/resources/perso/vert.gif"));
			GridBagConstraints c = new GridBagConstraints();
			c.gridx = nvPosition;

			panelSorciers.add(labelSorcier, c);
			panelSorciers.repaint();
			panelSorciers.revalidate();
		} catch (PositionInaccessibleException e) {
			return;
		}

	}

	/**
	 * Redimensionne un objet <code>ImageIcon</code> selon les paramètres spécifiés.
	 * 
	 * @param img     L'image à redimensionner
	 * @param largeur La nouvelle largeur
	 * @param hauteur La nouvelle hauteur
	 * @return L'image redimensionnée
	 */
	public static ImageIcon redimensionnerImage(ImageIcon img, int largeur, int hauteur) {
		Image image = img.getImage(); // Transformation en Image
		Image nvimg = image.getScaledInstance(largeur, hauteur, Image.SCALE_SMOOTH); // Redimensionnement
		return new ImageIcon(nvimg); // Transformation en ImageIcon
	}
}