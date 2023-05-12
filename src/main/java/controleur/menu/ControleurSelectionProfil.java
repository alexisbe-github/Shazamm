package main.java.controleur.menu;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

import main.java.vue.profil.VueSelectionProfil;

public class ControleurSelectionProfil implements ActionListener {

	private VueSelectionProfil vp;

	public ControleurSelectionProfil(VueSelectionProfil vp) {
		this.vp = vp;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() instanceof JButton) {
			JButton bouton = (JButton) e.getSource();
			switch (bouton.getText()) {
			case "OK":
				// TODO Lier le profil au joueur de la partie
			break;
			default: break;
			}
		}
	}

}