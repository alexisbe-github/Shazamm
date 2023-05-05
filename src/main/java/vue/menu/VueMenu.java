package main.java.vue.menu;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import main.java.controleur.menu.ControleurMenu;

/**
 * Le panneau correspondant au menu du joueur.
 */
public class VueMenu extends JFrame {

	/**
	 * L'icône permettant d'accéder au profil du joueur
	 */
	private final JLabel iconeProfil = new JLabel();
	/**
	 * Le bouton qui permet de lancer une partie
	 */
	private final JButton boutonJouer = new JButton("Jouer");
	
	private final JButton boutonJouerIA = new JButton("Jouer contre l'ordinateur");
	/**
	 * Le bouton qui permet d'afficher le classement des meilleurs joueurs
	 */
	private final JButton boutonClassement = new JButton("Classement");
	/**
	 * Le bouton qui permet d'afficher l'historique des parties
	 */
	private final JButton boutonHistorique = new JButton("Historique");
	/**
	 * Le bouton qui permet de sélectionner les options du jeu
	 */
	private final JButton boutonOptions = new JButton("Options");
	
	private JPanel panel = new JPanel(new GridBagLayout());

	public VueMenu() {
		this.setTitle("Menu de Shazamm");
		init();
	}

	/**
	 * Initialise le panel.
	 */
	private void init() {
		GridBagConstraints c = new GridBagConstraints(); // Les contraintes de positionnement des composants
		
		c.fill = GridBagConstraints.HORIZONTAL; // Remplissage horizontal
		
		// Panneau qui remplit l'espace restant à gauche de l'icône
	    c.weightx = 1; // Poids en X
	    c.gridx = 0; // Position en X
	    c.gridy = 0; // Position en Y
	    c.anchor = GridBagConstraints.LINE_START; // Ancre à gauche
	    c.insets = new Insets(0, 0, 20, 20);
	    panel.add(new JPanel(), c);
		
		iconeProfil.setIcon(new ImageIcon("src/resources/images/icone-profil-joueur.png"));
		c.weightx = 0;
		c.gridx = 1;
		c.gridy = 0;
		panel.add(iconeProfil, c); // Ajout du composant avec les contraintes
		
		c.gridwidth = 2; // Fait s'étendre tous les composants suivants sur 2 cellules
		c.insets = new Insets(5, 10, 5, 10); // Marge autour des boutons en pixels
		
		c.weightx = 1;
		c.gridx = 0;
		c.gridy = 1;
		panel.add(boutonJouer, c);
		
		c.weightx = 1;
		c.gridx = 0;
		c.gridy = 2;
		panel.add(boutonJouerIA, c);
		
		c.weightx = 1;
		c.gridx = 0;
		c.gridy = 3;
		panel.add(boutonClassement, c);
		
		c.weightx = 1;
		c.gridx = 0;
		c.gridy = 4;
		panel.add(boutonHistorique, c);
		
		c.weightx = 1;
		c.gridx = 0;
		c.gridy = 5;
		panel.add(boutonOptions, c);
		
		ControleurMenu cm = new ControleurMenu(this);
		boutonJouer.addActionListener(cm);
		
		panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); // Marge de 10px de chaque côté du panneau
		
		this.add(panel);
		this.setPreferredSize(new Dimension(600, 600));
		this.pack();
		this.setResizable(false);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 
		this.setLocationRelativeTo(null);
		this.setVisible(true);
	}
}
