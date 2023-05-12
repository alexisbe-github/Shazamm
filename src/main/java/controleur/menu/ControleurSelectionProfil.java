package main.java.controleur.menu;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;

import main.java.vue.profil.VueSelectionProfil;

public class ControleurSelectionProfil implements ActionListener {

	private VueSelectionProfil vp;
	private boolean profilChoisi = false;

	public ControleurSelectionProfil(VueSelectionProfil vp) {
		this.vp = vp;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() instanceof JButton) {
			JButton bouton = (JButton) e.getSource();
			switch (bouton.getText()) {
			case "OK":
				profilChoisi = true;
				JFrame f = (JFrame) vp.getRootPane().getParent();
				f.dispose();
				return;
			default:
				break;
			}
		}
	}
	
	/**
	 * @return <code>true</code> si le profil a été choisi, <code>false</code> sinon
	 */
	public boolean isProfilChoisi() {
		return profilChoisi;
	}

}