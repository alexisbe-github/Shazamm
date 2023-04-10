package main.java.vue.profil;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

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
		
		c.fill = GridBagConstraints.HORIZONTAL; // Remplissage horizontal
		
		c.weightx = 0.2; // Poids en X
		c.gridx = 0; // Position en X (index 0)
		c.gridy = 0; // Position en Y
		this.add(labelSelection);
		
		c.weightx = 0.8;
		c.gridx = 1;
		c.gridy = 0;
		this.add(listeChoix);
		
		setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); // Marge de 10px de chaque côté du panneau
	}
}