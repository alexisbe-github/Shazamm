package main.java.vue.profil;

import javax.swing.JFrame;
import javax.swing.JSplitPane;

/**
 * Le panneau définissant le profil du joueur.
 * 
 * @see JSplitPane
 */
public class VueProfil extends JSplitPane {

	private VueCreationProfil creation;
	private VueSelectionProfil selection;

	/**
	 * Construit le panel.
	 */
	public VueProfil(VueCreationProfil creation, VueSelectionProfil selection) {
		super();
		this.creation = creation;
		this.selection = selection;
		this.setOrientation(JSplitPane.HORIZONTAL_SPLIT);
		this.setLeftComponent(creation);
		this.setRightComponent(selection);

		setResizeWeight(0.5f); // Le panneau de gauche doit occuper 50 % du total
		setDividerSize(3); // La barre séparatrice fait 3px de large
	}
	
	public VueCreationProfil getPanelCreation() {
		return this.creation;
	}
	
	public VueSelectionProfil getPanelSelection() {
		return this.selection;
	}
	
	public static void main(String[] args) {
		JFrame fenetre = new JFrame();
		fenetre.setVisible(true);
		fenetre.getContentPane().add(new VueProfil(new VueCreationProfil(), new VueSelectionProfil()));
		fenetre.pack();
	}
}