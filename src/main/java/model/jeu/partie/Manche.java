package main.java.model.jeu.partie;

import java.util.ArrayList;
import java.util.List;

import main.java.model.jeu.Joueur;

public class Manche {

	private List<Tour> listeTours;
	private Joueur joueurRouge, joueurVert;
	private boolean mutismeCourant;
	private int deplacementMur;

	public Manche(Joueur jr, Joueur jv) {
		joueurRouge = jr;
		joueurVert = jv;
		mutismeCourant = false;
		deplacementMur = 0;
		listeTours = new ArrayList<>();
		listeTours.add(new Tour());
	}

	public Tour getTourCourant() {
		return listeTours.get(listeTours.size() - 1);
	}
	
	public void passerAuTourSuivant() {
		this.listeTours.add(new Tour());
	}
	
	public boolean getMutismeCourant() {
		return mutismeCourant;
	}
	
	public void enableMutisme(boolean enable) {
		this.mutismeCourant = enable;
	}

}
