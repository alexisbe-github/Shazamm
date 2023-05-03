package main.java.vue.jeu;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import main.java.controleur.jeu.ControleurCartes;
import main.java.controleur.jeu.ControleurJeu;
import main.java.model.jeu.ECouleurJoueur;
import main.java.model.jeu.Joueur;
import main.java.model.jeu.Pont;
import main.java.model.jeu.carte.Carte;
import main.java.model.jeu.partie.Partie;
import main.java.model.jeu.partie.Tour;
import main.java.utils.Utils;
import main.java.vue.ILancementStrategy;

/**
 * La fenêtre qui affiche les éléments du modèle sous forme de composants dans
 * une interface graphique.
 */
public class VueJeu extends JFrame implements ILancementStrategy {

	private Joueur joueur;
	private Partie partie;
	private JPanel panelLogo, panelPont, panelSorciers, panelJeu, panelMain, panelAction;
	private JProgressBar barreMana;
	private JTextField saisieMana;
	private JLabel logo = new JLabel();
	private List<JLabel> imagesPont, imagesCartesJoueur;
	private List<Integer> cartesJouees;

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
		// setSize(683, 691); // Affichage en 1366*768 px par défaut
		setResizable(false);

		getContentPane().setBackground(Color.BLACK);
		getContentPane().setLayout(new GridBagLayout());

		GridBagConstraints c = new GridBagConstraints(); // Les contraintes de positionnement des composants
		c.insets = new Insets(5, 10, 5, 10); // Marge autour des éléments en pixels
		c.fill = GridBagConstraints.BOTH;

		panelLogo = new JPanel(new GridBagLayout());
		panelLogo.setBackground(Color.BLACK);

		setConstraints(1, 0, 0, 0, c);
		panelLogo.add(new JLabel(), c); // Contraint le logo à se déplacer à droite

		setConstraints(0, 0, 1, 0, c);
		panelLogo.add(logo, c); // Logo centré

		setConstraints(1, 0, 2, 0, c);
		panelLogo.add(new JLabel(), c); // Contraint le logo à se déplacer à gauche

		logo.setIcon(Utils.redimensionnerImage(new ImageIcon("src/main/resources/logo_shazamm.gif"), 290, 85));
		logo.setBounds(this.getWidth() / 2 - 201, 0, 402, 100);

		setConstraints(1, 0, 0, 0, c);
		getContentPane().add(panelLogo, c);

		// Affichage du panneau contenant le pont, les sorciers et le mur
		panelJeu = new JPanel(new GridBagLayout());
		panelJeu.setBackground(Color.BLACK);

		setConstraints(1, 0.5, 0, 1, c);
		getContentPane().add(panelJeu, c);

		// Affichage des sorciers et du mur de feu
		panelSorciers = new JPanel(new GridBagLayout());
		panelSorciers.setBackground(Color.BLACK);

		updateSorciersEtMur();

		// Ajout du panel
		c.insets = new Insets(0, 20, 0, 20);
		setConstraints(1, 2, 0, 0, c);
		panelJeu.add(panelSorciers, c);

		// Affichage du pont
		panelPont = new JPanel();
		panelPont.setBounds(0, this.getHeight() * 2 / 10, this.getWidth(), this.getHeight() * 2 / 10);
		panelPont.setBackground(Color.BLACK);
		imagesPont = new ArrayList<>();
		initPont();
		updatePont();
		setConstraints(1, 0.5, 0, 1, c);
		c.insets = new Insets(-10, 10, 5, 10);
		panelJeu.add(panelPont, c);

		// Affichage des cartes de la main du joueur
		panelMain = new JPanel(new GridLayout(1, 0, 10, 10));
		panelMain.setBackground(Color.BLACK);
		imagesCartesJoueur = new ArrayList<>();
		this.paintMain();
		setConstraints(1, 0, 0, 2, c);
		c.insets = new Insets(5, 10, 5, 10);
		getContentPane().add(panelMain, c);

		// Affichage du panel d'actions
		panelAction = new JPanel();
		panelAction.setBackground(Color.BLACK);
		JButton boutonJouer = new JButton("Jouer le tour");
		ControleurJeu cj = new ControleurJeu(this);
		boutonJouer.addActionListener(cj);
		JButton historique = new JButton("Historique de la partie");
		JLabel mise = new JLabel();
		saisieMana = new JTextField("1", 10);
		new TexteFantome(saisieMana, "Entrer la mise…");

		saisieMana.addKeyListener(new KeyAdapter() {

			@Override
			public void keyPressed(KeyEvent e) {
				try {
					// On vérifie que la saisie est comprise entre 1 et le mana du joueur
					boolean saisieCorrecte = ((Character.isDigit(e.getKeyChar())
							|| e.getKeyCode() == KeyEvent.VK_BACK_SPACE || e.getKeyCode() == KeyEvent.VK_DELETE
							|| e.getKeyCode() == KeyEvent.VK_LEFT || e.getKeyCode() == KeyEvent.VK_RIGHT)
							&& ((Integer.parseInt(saisieMana.getText()
									+ (e.getKeyChar() == KeyEvent.VK_BACK_SPACE ? "" : e.getKeyChar())) <= joueur
											.getManaActuel())
									&& (Integer.parseInt(saisieMana.getText()
											+ (e.getKeyChar() == KeyEvent.VK_BACK_SPACE ? "" : e.getKeyChar())) >= 1)));

					if (saisieCorrecte) {
						saisieMana.setEditable(true);
					} else {
						saisieMana.setEditable(false);
						// Fondu vers le rouge pour prévenir l'utilisateur d'une saisie incorrecte
						Utils.fonduArrierePlan(saisieMana, new Color(255, 43, 28), 8, 15);
					}

					if (saisieMana.getText().length() == 1
							&& (e.getKeyCode() == KeyEvent.VK_BACK_SPACE || e.getKeyCode() == KeyEvent.VK_DELETE)) {
						boutonJouer.setEnabled(false);
					} else
						boutonJouer.setEnabled(true);

				} catch (NumberFormatException ex) {

				}
			}
		});

		mise.setIcon(new ImageIcon("src/main/resources/fr_votremise_"
				+ Character.toLowerCase(joueur.getCouleur().toString().charAt(0)) + ".gif"));
		panelAction.add(boutonJouer);
		panelAction.add(historique);
		panelAction.add(mise);
		panelAction.add(saisieMana);
		setConstraints(1, 0, 0, 3, c);
		getContentPane().add(panelAction, c);

		// Affichage du mana
		barreMana = new JProgressBar();
		barreMana.setStringPainted(true);
		barreMana.setForeground(Color.MAGENTA);
		barreMana.setBackground(Color.BLACK);
		barreMana
				.setString("Mana : " + String.valueOf(Joueur.MANA_MAXIMUM) + "/" + String.valueOf(Joueur.MANA_MAXIMUM));
		barreMana.setValue(100);
		setConstraints(1, 0, 0, 4, c);
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
			tmp.setIcon(Utils.redimensionnerImage(image, 140, 250));
			tmp.setHorizontalAlignment(JLabel.CENTER);
			tmp.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1)); // passer les borders en constantes ?
			imagesCartesJoueur.add(tmp);
			panelMain.add(tmp);

			tmp.addMouseListener(new ControleurCartes(this.panelMain, this.cartesJouees, this));
		}
	}

	public void displayCartesJouees() {
		for (int i = 0; i < this.imagesCartesJoueur.size(); i++) {
			if (this.cartesJouees.contains((Integer) i)) {
				this.imagesCartesJoueur.get(i).setBorder(BorderFactory.createLineBorder(Color.RED, 1));
			} else {
				this.imagesCartesJoueur.get(i).setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));
			}
		}
	}

	/**
	 * @param couleur : couleur du sorcier
	 * @return Le label contenant le sorcier en question
	 */
	private JLabel getLabelSorcier(ECouleurJoueur couleur) {
		for (Component c : panelSorciers.getComponents()) {
			try {
				JLabel label = (JLabel) c;
				ImageIcon image = (ImageIcon) label.getIcon();
				if ((image.getDescription() == partie.getJoueurRouge().getPath()
						&& couleur.equals(ECouleurJoueur.ROUGE))
						|| (image.getDescription() == partie.getJoueurVert().getPath()
								&& couleur.equals(ECouleurJoueur.VERT))) {
					return label;
				} else if (image.getDescription() == partie.getJoueurVert().getPath()
						&& couleur.equals(ECouleurJoueur.VERT)) {

				}
			} catch (NullPointerException e) {

			}
		}
		return null;
	}

	/**
	 * @return Le label contenant le mur de feu
	 */
	private JLabel getLabelMurFeu() {
		for (Component c : panelSorciers.getComponents()) {
			try {
				JLabel label = (JLabel) c;
				ImageIcon image = (ImageIcon) label.getIcon();
				if (image.getDescription() == partie.getPont().getPath()) {
					return label;
				}
			} catch (NullPointerException e) {

			}
		}
		return null;
	}

	/**
	 * Factorise la modification répétitive des contraintes
	 */
	private void setConstraints(double weightx, double weighty, int gridx, int gridy, GridBagConstraints c) {
		c.weightx = weightx;
		c.weighty = weighty;
		c.gridx = gridx;
		c.gridy = gridy;
	}

	/**
	 * @return Le joueur associé à la fenêtre
	 */
	public Joueur getJoueur() {
		return this.joueur;
	}

	/**
	 * @return La mise du joueur
	 */
	public int getMise() {
		try {
			return Integer.parseInt(this.saisieMana.getText());
		} catch (Exception e) {
			return 0;
		}
	}

	private void updateSorciersEtMur() {
		GridBagConstraints c = new GridBagConstraints();
		setConstraints(1, 0.5, 0, 1, c);
		for (int i = 0; i < Pont.TAILLE_PONT - 1; i++) {
			c.gridx++;
			c.gridx = i;
			panelSorciers.add(new JLabel(), c);
		}

		// Décalage des images vers le bas
		c.insets = new Insets(50, -32, 0, 0);

		// Affichage Joueurs / Mur
		c.gridx = partie.getPosJoueur(ECouleurJoueur.ROUGE);
		panelSorciers.add(new JLabel(new ImageIcon(partie.getJoueurRouge().getPath())), c);
		c.gridx = partie.getPont().getPosMurDeFeu();
		panelSorciers.add(new JLabel(new ImageIcon(partie.getPont().getPath())), c);
		c.gridx = partie.getPosJoueur(ECouleurJoueur.VERT);
		panelSorciers.add(new JLabel(new ImageIcon(partie.getJoueurVert().getPath())), c);
	}

	/**
	 * Met à jour la barre de mana avec la valeur spécifiée
	 * 
	 * @param nv La nouvelle valeur
	 */
	public void updateBarreMana(int nv) {

		if (nv < 0 || nv > 50) {
			return;
		}

		// Animation pour faire baisser progressivement les réserves de mana
		new Thread(new Runnable() {

			@Override
			public void run() {
				int anc = barreMana.getValue();
				int mana = anc / 2;
				while (anc > nv * 2) {
					anc -= 2;
					mana--;
					barreMana.setValue(anc);
					barreMana.setString("Mana : " + mana + "/" + Joueur.MANA_MAXIMUM);

					try {
						Thread.sleep(10);
					} catch (InterruptedException e) {
					}
				}
			}
		}).start();
	}

	/**
	 * Réinitialise le champ de saisie de la mise avec la valeur spécifiée
	 * 
	 * @param valeur La valeur avec laquelle réinitialiser
	 */
	public void reinitialiserTextField(String valeur) {
		saisieMana.setText(valeur);
	}

	/**
	 * Permet d'afficher un texte fantôme en arrière-plan d'un champ textuel
	 * 
	 * <a href=
	 * "https://stackoverflow.com/questions/10506789/how-to-display-faint-gray-ghost-text-in-a-jtextfield">Lien
	 * StackOverflow</a>
	 */
	private class TexteFantome implements FocusListener, DocumentListener, PropertyChangeListener {
		private final JTextField textField;
		private boolean isEmpty;
		private Color ghostColor;
		private Color foregroundColor;
		private final String ghostText;

		private TexteFantome(final JTextField textField, String ghostText) {
			super();
			this.textField = textField;
			this.ghostText = ghostText;
			this.ghostColor = Color.DARK_GRAY;
			textField.addFocusListener(this);
			registerListeners();
			updateState();
			if (!this.textField.hasFocus()) {
				focusLost(null);
			}
		}

		public void delete() {
			unregisterListeners();
			textField.removeFocusListener(this);
		}

		private void registerListeners() {
			textField.getDocument().addDocumentListener(this);
			textField.addPropertyChangeListener("foreground", this);
		}

		private void unregisterListeners() {
			textField.getDocument().removeDocumentListener(this);
			textField.removePropertyChangeListener("foreground", this);
		}

		public Color getGhostColor() {
			return ghostColor;
		}

		public void setGhostColor(Color ghostColor) {
			this.ghostColor = ghostColor;
		}

		private void updateState() {
			isEmpty = textField.getText().length() == 0;
			foregroundColor = textField.getForeground();
		}

		@Override
		public void focusGained(FocusEvent e) {
			if (isEmpty) {
				unregisterListeners();
				try {
					textField.setText("");
					textField.setForeground(foregroundColor);
				} finally {
					registerListeners();
				}
			}

		}

		@Override
		public void focusLost(FocusEvent e) {
			if (isEmpty) {
				unregisterListeners();
				try {
					textField.setText(ghostText);
					textField.setForeground(ghostColor);
				} finally {
					registerListeners();
				}
			}
		}

		@Override
		public void propertyChange(PropertyChangeEvent evt) {
			updateState();
		}

		@Override
		public void changedUpdate(DocumentEvent e) {
			updateState();
		}

		@Override
		public void insertUpdate(DocumentEvent e) {
			updateState();
		}

		@Override
		public void removeUpdate(DocumentEvent e) {
			updateState();
		}

	}

	@Override
	public void lancerClone(Partie p, Tour tour, Joueur joueur) {
		// TODO Auto-generated method stub

	}

	@Override
	public void lancerRecyclage(Partie p, Tour tour, Joueur joueur) {
		// TODO Auto-generated method stub

	}

	@Override
	public void lancerLarcin(Partie p, Tour tour, Joueur joueur) {
		// TODO Auto-generated method stub

	}
}