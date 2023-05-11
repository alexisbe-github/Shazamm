package main.java.vue.profil;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.BorderFactory;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;

import main.java.controleur.menu.ControleurSelectionProfil;
import main.java.model.bdd.Profil;

/**
 * Le panneau servant à la sélection d'un profil de joueur enregistré dans la
 * base de données.
 * 
 * @see JPanel
 */
public class VueSelectionProfil extends JPanel {
	
	/**
	 * Le label affichant le titre du panel
	 */
	private final JLabel labelTitre = new JLabel("Sélectionner un profil : ");
	/**
	 * Le label précisant ce qu'il faut sélectionner dans la boîte déroulante
	 */
	private final JLabel labelSelection = new JLabel("Sélectionnez un profil de joueur : ");
	/**
	 * Le bouton <code>OK</code> qui valide la sélection du profil
	 */
	private final JButton boutonOK = new JButton("OK");
	/**
	 * La liste des items sélectionnables par le joueur
	 */
	private JComboBox<Object> listeChoix;
	/**
	 * Le contrôleur du panel
	 */
	private ControleurSelectionProfil controleur = new ControleurSelectionProfil(this);
	
	/**
	 * Construit un <code>JPanel</code> en respectant les contraintes de positionnement des composants.
	 */
	public VueSelectionProfil() {
		super(new GridBagLayout());
		init();
	}
	
	/**
	 * Initialise le panel.
	 */
	private void init() {
		Object[] choix = Profil.getListeProfils().toArray();
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
		c.insets = new Insets(5, 5, 5, 5);
		listeChoix.setPreferredSize(new Dimension(this.getWidth(), 12));
		this.add(listeChoix, c);
		
		
		c.weightx = 1;
		c.weighty = 0;
		c.gridx = 0;
		c.gridy = 3;
		c.insets = new Insets(10, 5, 10, 5);
		this.boutonOK.addActionListener(controleur);
		this.add(boutonOK, c);
		
		setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); // Marge de 10px de chaque côté du panneau
	}
	
	/**
	 * Met à jour la liste déroulante de sélection des profils
	 */
	public void majListeSelectionProfils() {
		DefaultComboBoxModel<Object> modele = (DefaultComboBoxModel<Object>) listeChoix.getModel();
        modele.removeAllElements();
        for (Profil profil : Profil.getListeProfils()) {
            modele.addElement(profil);
        }
		listeChoix.setModel(modele);
		listeChoix.repaint();
		listeChoix.revalidate();
	}
}