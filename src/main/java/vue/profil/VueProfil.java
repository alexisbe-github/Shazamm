package main.java.vue.profil;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JSplitPane;

/**
 * Le panneau définissant le profil du joueur.
 * 
 * @see JPanel
 */
public class VueProfil extends JSplitPane {

	/**
	 * Construit le panel.
	 */
	public VueProfil() {
		super(JSplitPane.HORIZONTAL_SPLIT, new VueCreationCompte(), new VueSelectionCompte());
		setResizeWeight(0.5f); // Le panneau de gauche doit occuper 50 % du total
		setDividerSize(3); // La barre séparatrice fait 3px de large
	}

}
