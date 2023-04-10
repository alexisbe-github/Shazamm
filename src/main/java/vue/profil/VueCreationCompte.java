package main.java.vue.profil;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 * Le panneau servant à la création d'un nouveau profil de joueur et à son
 * insertion dans la base de données.
 * 
 * @see JPanel
 */
public class VueCreationCompte extends JPanel {

	/**
	 * Le label précisant ce qu'il faut entrer dans le champ texte
	 */
	private final JLabel labelNom = new JLabel("Entrez votre nom : "),
			labelPrenom = new JLabel("Entrez votre prénom : ");
	/**
	 * Le <code>JTextField</code> qui va recevoir les informations du joueur <br>
	 * <br>
	 * Taille par défaut : <code>30 colonnes</code>
	 */
	private final JTextField textFieldNom = new JTextField(30), textFieldPrenom = new JTextField(30);
	/**
	 * Le bouton permettant d'ouvrir la fenêtre qui laissera le joueur choisir son avatar
	 */
	private final JButton boutonSelectionAvatar = new JButton("Sélectionner un avatar");
	/**
	 * L'aperçu de l'avatar sélectionné par le joueur
	 */
	private JLabel avatarSelectionne = new JLabel();
	/**
	 * Le bouton <code>OK</code> qui valide l'enregistrement du profil
	 */
	private final JButton boutonOK = new JButton("OK");

	/**
	 * Construit un <code>JPanel</code> en respectant les contraintes de positionnement des composants.
	 */
	public VueCreationCompte() {
		super(new GridBagLayout());
		init();
	}

	/**
	 * Initialise le panel.
	 */
	private void init() {
		GridBagConstraints c = new GridBagConstraints(); // Les contraintes de positionnement des composants
		
		c.fill = GridBagConstraints.HORIZONTAL; // Remplissage horizontal
		c.weightx = 0.25; // Poids en X
		c.gridx = 0; // Position en X (index 0)
		c.gridy = 0; // Position en Y
		this.add(labelNom, c); // Ajout du composant avec les contraintes
		
		c.weightx = 0.75;
		c.gridx = 1;
		c.gridy = 0;
		this.add(textFieldNom, c);
		
		c.weightx = 0.25;
		c.gridx = 0;
		c.gridy = 1;
		this.add(labelPrenom, c);
		
		c.weightx = 0.75;
		c.gridx = 1;
		c.gridy = 1;
		this.add(textFieldPrenom, c);
		
		c.weightx = 0.65;
		c.gridx = 0;
		c.gridy = 2;
		c.insets = new Insets(10, 30, 10, 30); // Padding du composant
		this.add(boutonSelectionAvatar, c);
		
		c.weightx = 0.35;
		c.gridx = 1;
		c.gridy = 2;
		c.insets = new Insets(10, 10, 10, 10);
		this.add(avatarSelectionne, c);
		
		c.weightx = 1;
		c.gridx = 0;
		c.gridy = 3;
		c.gridwidth = 2; // Largeur égale à 2 cellules
		c.insets = new Insets(20, 50, 0, 50);
		this.add(boutonOK, c);
		
		setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); // Padding de 10px de chaque côté du panneau
	}

}