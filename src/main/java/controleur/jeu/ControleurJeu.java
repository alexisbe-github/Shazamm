package main.java.controleur.jeu;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

import main.java.model.jeu.Joueur;
import main.java.vue.jeu.VueJeu;

public class ControleurJeu implements ActionListener {

	private VueJeu vj;

	public ControleurJeu(VueJeu vj) {
		this.vj = vj;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		JButton bouton = (JButton) e.getSource();
		switch (bouton.getText()) {
		case "OK":
			Joueur j = this.vj.getJoueur();
			int mise = vj.getMise();
			j.depenserMana(mise);
			this.vj.updateBarreMana(j.getManaActuel());
			break;
		default:
			return;
		}
	}

}
