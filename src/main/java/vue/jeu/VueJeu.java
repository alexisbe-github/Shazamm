package main.java.vue.jeu;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dialog.ModalityType;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

import main.java.controleur.jeu.ControleurCartes;
import main.java.controleur.jeu.ControleurJeu;
import main.java.controleur.jeu.ControleurMana;
import main.java.model.jeu.ECouleurJoueur;
import main.java.model.jeu.Joueur;
import main.java.model.jeu.Pont;
import main.java.model.jeu.carte.Carte;
import main.java.model.jeu.partie.Partie;
import main.java.model.jeu.partie.Tour;
import main.java.utils.TexteFantome;
import main.java.utils.Utils;
import main.java.vue.ILancementStrategy;

/**
 * La fenêtre qui affiche les éléments du modèle sous forme de composants dans
 * une interface graphique.
 */
public class VueJeu extends JFrame implements ILancementStrategy, PropertyChangeListener {

	private Joueur joueur;
	private Partie partie;
	private JPanel panelLogo, panelPont, panelSorciers, panelJeu, panelMain, panelAction;
	private JProgressBar barreMana;
	private JTextField saisieMana;
	private JButton boutonJouer;
	private JLabel logo = new JLabel();
	private JLabel labelManaAdversaire, labelInfos;
	private List<Integer> cartesJouees;
	private int choix; // choix pour les cartes qui nécéssitent une sélection

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

		// Label infos
		labelInfos = new JLabel();
		labelInfos.setHorizontalAlignment(JLabel.CENTER);
		labelInfos.setFont(new Font("Serif", Font.PLAIN, 14));
		updateInfos();
		setConstraints(0, 0, 0, 1, c);
		labelInfos.setForeground(Color.LIGHT_GRAY);

		getContentPane().add(labelInfos, c);

		// Affichage du panneau contenant le pont, les sorciers et le mur
		panelJeu = new JPanel(new GridBagLayout());
		panelJeu.setBackground(Color.BLACK);

		// Affichage des sorciers et du mur de feu
		initSorciersEtMur();

		// Ajout du panel
		c.insets = new Insets(0, 20, 0, 20);
		c.anchor = GridBagConstraints.SOUTH;
		c.fill = GridBagConstraints.VERTICAL;
		setConstraints(1, 0.5, 0, 0, c);
		panelJeu.add(panelSorciers, c);

		// Affichage du pont
		panelPont = new JPanel();
		panelPont.setBounds(0, this.getHeight() * 2 / 10, this.getWidth(), this.getHeight() * 2 / 10);
		panelPont.setBackground(Color.BLACK);
		initPont();
		updatePont();
		setConstraints(1, 0.5, 0, 1, c);
		c.insets = new Insets(0, 10, 5, 10);
		c.ipady = 0;
		c.anchor = GridBagConstraints.CENTER;
		c.fill = GridBagConstraints.NONE;
		panelJeu.add(panelPont, c);

		panelJeu.setComponentZOrder(panelSorciers, 0); // On met les sorciers au-dessus
		panelJeu.setComponentZOrder(panelPont, 1);

		c.insets = new Insets(0, 10, 0, 10);
		setConstraints(1, 0.5, 0, 2, c);
		getContentPane().add(panelJeu, c);

		// Affichage des cartes de la main du joueur
		panelMain = new JPanel(new GridLayout(1, 0, 10, 10));
		panelMain.setBackground(Color.BLACK);
		this.paintMain();
		setConstraints(1, 0, 0, 3, c);
		c.insets = new Insets(5, 10, 5, 10);
		c.fill = GridBagConstraints.BOTH;
		getContentPane().add(panelMain, c);

		// Affichage du panel d'actions
		panelAction = new JPanel();
		panelAction.setBackground(Color.BLACK);
		boutonJouer = new JButton("Jouer le tour");
		ControleurJeu cj = new ControleurJeu(this, partie);
		boutonJouer.addActionListener(cj);
		JButton historique = new JButton("Historique de la partie");
		JLabel mise = new JLabel();
		saisieMana = new JTextField("1", 10);
		new TexteFantome(saisieMana, "Entrer la mise…");

		saisieMana.addKeyListener(new ControleurMana(saisieMana, boutonJouer, this));

		mise.setIcon(new ImageIcon("src/main/resources/fr_votremise_"
				+ Character.toLowerCase(joueur.getCouleur().toString().charAt(0)) + ".gif"));
		labelManaAdversaire = new JLabel();
		labelManaAdversaire.setForeground(Color.LIGHT_GRAY);
		this.updateManaAdversaire();
		panelAction.add(boutonJouer);
		panelAction.add(historique);
		panelAction.add(mise);
		panelAction.add(saisieMana);
		panelAction.add(labelManaAdversaire);
		setConstraints(1, 0, 0, 4, c);
		getContentPane().add(panelAction, c);

		// Affichage du mana
		barreMana = new JProgressBar();
		barreMana.setStringPainted(true);
		barreMana.setForeground(Color.MAGENTA);
		barreMana.setBackground(Color.BLACK);
		barreMana
				.setString("Mana : " + String.valueOf(Joueur.MANA_MAXIMUM) + "/" + String.valueOf(Joueur.MANA_MAXIMUM));
		barreMana.setValue(100);
		setConstraints(1, 0, 0, 5, c);
		getContentPane().add(barreMana, c);
	}

	private void updateInfos() {
		labelInfos.setText(getInfos());
	}

	private void updateManaAdversaire() {
		labelManaAdversaire.setText(String.format("Mana de l'adversaire : %d", getManaAdversaire()));
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

	public List<Carte> getCartesJouees() {
		List<Carte> cartes = new ArrayList<>();
		for (int i = 0; i < joueur.getMainDuJoueur().size(); i++) {
			Carte c = joueur.getMainDuJoueur().get(i);
			if (cartesJouees.contains(i)) {
				cartes.add(c);
			}
		}
		return cartes;
	}

	/**
	 * Met à jour les images du pont
	 */
	private void updatePont() {
		for (int i = 0; i < panelPont.getComponentCount(); i++) {
			JLabel tmp = (JLabel) panelPont.getComponent(i);
			tmp.setIcon(new ImageIcon(this.getImageCasePont(i)));
		}
	}

	private void initSorciersEtMur() {
		panelSorciers = new JPanel(new GridBagLayout());
		panelSorciers.setBackground(Color.BLACK);
		updateSorciersEtMur();
	}

	// Met à jour les images des sorciers et du mur
	private void updateSorciersEtMur() {
		panelSorciers.removeAll();
		GridBagConstraints c = new GridBagConstraints();
		setConstraints(1, 0.5, 0, 1, c);

		ImageIcon temp = new ImageIcon(partie.getJoueurRouge().getPath());
		BufferedImage bi = new BufferedImage(temp.getIconWidth(), temp.getIconHeight(), BufferedImage.TYPE_INT_ARGB);
		ImageIcon icon = new ImageIcon(bi);
		for (int i = 0; i < Pont.TAILLE_PONT; i++) {
			c.gridx = i;
			JLabel l = new JLabel();
			l.setIcon(icon);
			panelSorciers.add(l, c);
		}

		// Affichage Joueurs / Mur
		c.gridx = partie.getPosJoueur(ECouleurJoueur.ROUGE);
		panelSorciers.add(new JLabel(new ImageIcon(partie.getJoueurRouge().getPath())), c);
		c.gridx = partie.getPont().getPosMurDeFeu();
		panelSorciers.add(new JLabel(new ImageIcon(partie.getPont().getPathMur())), c);
		c.gridx = partie.getPosJoueur(ECouleurJoueur.VERT);
		panelSorciers.add(new JLabel(new ImageIcon(partie.getJoueurVert().getPath())), c);
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

		panelMain.removeAll();
		panelMain.repaint();

		for (int i = 0; i < mainJoueur.size(); i++) {
			Carte c = mainJoueur.get(i);
			JLabel tmp = new JLabel();
			ImageIcon image = new ImageIcon(c.getPath());
			tmp.setIcon(Utils.redimensionnerImage(image, 140, 250));
			tmp.setHorizontalAlignment(JLabel.CENTER);
			// tmp.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1)); // passer les
			// borders en constantes ?
			panelMain.add(tmp);

			tmp.addMouseListener(new ControleurCartes(this.panelMain, this.cartesJouees, this));
		}
	}

	public void displayCartesJouees() {
		for (int i = 0; i < joueur.getMainDuJoueur().size(); i++) {
			if (this.cartesJouees.contains(i)) {
				((JLabel) panelMain.getComponent(i)).setBorder(BorderFactory.createLineBorder(Color.RED, 1));
			} else {
				((JLabel) panelMain.getComponent(i)).setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
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
				if (image.getDescription() == partie.getPont().getPathMur()) {
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

	/**
	 * Met à jour la barre de mana avec la valeur spécifiée
	 * 
	 */
	public void updateBarreMana() {

		// Animation pour faire baisser progressivement les réserves de mana
//		new Thread(new Runnable() {
//
//			@Override
//			public void run() {
//				int anc = barreMana.getValue();
//				int mana = anc / 2;
//				while (anc > nv * 2) {
//					anc -= 2;
//					mana--;
		barreMana.setValue(joueur.getManaActuel() * 2);
		barreMana.setString("Mana : " + joueur.getManaActuel() + "/" + Joueur.MANA_MAXIMUM);
//
//					try {
//						Thread.sleep(10);
//					} catch (InterruptedException e) {
//					}
//				}
//			}
//		}).start();
	}

	/**
	 * @return Les infos actuelles sur la partie
	 */
	private String getInfos() {
		String str = "<html>";
		str += "      <b>Manche " + this.partie.getNombreManches() + " - ";
		str += "Tour " + this.partie.getMancheCourante().getNumeroTourCourant() + "<br>";
		str += "Joueur " + (this.joueur.getCouleur().equals(ECouleurJoueur.ROUGE) ? "rouge" : "vert");

		str += " - " + joueur.getNom() + "</b></html>";
		return str;
	}

	/**
	 * @return Le solde de mana de l'adversaire
	 */
	private int getManaAdversaire() {
		switch (this.joueur.getCouleur()) {
		case ROUGE:
			return this.partie.getJoueurVert().getManaActuel();
		case VERT:
			return this.partie.getJoueurRouge().getManaActuel();
		default:
			return 0;
		}
	}

	/**
	 * Renvoie une marge permettant de positionner les sorciers et le mur de feu en
	 * fonction de leur position sur le pont
	 * 
	 * @param position La position (entre <code>0</code> et
	 *                 <code>{@link Pont.TAILLE_PONT} - 1</code>).
	 * @return La marge correspondante à appliquer. Retourne <code>null</code> si la
	 *         case n'existe pas.
	 */
	private Insets getMargePont(int position) {

		if (position < 0 || position >= Pont.TAILLE_PONT)
			return null;

		if (this.partie.getPont().estUneCaseLave(position))
			return new Insets(0, 0, 0, 0);

		String cheminImage = "src/main/resources/pont/pont_";
		if (position < 9)
			cheminImage += "0";
		cheminImage += (position + 1) + ".gif";
		int hauteurImage = new ImageIcon(cheminImage).getIconHeight();
		return new Insets(104 - hauteurImage, 0, 0, 0);
	}

	/**
	 * Réinitialise le champ de saisie de la mise avec la valeur spécifiée
	 * 
	 * @param valeur La valeur avec laquelle réinitialiser
	 */
	private void reinitialiserTextField(String valeur) {
		saisieMana.setText(valeur);
	}

	@Override
	public void lancerClone(Partie p, Tour tour, Joueur joueur) {
		JFrame fenetreClone = new JFrame();
		JDialog jd = new JDialog(fenetreClone,
				"Effet Clone pour Joueur " + joueur.getCouleur().toString().toLowerCase(), true);
		jd.setModalityType(ModalityType.APPLICATION_MODAL);
		jd.setSize(800, 400);

		JLabel label = new JLabel("Choisissez la carte à cloner");
		label.setForeground(Color.WHITE);
		label.setBackground(Color.BLACK);
		label.setOpaque(true);
		label.setHorizontalAlignment(JLabel.CENTER);

		JPanel panelCartes = new JPanel();
		panelCartes.setBackground(Color.BLACK);

		JButton valider = new JButton("Valider");
		valider.setEnabled(false);

		choix = 0;

		// On récupère les cartes jouées par l'adversaire au tour précédent
		List<Carte> cartes = p.getCartesJoueesParAdversaireTourPrecedent(joueur);
		for (Carte c : cartes) {
			JLabel tmp = new JLabel();
			ImageIcon image = new ImageIcon(c.getPath());
			tmp.setIcon(Utils.redimensionnerImage(image, 140, 250));
			tmp.setHorizontalAlignment(JLabel.CENTER);
			panelCartes.add(tmp);
			tmp.addMouseListener(new MouseListener() {

				@Override
				public void mouseClicked(MouseEvent e) {
				}

				@Override
				public void mousePressed(MouseEvent e) {
					if (SwingUtilities.isLeftMouseButton(e)) {
						JLabel tmp = (JLabel) e.getComponent();
						Integer index = panelCartes.getComponentZOrder(tmp);
						choix = index;
						valider.setEnabled(true);
						for (Component c : panelCartes.getComponents()) {
							JLabel labelCourant = (JLabel) c;
							if (c.equals(tmp))
								tmp.setBorder(BorderFactory.createLineBorder(Color.RED, 1));
							else
								labelCourant.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
						}
					}
				}

				@Override
				public void mouseReleased(MouseEvent e) {
				}

				@Override
				public void mouseEntered(MouseEvent e) {
				}

				@Override
				public void mouseExited(MouseEvent e) {
				}

			});
		}

		valider.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				Carte carteAVoler = cartes.get(choix);
				carteAVoler.changerDetenteurCarte(joueur);
				fenetreClone.dispose();
			}

		});

		jd.add(label, BorderLayout.NORTH);
		jd.add(panelCartes, BorderLayout.CENTER);
		jd.add(valider, BorderLayout.SOUTH);
		jd.setVisible(true);
		jd.setResizable(false);
		jd.setLocation(this.getLocation());
		jd.setAlwaysOnTop(true);
	}

	@Override
	public void lancerRecyclage(Partie p, Tour tour, Joueur joueur) {
		JFrame fenetreClone = new JFrame();
		JDialog jd = new JDialog(fenetreClone,
				"Effet Recyclage pour Joueur " + joueur.getCouleur().toString().toLowerCase(), true);

		choix = 0;

		JLabel mise = new JLabel();
		mise.setText("Entrer la mise à recycler entre +5 et -5 dans la limite de votre mana:");

		JTextField saisieManaRecyclage = new JTextField("0", 5);

		JButton valider = new JButton("Valider");

		saisieManaRecyclage.addKeyListener(new KeyAdapter() {

			@Override
			public void keyPressed(KeyEvent e) {
				try {
					// On vérifie que la saisie est comprise entre -5 et 5 et que ça ne génère pas
					// d'erreur sur la réserve de mana
					boolean saisieCorrecte = (((Character.isDigit(e.getKeyChar())
							&& Integer.parseInt(String.valueOf(e.getKeyChar())) < 6))
							|| (e.getKeyChar() == '-' && saisieManaRecyclage.getCaretPosition() == 0)
							|| e.getKeyCode() == KeyEvent.VK_BACK_SPACE || e.getKeyCode() == KeyEvent.VK_DELETE
							|| e.getKeyCode() == KeyEvent.VK_LEFT || e.getKeyCode() == KeyEvent.VK_RIGHT
							|| e.getKeyCode() == KeyEvent.VK_SHIFT || e.getKeyCode() == KeyEvent.VK_CONTROL)
							&& saisieManaRecyclage.getText().length() < 2;
					if (saisieCorrecte) {
						saisieManaRecyclage.setEditable(true);
					} else {
						saisieManaRecyclage.setEditable(false);
						// Fondu vers le rouge pour prévenir l'utilisateur d'une saisie incorrecte
						Utils.fonduArrierePlan(saisieManaRecyclage, new Color(255, 43, 28), 8, 15);
					}

					if (saisieManaRecyclage.getText().length() == 1
							&& (e.getKeyCode() == KeyEvent.VK_BACK_SPACE || e.getKeyCode() == KeyEvent.VK_DELETE)) {
						valider.setEnabled(false);
					} else {
						valider.setEnabled(true);
						choix = Integer.parseInt(saisieManaRecyclage.getText());
					}
				} catch (NumberFormatException ex) {

				}
			}
		});

		valider.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				tour.changerMise(joueur, choix);
			}

		});

		jd.setLayout(new FlowLayout());
		jd.add(mise);
		jd.add(saisieManaRecyclage);
		jd.add(valider);
		jd.setModalityType(ModalityType.APPLICATION_MODAL);
		jd.setSize(500, 70);
		jd.setVisible(true);
		jd.setResizable(false);
		jd.setLocation(this.getLocation());
		jd.setAlwaysOnTop(true);
	}

	@Override
	public void lancerLarcin(Partie p, Tour tour, Joueur joueur) {
		JFrame fenetreClone = new JFrame();
		JDialog jd = new JDialog(fenetreClone,
				"Effet Larcin pour Joueur " + joueur.getCouleur().toString().toLowerCase(), true);
		jd.setModalityType(ModalityType.APPLICATION_MODAL);
		jd.setSize(800, 400);

		JLabel label = new JLabel(
				"Choisissez les cartes à jouer sous votre contrôle, le reste sera annulé puis défausser:");
		label.setForeground(Color.WHITE);
		label.setBackground(Color.BLACK);
		label.setOpaque(true);
		label.setHorizontalAlignment(JLabel.CENTER);

		JPanel panelCartes = new JPanel();
		panelCartes.setBackground(Color.BLACK);

		JButton valider = new JButton("Valider");

		List<Integer> listeCartesAPrendre = new ArrayList<Integer>();

		// On récupère les cartes jouées par l'adversaire
		List<Carte> cartes;
		if (joueur.getCouleur().equals(ECouleurJoueur.ROUGE)) {
			cartes = p.getListeCartesJoueesParJoueur(p.getJoueurVert());
		} else {
			cartes = p.getListeCartesJoueesParJoueur(p.getJoueurRouge());
		}
		for (Carte c : cartes) {
			JLabel tmp = new JLabel();
			ImageIcon image = new ImageIcon(c.getPath());
			tmp.setIcon(Utils.redimensionnerImage(image, 140, 250));
			tmp.setHorizontalAlignment(JLabel.CENTER);
			panelCartes.add(tmp);
			tmp.addMouseListener(new MouseListener() {

				@Override
				public void mouseClicked(MouseEvent e) {
				}

				@Override
				public void mousePressed(MouseEvent e) {
					if (SwingUtilities.isLeftMouseButton(e)) {
						JLabel tmp = (JLabel) e.getComponent();
						Integer index = panelCartes.getComponentZOrder(tmp);
						if (listeCartesAPrendre.contains(index)) {
							listeCartesAPrendre.remove(index);
						} else {
							listeCartesAPrendre.add(index);
						}

						for (int i = 0; i < panelCartes.getComponentCount(); i++) {
							JLabel labelCourant = (JLabel) panelCartes.getComponent(i);
							if (!listeCartesAPrendre.contains(i)) {
								labelCourant.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
							} else {
								labelCourant.setBorder(BorderFactory.createLineBorder(Color.RED, 1));
							}
						}
					}
				}

				@Override
				public void mouseReleased(MouseEvent e) {
				}

				@Override
				public void mouseEntered(MouseEvent e) {
				}

				@Override
				public void mouseExited(MouseEvent e) {
				}

			});
		}

		valider.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				for (int i = 0; i < cartes.size(); i++) {
					Carte carte = cartes.get(i);
					if (listeCartesAPrendre.contains(i)) {
						carte.changerDetenteurCarte(joueur);
					} else {
						carte.defausser();
					}
				}
				fenetreClone.dispose();
			}

		});

		jd.add(label, BorderLayout.NORTH);
		jd.add(panelCartes, BorderLayout.CENTER);
		jd.add(valider, BorderLayout.SOUTH);
		jd.setVisible(true);
		jd.setResizable(false);
		jd.setLocation(this.getLocation());
		jd.setAlwaysOnTop(true);
	}

	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		this.updateBarreMana();
		this.updateManaAdversaire();
		this.updateInfos();
		this.paintMain();
		this.updatePont();
		this.updateSorciersEtMur();
		this.reinitialiserTextField("1");
		this.cartesJouees.clear();
		boutonJouer.setEnabled(true);
	}
}