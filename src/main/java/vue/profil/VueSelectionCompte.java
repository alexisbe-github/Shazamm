package main.java.vue.profil;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.BorderFactory;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * Le panneau servant à la sélection d'un profil de joueur enregistré dans la
 * base de données.
 * 
 * @see JPanel
 */
public class VueSelectionCompte extends JPanel {
	
	/**
	 * Le label affichant le titre du panel
	 */
	private final JLabel labelTitre = new JLabel("Sélectionner un profil : ");
	/**
	 * Le label précisant ce qu'il faut sélectionner dans la boîte déroulante
	 */
	private final JLabel labelSelection = new JLabel("Sélectionnez un profil de joueur : ");

	/**
	 * La liste des items sélectionnables par le joueur
	 */
	private JComboBox<Object> listeChoix;
	
	/**
	 * Construit un <code>JPanel</code> en respectant les contraintes de positionnement des composants.
	 */
	public VueSelectionCompte() {
		super(new GridBagLayout());
		init();
	}
	
	/**
	 * Initialise le panel.
	 */
	private void init() {
		Object[] choix = {}; //TODO À modifier/importer depuis la BDD
		listeChoix = new JComboBox<>(choix);
		
		GridBagConstraints c = new GridBagConstraints(); // Les contraintes de positionnement des composants
		
		c.fill = GridBagConstraints.BOTH; // Remplissage horizontal
		
		labelTitre.setFont(new Font(getFont().getFontName(), Font.BOLD, 16));
		c.weightx = 1;
		c.weighty = 0;
		c.gridx = 0;
		c.gridy = 0;
		this.add(labelTitre, c); // Ajout du composant avec les contraintes
		
		c.weightx = 1; // Poids en X
		c.weighty = 1; // Poids en Y
		c.gridx = 0; // Position en X
		c.gridy = 1; // Position en Y
		this.add(labelSelection, c);
		
		c.weightx = 1;
		c.weighty = 0.2;
		c.gridx = 0;
		c.gridy = 2;
		c.insets = new Insets(5, 5, 30, 5);
		this.add(listeChoix, c);
		
		listeChoix.setPreferredSize(new Dimension(this.getWidth(), 12));
		
		setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); // Marge de 10px de chaque côté du panneau
	}
}