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
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.plaf.basic.BasicScrollBarUI;

import main.java.controleur.jeu.ControleurCartes;
import main.java.controleur.jeu.ControleurJeu;
import main.java.controleur.jeu.ControleurMana;
import main.java.model.jeu.Chrono;
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
public class VueJeu extends JFrame implements ILancementStrategy, PropertyChangeListener {

	private Joueur joueur;
	private Partie partie;
	private JPanel panelLogo, panelPont, panelSorciers, panelJeu, panelMain, panelAction, panelCartesJouees;
	private JProgressBar barreMana;
	private JTextField saisieMana;
	private JButton boutonJouer;
	private JLabel logo = new JLabel();
	private JLabel labelManaAdversaire, labelInfos, labelInfosTour;
	private List<Integer> cartesJouees;
	private int choix; // choix pour les cartes qui nécéssitent une sélection
	Chrono timer;

	/**
	 * Construit un objet <code>Fenetre</code> avec le titre spécifié, qui
	 * correspond à l'interface graphique affichant le jeu.
	 */
	public VueJeu(Joueur joueur, Partie partie, Chrono timer) {
		this.joueur = joueur;
		this.partie = partie;
		this.timer=timer;
		VueChrono labelTimer = new VueChrono(timer);
		timer.setVueJeu(this);
		timer.startChrono();
		
		cartesJouees = new ArrayList<>();
		
		int hauteurElement = 0; // Variable permettant de gérer la hauteur des élements dans le gridbag layout. à incrémenter avant utilisation.

		setVisible(true); // Rend la fenêtre visible
		setDefaultCloseOperation(EXIT_ON_CLOSE); // Quitte le programme quand on ferme la fenêtre

		//Redimensionne la frame
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		setSize(screenSize.width / 2, screenSize.height * 9 / 10);
		setResizable(false);

		//Couleur d'arrière plan et ajout du grid bag layout
		getContentPane().setBackground(Color.BLACK);
		getContentPane().setLayout(new GridBagLayout());

		//Création de le contrainte et initialisation
		GridBagConstraints c = new GridBagConstraints(); // Les contraintes de positionnement des composants
		c.insets = new Insets(5, 10, 5, 10); // Marge autour des éléments en pixels
		c.fill = GridBagConstraints.BOTH;

		
		
		
		//Création du panel du logo
		panelLogo = new JPanel(new GridLayout(1,3)); //Création du panel principal du logo
		panelLogo.setBackground(Color.BLACK);	
		
		//Création du label info sorcier et ajout au panel infos joueur
		JLabel logoJoueur = new JLabel();
		logoJoueur.setIcon(Utils.redimensionnerImage(new ImageIcon("src/main/resources/avatars/popsimoke.png"), this.getHeight()/13));
		logoJoueur.setBorder(BorderFactory.createLineBorder(Color.BLACK,3));
		JLabel labelSorcier = new JLabel(
				"<html><b>Sorcier " + (this.joueur.getCouleur().equals(ECouleurJoueur.ROUGE) ? "rouge" : "vert") + "<br>"
						+ joueur.getNom() + "</b></html>");
		labelSorcier.setFont(new Font("Verdana", Font.PLAIN, this.getWidth()/70));
		labelSorcier.setForeground(Color.BLACK);
		JPanel panelInfosJoueur = new JPanel();
		panelInfosJoueur.add(logoJoueur);
		panelInfosJoueur.add(labelSorcier);
		
		if(joueur.getCouleur().equals(ECouleurJoueur.ROUGE)) {
			panelInfosJoueur.setBackground(new Color(176,47,47));
		}else {
			panelInfosJoueur.setBackground(new Color(47,176,47));
		}
		Utils.setConstraints(1, 0, 1, 0, c);
		panelLogo.add(panelInfosJoueur, c);
		
		//création et ajout de l'image du logo
		logo.setIcon(Utils.redimensionnerImage(new ImageIcon("src/main/resources/logo_shazamm.gif"), this.getHeight()/13));
		Utils.setConstraints(1, 0, 0, 0, c);
		panelLogo.add(logo, c);
		
		JLabel limite = new JLabel();
		limite.setPreferredSize(new Dimension(this.getWidth()/3, this.getHeight()/10));
		panelLogo.add(limite);
		
		//Ajout du panel logo à la frame
		Utils.setConstraints(0, 0, 0, hauteurElement, c);
		getContentPane().add(panelLogo, c);
		
		
		
		
		// Création du Label infos joueur et ajout à la frame
		labelInfos = new JLabel();
		labelInfos.setHorizontalAlignment(JLabel.CENTER);
		labelInfos.setFont(new Font("Verdana", Font.PLAIN, 20));
		labelInfos.setForeground(Color.LIGHT_GRAY);
		updateInfos();
		hauteurElement++;
		Utils.setConstraints(0, 0, 0, hauteurElement, c);
		getContentPane().add(labelInfos, c);

		
		
		
		// Création du label timer et ajout à la fenêtre
		hauteurElement++;
		Utils.setConstraints(0, 0, 0, hauteurElement, c);
		getContentPane().add(labelTimer, c);

		
		
		
		// Création du panel Principal Jeu, contenant le pont, les sorciers et le mur
		panelJeu = new JPanel(new GridBagLayout());
		panelJeu.setBackground(Color.BLACK);
		// Affichage des sorciers et du mur de feu
		initSorciersEtMur();
		// ajout du panel sorciers au panel Jeu
		c.insets = new Insets(0, 20, 0, 20);
		c.anchor = GridBagConstraints.SOUTH;
		c.fill = GridBagConstraints.VERTICAL;
		Utils.setConstraints(0, 0, 0, 0, c);
		panelJeu.add(panelSorciers, c);
		// Création du panel pont et ajout au panel Jeu
		panelPont = new JPanel(new FlowLayout(FlowLayout.CENTER,0,0));
		panelPont.setBackground(Color.BLACK);
		initPont();
		updatePont();
		Utils.setConstraints(0, 0, 0, 1, c);
		panelPont.setBounds(0, this.getHeight() * 2 / 10, this.getWidth(), this.getHeight() * 2 / 10);
		c.insets = new Insets(0, 10, 5, 10);
		c.ipady = 0;
		c.anchor = GridBagConstraints.CENTER;
		c.fill = GridBagConstraints.NONE;
		panelJeu.add(panelPont, c);
		// On met les sorciers au-dessus du pont
		panelJeu.setComponentZOrder(panelSorciers, 0);
		panelJeu.setComponentZOrder(panelPont, 1);
		//Ajout du panel jeu principal à la frame
		hauteurElement++;
		Utils.setConstraints(0, 0, 0, hauteurElement, c);
		getContentPane().add(panelJeu, c);

		
		
		
		// Création du panel Tour (informations du tour précédent) et ajout à la frame
		JPanel panelTour = new JPanel(new BorderLayout());
		panelCartesJouees = new JPanel();
		panelCartesJouees.setBackground(Color.BLACK);
		JLabel invisible = new JLabel();
		//Création et ajout d'une image invisible permettant de combler l'éspace généré par le panel cartesjouées lorsqu'il est plein
		//int height = this.getHeight()/5+20; // height de la carte + 20 pour la place prise par le texte
		//BufferedImage bi = new BufferedImage(Math.round(height*(872f/1356f)), height, BufferedImage.TYPE_INT_ARGB);
		BufferedImage bi = new BufferedImage(this.getWidth() / 9, this.getWidth() / 7, BufferedImage.TYPE_INT_ARGB);
		invisible.setIcon(new ImageIcon(bi));
		panelCartesJouees.add(invisible);
		panelTour.add(panelCartesJouees, BorderLayout.CENTER);
		//Création du label Infos Tour et ajout au panel
		labelInfosTour = new JLabel();
		labelInfosTour.setOpaque(true);
		labelInfosTour.setBackground(Color.BLACK);
		labelInfosTour.setHorizontalAlignment(JLabel.CENTER);
		labelInfosTour.setFont(new Font("Verdana", Font.PLAIN, 13));
		labelInfosTour.setForeground(Color.WHITE);
		panelTour.add(labelInfosTour, BorderLayout.NORTH);
		//Ajout du panel à la frame
		c.fill = GridBagConstraints.BOTH;
		hauteurElement++;
		Utils.setConstraints(0, 0, 0, hauteurElement, c);
		getContentPane().add(panelTour, c);

		
		
		
		
		// Création du panel main (cartes dans la main du joueur) et ajout à la frame
		panelMain = new JPanel(new FlowLayout(FlowLayout.CENTER,0,0));
		panelMain.setBackground(Color.BLACK);
		this.paintMain();
		// Configuration du scrollPane permettant de scroller pour parcourir les cartes
		// lorsqu'il y en a trop
		JScrollPane scrollPaneCartes = new JScrollPane(panelMain);
		scrollPaneCartes.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		scrollPaneCartes.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);
		scrollPaneCartes.setBorder(BorderFactory.createEmptyBorder());
		scrollPaneCartes.getHorizontalScrollBar().setPreferredSize(new Dimension(0, 10));
		scrollPaneCartes.getHorizontalScrollBar().setUnitIncrement(20);
		scrollPaneCartes.getHorizontalScrollBar().setUI(new BasicScrollBarUI() {
			@Override
			protected void configureScrollBarColors() {
				this.thumbColor = Color.GRAY;
				this.trackColor = Color.BLACK;
			}

			protected JButton createEmptyButton() {
				JButton zero = new JButton("zero button");
				Dimension dim = new Dimension(0, 0);
				zero.setPreferredSize(dim);
				zero.setMinimumSize(dim);
				zero.setMaximumSize(dim);
				return zero;
			}

			@Override
			protected JButton createDecreaseButton(int orientation) {
				return createEmptyButton();
			}

			@Override
			protected JButton createIncreaseButton(int orientation) {
				return createEmptyButton();
			}
		});
		//Ajout du panel Cartes à la frame
		c.fill = GridBagConstraints.BOTH;
		hauteurElement++;
		Utils.setConstraints(1, 1, 0, hauteurElement, c);
		getContentPane().add(scrollPaneCartes, c);

		
		
		
		
		
		// Création du panel d'actions et ajout à la frame
		panelAction = new JPanel();
		panelAction.setBackground(Color.BLACK);
		//Initialisation des éléments du panel
		boutonJouer = new JButton("Jouer le tour");
		saisieMana = new JTextField("1", 10);
		labelManaAdversaire = new JLabel();
		labelManaAdversaire.setForeground(Color.LIGHT_GRAY);
		this.updateManaAdversaire();
		JButton historique = new JButton("Historique de la partie");
		JLabel imageMise = new JLabel();
		imageMise.setIcon(new ImageIcon("src/main/resources/fr_votremise_"
				+ Character.toLowerCase(joueur.getCouleur().toString().charAt(0)) + ".gif"));
		//Ajout des listeners
		ControleurJeu cj = new ControleurJeu(this, partie);
		boutonJouer.addActionListener(cj);
		historique.addActionListener(cj);
		saisieMana.addKeyListener(new ControleurMana(saisieMana, boutonJouer, this));
		//Ajout des éléments au panel
		panelAction.add(boutonJouer);
		panelAction.add(historique);
		panelAction.add(imageMise);
		panelAction.add(saisieMana);
		panelAction.add(labelManaAdversaire);
		//Ajout du panel à la frame
		hauteurElement++;
		Utils.setConstraints(0, 0, 0, hauteurElement, c);
		getContentPane().add(panelAction, c);

		
		
		
		// Affichage du mana
		barreMana = new JProgressBar();
		barreMana.setStringPainted(true);
		barreMana.setForeground(Color.MAGENTA);
		barreMana.setBackground(Color.BLACK);
		barreMana
				.setString("Mana : " + String.valueOf(Joueur.MANA_MAXIMUM) + "/" + String.valueOf(Joueur.MANA_MAXIMUM));
		barreMana.setValue(100);
		hauteurElement++;
		Utils.setConstraints(1, 0, 0, hauteurElement, c);
		getContentPane().add(barreMana, c);
	}

	
	
	
	//update le panel CartesJouees
	private void updateCartesJouees() {
		panelCartesJouees.removeAll();
		panelCartesJouees.repaint();
		Tour tourCourant = partie.getMancheCourante().getTourCourant();
		if (tourCourant.getMiseJoueurRouge() == 0 && tourCourant.getMiseJoueurVert() == 0)
			tourCourant = partie.getTourPrecedent();
		List<Carte> cartesJoueesDuTour = tourCourant.getCartesJouees();
		
		if(cartesJoueesDuTour.isEmpty()) {
			JLabel invisible = new JLabel();
			BufferedImage bi = new BufferedImage(this.getWidth() / 9, this.getWidth() / 7, BufferedImage.TYPE_INT_ARGB);
			invisible.setIcon(new ImageIcon(bi));
			panelCartesJouees.add(invisible);
		}else {
			for (int i = 0; i < cartesJoueesDuTour.size(); i++) {
				Carte c = cartesJoueesDuTour.get(i);
				JLabel tmp = new JLabel();
				ImageIcon image = new ImageIcon(c.getPath());
				tmp.setIcon(Utils.redimensionnerImage(image, this.getHeight() / 7));
				tmp.setHorizontalAlignment(JLabel.CENTER);
				panelCartesJouees.add(tmp);
			}
		}
		this.updateLabelInfosTour(tourCourant);
	}

	//update le label InfosTour
	private void updateLabelInfosTour(Tour tourCourant) {
		String text = "<html>";
		if (tourCourant.isFinDeManche()) {
			text += "Fin de manche, les sorciers sont remis à 3 cases du mur, les autres cartes jouées sont défaussées.";
		} else {
			if ((tourCourant.getManaRestantRouge() == 0 || tourCourant.getManaRestantVert() == 0)
					&& !partie.isJoueurPousse() && partie.isCartesJouees()) {
				if (tourCourant.getManaRestantRouge() == 0 && tourCourant.getManaRestantVert() == 0) {
					text += "Les deux sorciers n'ont plus de mana, le mur de feu se replace donc au milieu des deux joueurs";
				} else {
					if (tourCourant.getManaRestantRouge() == 0) {
						text += "Le sorcier rouge n'a plus de mana, le mur de feu s'est donc dirigé vers lui et la nouvelle manche est lancée!";
					} else {
						text += "Le sorcier vert n'a plus de mana, le mur de feu s'est donc dirigé vers lui et la nouvelle manche est lancée!";
					}
				}
			} else {
				text += "Mises:<font color=red>" + tourCourant.getMiseJoueurRouge() + "</font> - <font color=green>"
						+ tourCourant.getMiseJoueurVert() + "</font>";
				text += " / Attaques:<font color=red>" + tourCourant.getAttaqueJoueurRouge()
						+ "</font> - <font color=green>" + tourCourant.getAttaqueJoueurVert() + "</font>";
				text += " / Déplacement du mur: ";
				if (tourCourant.getDeplacementMur() > 0) {
					text += "<font color=red>" + tourCourant.getDeplacementMur() + "</font>";
				} else {
					if (tourCourant.getDeplacementMur() < 0) {
						text += "<font color=green>" + tourCourant.getDeplacementMur() + "</font>";
					} else {
						text += tourCourant.getDeplacementMur();
					}
				}
				if (partie.isJoueurPousse()) {
					text += " et un joueur est poussé par le mur, la nouvelle manche est lancée!";
				}
			}
		}
		if (partie.getPont().unSorcierEstTombe()) {
			text = partie.getGagnant();
			this.timer.stopChrono();
		} else {
			text += "</html>";
		}
		labelInfosTour.setText(text);
	}

	private void updateInfos() {
		labelInfos.setText(getInfos());
	}

	//update le label ManaAdversaire
	private void updateManaAdversaire() {
		labelManaAdversaire.setText(String.format("Mana de l'adversaire : %d", getManaAdversaire()));
	}

	/**
	 * Pose les images du pont sur l'interface graphique
	 */
	private void initPont() {
		panelPont.setLayout(new FlowLayout(FlowLayout.CENTER,0,0));
		
		for (int i = 0; i < Pont.TAILLE_PONT; i++) {
			JLabel tmp = new JLabel();
			String source = this.getImageCasePont(i);
			ImageIcon image = new ImageIcon(source);
			tmp.setIcon(Utils.redimensionnerImage(image, this.getWidth() / 25, this.getHeight() / 15));
			panelPont.add(tmp);
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
			ImageIcon image = new ImageIcon(this.getImageCasePont(i));
			tmp.setIcon(Utils.redimensionnerImage(image, this.getWidth() / 25, this.getHeight() / 15));
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
		Utils.setConstraints(1, 0.5, 0, 1, c);
		ImageIcon temp = new ImageIcon(partie.getJoueurRouge().getPath());
		BufferedImage bi = new BufferedImage(this.getWidth() / 25, this.getHeight() / 15, BufferedImage.TYPE_INT_ARGB);
		ImageIcon icon = new ImageIcon(bi);
		for (int i = 0; i < Pont.TAILLE_PONT; i++) {
			c.gridx = i;
			JLabel l = new JLabel();
			l.setIcon(icon);
			panelSorciers.add(l, c);
		}
		
		// Affichage Joueurs / Mur
		c.gridx = partie.getPosJoueur(ECouleurJoueur.ROUGE);
		ImageIcon imgRouge = new ImageIcon(partie.getJoueurRouge().getPath());
		imgRouge = Utils.redimensionnerImage(imgRouge, this.getHeight()/16);
		panelSorciers.add(new JLabel(imgRouge), c);
		c.gridx = partie.getPont().getPosMurDeFeu();
		ImageIcon imgMur = new ImageIcon(partie.getPont().getPathMur());
		imgMur = Utils.redimensionnerImage(imgMur, this.getHeight()/16);
		panelSorciers.add(new JLabel(imgMur), c);
		c.gridx = partie.getPosJoueur(ECouleurJoueur.VERT);
		ImageIcon imgVert = new ImageIcon(partie.getJoueurVert().getPath());
		imgVert = Utils.redimensionnerImage(imgVert, this.getHeight()/16);
		panelSorciers.add(new JLabel(imgVert), c);
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
		ControleurCartes ml = new ControleurCartes(this.panelMain, this.cartesJouees, this);
		
		for (int i = 0; i < mainJoueur.size(); i++) {
			Carte c = mainJoueur.get(i);
			JLabel tmp = new JLabel();
			ImageIcon image = new ImageIcon(c.getPath());
			tmp.setIcon(Utils.redimensionnerImage(image, this.getHeight() / 4));
			tmp.setHorizontalAlignment(JLabel.CENTER);
			tmp.setToolTipText(c.getDescription()); //ajoute la description lorsque la souris passe au dessus de la carte
			//tmp.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
			panelMain.add(tmp);
			tmp.addMouseListener(ml);
		}
	}

	public void displayCartesJouees() {
		for (int i = 0; i < joueur.getMainDuJoueur().size(); i++) {
			if (this.cartesJouees.contains(i)) {
				((JLabel) panelMain.getComponent(i)).setBorder(BorderFactory.createLineBorder(Color.RED, 1));
			} else {
				((JLabel) panelMain.getComponent(i)).setBorder(BorderFactory.createEmptyBorder());
			}
		}
	}

	public JButton getBoutonJouer() {
		return this.boutonJouer;
	}
	
	public Chrono getChrono() {
		return this.timer;
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
		Joueur jPartie;
		if(joueur.getCouleur().equals(ECouleurJoueur.ROUGE)) jPartie = partie.getJoueurRouge();
		else jPartie = partie.getJoueurVert();
		barreMana.setValue(jPartie.getManaActuel() * 2);
		barreMana.setString("Mana : " + jPartie.getManaActuel() + "/" + Joueur.MANA_MAXIMUM);
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
		str += "<b>Manche " + this.partie.getNombreManches() + " - ";
		str += "Tour " + this.partie.getMancheCourante().getNumeroTourCourant() + "<br></html>";
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
			tmp.setIcon(Utils.redimensionnerImage(image, this.getWidth() / 6, this.getWidth() / 4));
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
				tour.activerClone(carteAVoler);
				fenetreClone.dispose();
			}

		});

		jd.add(label, BorderLayout.NORTH);
		jd.add(panelCartes, BorderLayout.CENTER);
		jd.add(valider, BorderLayout.SOUTH);
		jd.setVisible(true);
		jd.setResizable(false);
		jd.setLocationRelativeTo(this);
		jd.setAlwaysOnTop(true);
	}

	@Override
	public void lancerRecyclage(Partie p, Tour tour, Joueur joueur) {
		JFrame fenetreClone = new JFrame();
		JDialog jd = new JDialog(fenetreClone,
				"Effet Recyclage pour Joueur " + joueur.getCouleur().toString().toLowerCase(), true);

		choix = 0;

		JLabel mise = new JLabel();
		mise.setText(
				"Entrer la mise à recycler entre +5 et -5 dans la limite de votre mise et de votre mana (mise actuelle "
						+ tour.getMiseJoueur(joueur) + "):");

		JTextField saisieManaRecyclage = new JTextField("0", 5);

		JButton valider = new JButton("Valider");

		saisieManaRecyclage.addKeyListener(new KeyAdapter() {

			@Override
			public void keyPressed(KeyEvent e) {
				try {
					// On vérifie que la saisie est comprise entre -5 et 5 et que ça ne génère pas
					// d'erreur sur la réserve de mana
					boolean saisieCorrecte = (Character.isDigit(e.getKeyChar())
							|| (e.getKeyChar() == '-' && saisieManaRecyclage.getCaretPosition() == 0)
							|| (e.getKeyChar() == '+' && saisieManaRecyclage.getCaretPosition() == 0)
									&& ((Integer.parseInt(saisieManaRecyclage.getText() + e.getKeyChar()) >= -5)
											&& (Integer.parseInt(saisieManaRecyclage.getText() + e.getKeyChar()) <= 5)
											&& (Integer.parseInt(saisieManaRecyclage.getText() + e.getKeyChar()) + (tour.getMiseJoueur(joueur)) <= joueur.getManaActuel())
											&& (joueur.getManaActuel() - Integer.parseInt(saisieManaRecyclage.getText() + e.getKeyChar()) - tour.getMiseJoueur(joueur) >= 0)
											&& (joueur.getManaActuel() - Integer.parseInt(saisieManaRecyclage.getText() + e.getKeyChar()) - tour.getMiseJoueur(joueur) <= Joueur.MANA_MAXIMUM)
											))
							|| (e.getKeyCode() == KeyEvent.VK_BACK_SPACE || e.getKeyCode() == KeyEvent.VK_DELETE
									|| e.getKeyCode() == KeyEvent.VK_LEFT || e.getKeyCode() == KeyEvent.VK_RIGHT
									|| e.getKeyCode() == KeyEvent.VK_SHIFT || e.getKeyCode() == KeyEvent.VK_CONTROL);
					if (saisieCorrecte) {
						saisieManaRecyclage.setEditable(true);
					} else {
						saisieManaRecyclage.setEditable(false);
						// Fondu vers le rouge pour prévenir l'utilisateur d'une saisie incorrecte
						Utils.fonduArrierePlan(saisieManaRecyclage, new Color(255, 43, 28), 8, 15);
					}

					int saisie = Integer.parseInt(saisieManaRecyclage.getText() + e.getKeyChar());

					if (saisie < -5 || saisie > 5) {
						valider.setEnabled(false);
					} else
						valider.setEnabled(true);
				} catch (NumberFormatException ex) {
					valider.setEnabled(false);
				}
			}
		});

		valider.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				choix = Integer.parseInt(saisieManaRecyclage.getText());
				tour.recyclerMise(joueur, choix);
				jd.dispose();
			}

		});

		jd.setLayout(new FlowLayout());
		jd.add(mise);
		jd.add(saisieManaRecyclage);
		jd.add(valider);
		jd.setModalityType(ModalityType.APPLICATION_MODAL);
		jd.setSize(700, 80);
		jd.setVisible(true);
		jd.setResizable(false);
		jd.setLocationRelativeTo(this);
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
				"Choisissez les cartes à jouer sous votre contrôle, le reste sera annulé puis défaussé:");
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
		jd.setLocationRelativeTo(this);
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
		this.updateCartesJouees();
		this.cartesJouees.clear();
		if (partie.getPont().unSorcierEstTombe()) {
			boutonJouer.setEnabled(false);
		} else {
			boutonJouer.setEnabled(true);
		}
	}

}