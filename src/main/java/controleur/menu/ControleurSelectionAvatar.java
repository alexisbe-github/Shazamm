package main.java.controleur.menu;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;

import main.java.vue.profil.VueSelectionAvatars;

public class ControleurSelectionAvatar implements MouseListener {

	private VueSelectionAvatars vs;
	private JLabel labelActif;
	private String cheminImage;

	public ControleurSelectionAvatar(VueSelectionAvatars vs, JLabel label, ImageIcon icone) {
		this.vs = vs;
		this.labelActif = label;
		this.cheminImage = icone.getDescription();
		System.out.println(cheminImage);
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		if (e.getSource() instanceof JLabel) {
			labelActif = (JLabel) e.getSource();
			ImageIcon icone = (ImageIcon) labelActif.getIcon();
			System.out.println(icone.getDescription());
		} else {
			JButton bouton = (JButton) e.getSource();
		}
	}

	@Override
	public void mousePressed(MouseEvent e) {
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		
	}

}
