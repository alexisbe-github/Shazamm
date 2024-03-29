package main.java.vue.profil;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JSplitPane;

/**
 * La fenêtre définissant le profil du joueur.
 * 
 * @see JFrame
 */
public class VueProfil extends JPanel {

    private JSplitPane panel = new JSplitPane();
    private VueCreationProfil creation;
    private VueSelectionProfil selection;

    /**
     * Construit le panel.
     * 
     * @param creation  Le panneau de création de profil 
     * @param selection Le panneau de sélection de profil
     */
    public VueProfil(VueCreationProfil creation, VueSelectionProfil selection) {
        super();
        this.creation = creation;
        this.selection = selection;
        panel.setOrientation(JSplitPane.HORIZONTAL_SPLIT);
        panel.setLeftComponent(creation);
        panel.setRightComponent(selection);
        init();
    }
    
    private void init() {
        panel.setOrientation(JSplitPane.HORIZONTAL_SPLIT);
        panel.setLeftComponent(creation);
        panel.setRightComponent(selection);
        panel.setResizeWeight(0.5f); // Le panneau de gauche doit occuper 50 % du total
        panel.setDividerSize(3); // La barre séparatrice fait 3px de large
        this.add(panel);
    }
    
    public VueCreationProfil getPanelCreation() {
        return this.creation;
    }
    
    public VueSelectionProfil getPanelSelection() {
        return this.selection;
    }
}