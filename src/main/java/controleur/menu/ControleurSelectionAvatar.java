package main.java.controleur.menu;

import java.awt.Color;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;

import main.java.vue.profil.VueSelectionAvatars;

public class ControleurSelectionAvatar implements MouseListener {

	private VueSelectionAvatars vs;
	private JLabel labelActif;
	private String cheminImage;
	private boolean imageSelectionnee;

	public ControleurSelectionAvatar(VueSelectionAvatars vs, JLabel label, ImageIcon icone) {
		this.vs = vs;
		this.labelActif = label;
		this.cheminImage = icone.getDescription();
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		if (e.getSource() instanceof JLabel) {
			labelActif = (JLabel) e.getSource();
			for (JLabel l : this.vs.getLabels()) {
				if (!l.equals(labelActif))
					l.setBorder(BorderFactory.createEmptyBorder());
			}
			labelActif.setBorder(BorderFactory.createLineBorder(Color.GREEN, 1));
			vs.getBouton().setEnabled(true);
			vs.setControleurActif(this);
			imageSelectionnee = true;
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
		if (e.getSource() instanceof JLabel) {
			labelActif.setBorder(BorderFactory.createLineBorder(Color.RED, 1));
		}
	}

	@Override
	public void mouseExited(MouseEvent e) {
		if (e.getSource() instanceof JLabel) {
			if (!imageSelectionnee)
				labelActif.setBorder(BorderFactory.createEmptyBorder());
		}
	}
	
	public String getCheminImage () {
		return this.cheminImage;
	}

}
