package main.java.vue.jeu;

import java.awt.Dimension;

import javax.swing.JFrame;

import main.java.model.jeu.partie.Partie;

public class VueHistorique extends JFrame{
	
	Partie partie;

	public VueHistorique(Partie p) {
		this.setVisible(true);
		this.setSize(new Dimension(500,500));
		this.partie = p;
	}
	
}
