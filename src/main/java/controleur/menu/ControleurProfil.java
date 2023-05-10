package main.java.controleur.menu;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JOptionPane;

import main.java.utils.Utils;
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
			Thread t = new Thread(new Runnable() {
				@Override
				public void run() {
					VueSelectionAvatars vueSelection = new VueSelectionAvatars();
					while (true) {
						if (!vueSelection.isFenetreActive()) {
							String cheminAvatarSelectionné = vueSelection.getCheminImageCourante();
							ImageIcon avatar = new ImageIcon(cheminAvatarSelectionné);
							avatar = Utils.redimensionnerImage(avatar, 40, 40);
							vp.setAvatar(avatar);
							break;
						}
						try {
							Thread.sleep(500);
						} catch (InterruptedException e) {

						}
					}
				}
			});
			t.start();
			break;
		case "OK":
			break;
		default:
			return;
		}
	}

}
