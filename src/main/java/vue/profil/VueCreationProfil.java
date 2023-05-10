package main.java.vue.profil;

import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import main.java.controleur.menu.ControleurProfil;

/**
 * Le panneau servant à la création d'un nouveau profil de joueur et à son
 * insertion dans la base de données.
 * 
 * @see JPanel
 */
public class VueCreationProfil extends JPanel {

	/**
	 * Le label affichant le titre du panel
	 */
	private final JLabel labelTitre = new JLabel("Créer un profil : ");
	/**
	 * Le label précisant ce qu'il faut entrer dans le champ texte
	 */
	private final JLabel labelPrenom = new JLabel("Entrez votre prénom : "),
			labelNom = new JLabel("Entrez votre nom : ");
	/**
	 * Le <code>JTextField</code> qui va recevoir les informations du joueur <br>
	 * <br>
	 * Taille par défaut : <code>30 colonnes</code>
	 */
	private final JTextField textFieldNom = new JTextField(30), textFieldPrenom = new JTextField(30);
	/**
	 * Le bouton permettant d'ouvrir la fenêtre qui laissera le joueur choisir son
	 * avatar
	 */
	private final JButton boutonSelectionAvatar = new JButton("Sélectionner un avatar");
	/**
	 * L'aperçu de l'avatar sélectionné par le joueur
	 */
	private JLabel avatarSelectionne = new JLabel();
	/**
	 * Le chemin vers le fichier de l'avatar sélectionné
	 */
	private String cheminAvatar;
	/**
	 * Le bouton <code>OK</code> qui valide l'enregistrement du profil
	 */
	private final JButton boutonOK = new JButton("OK");
	/**
	 * Le contrôleur de la classe qui écoute les évènements
	 */
	private ControleurProfil controleur = new ControleurProfil(this);

	/**
	 * Construit un <code>JPanel</code> en respectant les contraintes de
	 * positionnement des composants.
	 */
	public VueCreationProfil() {
		super(new GridBagLayout());
		init();
	}

	/**
	 * Initialise le panel.
	 */
	private void init() {
		GridBagConstraints c = new GridBagConstraints(); // Les contraintes de positionnement des composants

		c.fill = GridBagConstraints.HORIZONTAL; // Remplissage horizontal

		labelTitre.setFont(new Font(getFont().getFontName(), Font.BOLD, 16));
		c.weightx = 1;
		c.gridx = 0;
		c.gridy = 0;
		this.add(labelTitre, c); // Ajout du composant avec les contraintes

		c.weightx = 0.25;
		c.gridx = 0;
		c.gridy = 1;
		this.add(labelPrenom, c);

		c.weightx = 0.75;
		c.gridx = 1;
		c.gridy = 1;
		textFieldPrenom.addKeyListener(controleur);
		this.add(textFieldPrenom, c);
		
		c.weightx = 0.25; // Poids en X
		c.gridx = 0; // Position en X (index 0)
		c.gridy = 2; // Position en Y
		this.add(labelNom, c); // Ajout du composant avec les contraintes

		c.weightx = 0.75;
		c.gridx = 1;
		c.gridy = 2;
		textFieldNom.addKeyListener(controleur);
		this.add(textFieldNom, c);

		c.weightx = 0.65;
		c.gridx = 0;
		c.gridy = 3;
		c.insets = new Insets(10, 30, 10, 30); // Padding du composant
		boutonSelectionAvatar.addActionListener(controleur);
		this.add(boutonSelectionAvatar, c);

		c.weightx = 0.35;
		c.gridx = 1;
		c.gridy = 3;
		c.insets = new Insets(10, 10, 10, 10);
		avatarSelectionne.addPropertyChangeListener(controleur);
		this.add(avatarSelectionne, c);

		c.weightx = 1;
		c.gridx = 0;
		c.gridy = 4;
		c.gridwidth = 2; // Largeur égale à 2 cellules
		c.insets = new Insets(20, 50, 0, 50);
		boutonOK.setEnabled(false);
		boutonOK.addActionListener(controleur);
		this.add(boutonOK, c);

		setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); // Marge de 10px de chaque côté du panneau
	}

	/**
	 * Met à jour l'avatar sélectionné
	 * 
	 * @param avatar Le nouvel avatar
	 * @param chemin Le chemin vers l'avatar
	 */
	public void setAvatar(ImageIcon avatar, String chemin) {
		avatar.setDescription(chemin);
		this.cheminAvatar = chemin;
		this.avatarSelectionne.setIcon(avatar);
	}

	/**
	 * @return Le chemin vers l'avatar sélectionné
	 */
	public String getCheminAvatar() {
		return this.cheminAvatar;
	}

	/**
	 * @return Le <code>JTextField</code> correspondant au nom
	 */
	public JTextField getTextFieldNom() {
		return this.textFieldNom;
	}

	/**
	 * @return Le <code>JTextField</code> correspondant au prénom
	 */
	public JTextField getTextFieldPrenom() {
		return this.textFieldPrenom;
	}

	/**
	 * @return L'avatar sélectionné
	 */
	public JLabel getAvatarSelectionne() {
		return this.avatarSelectionne;
	}

	/**
	 * @return Le bouton de validation
	 */
	public JButton getBouton() {
		return this.boutonOK;
	}
}