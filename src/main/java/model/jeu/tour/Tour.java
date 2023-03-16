package main.java.model.jeu.tour;

import java.util.ArrayList;
import java.util.List;

import main.java.model.jeu.carte.Carte;

public class Tour {

	private int miseJoueurRouge, miseJoueurVert;
	private List<Carte> cartesJoueesVert, cartesJoueesRouge;
	
	public Tour() {
		this.cartesJoueesRouge = new ArrayList<>();
		this.cartesJoueesVert = new ArrayList<>();
	}
	
}
