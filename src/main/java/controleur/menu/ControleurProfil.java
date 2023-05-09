package main.java.controleur.menu;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JButton;
import javax.swing.JOptionPane;

import main.java.vue.profil.VueCreationProfil;
import main.java.vue.profil.VueSelectionAvatars;

public class ControleurProfil implements ActionListener, KeyListener {

	private VueCreationProfil vp;

	public ControleurProfil(VueCreationProfil vp) {
		this.vp = vp;
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		JButton bouton = (JButton) e.getSource();
		switch (bouton.getText()) {
		case "Sélectionner un avatar":
			new VueSelectionAvatars();
			break;
		case "OK":
			break;
		default:
			return;
		}
	}

}
