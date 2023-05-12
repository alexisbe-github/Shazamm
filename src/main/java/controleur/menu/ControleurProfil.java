package main.java.controleur.menu;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTextField;

import main.java.model.bdd.Profil;
import main.java.utils.Utils;
import main.java.vue.profil.VueCreationProfil;
import main.java.vue.profil.VueLancementPartie;
import main.java.vue.profil.VueProfil;
import main.java.vue.profil.VueSelectionAvatars;

public class ControleurProfil implements ActionListener, KeyListener, PropertyChangeListener {

	private VueCreationProfil vp;
	private boolean nomRempli;
	private boolean prenomRempli;
	private boolean avatarSelectionne;
	private boolean profilValide;

	public ControleurProfil(VueCreationProfil vp) {
		this.vp = vp;
	}

	@Override
	public void keyTyped(KeyEvent e) {
		if (e.getSource() instanceof JTextField) {
			verifierProfil();
		}
	}

	@Override
	public void keyPressed(KeyEvent e) {

	}

	@Override
	public void keyReleased(KeyEvent e) {

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() instanceof JButton) {
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
								avatar.setDescription(cheminAvatarSelectionné);
								avatar = Utils.redimensionnerImage(avatar, 40, 40);
								vp.setAvatar(avatar, cheminAvatarSelectionné);
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
				new Thread(new Runnable() {
					@Override
					public void run() {
						try {
							for (Component c : vp.getComponents()) {
								if (!c.equals(bouton))
									c.setEnabled(false);
							}
							Thread.sleep(50);
							bouton.setEnabled(false);
							insererBDD();
							VueLancementPartie f = (VueLancementPartie) vp.getRootPane().getParent();
							VueProfil vueProfilJ1 = f.getVueProfilJ1();
							VueProfil vueProfilJ2 = f.getVueProfilJ2();
							Thread.sleep(50);
							vueProfilJ1.getPanelSelection().majListeSelectionProfils();
							vueProfilJ2.getPanelSelection().majListeSelectionProfils();
						} catch (InterruptedException ex) {
							
						}
					}
				}).start();
				break;
			default:
				return;
			}
		}

	}

	@Override
	public void propertyChange(PropertyChangeEvent e) {
		if (e.getSource() instanceof JLabel && e.getNewValue() instanceof ImageIcon) {
			avatarSelectionne = false;
			ImageIcon img = (ImageIcon) e.getNewValue();
			if (img.getIconHeight() > 0 && img.getIconWidth() > 0) {
				avatarSelectionne = true;
			}
			verifierProfil();
		}
	}

	private void verifierProfil() {
		if (!this.vp.getTextFieldNom().getText().equals("")) {
			nomRempli = true;
		} else
			nomRempli = false;
		if (!this.vp.getTextFieldPrenom().getText().equals("")) {
			prenomRempli = true;
		} else
			prenomRempli = false;
		if (nomRempli && prenomRempli && avatarSelectionne) {
			profilValide = true;
		} else
			profilValide = false;
		if (profilValide) {
			this.vp.getBouton().setEnabled(true);
		} else
			this.vp.getBouton().setEnabled(false);
	}

	/**
	 * Insère le profil nouvellement créé dans la base de données
	 */
	private synchronized void insererBDD() {
		if (profilValide) {
			String nom = vp.getTextFieldNom().getText();
			String prenom = vp.getTextFieldPrenom().getText();
			String chemin = vp.getCheminAvatar();
			ImageIcon avatar = new ImageIcon(chemin);
			new Profil(nom, prenom, avatar);
		}
	}

}
