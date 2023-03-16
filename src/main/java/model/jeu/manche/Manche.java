package main.java.model.jeu.manche;

import java.util.ArrayList;
import java.util.List;

import main.java.model.jeu.Joueur;
import main.java.model.jeu.tour.Tour;

public class Manche {

	private List<Tour> listeTours;
	private Joueur joueurRouge, joueurVert;

	public Manche(Joueur jr, Joueur jv) {
		joueurRouge = jr;
		joueurVert = jv;
		listeTours = new ArrayList<>();
		listeTours.add(new Tour());
	}

	public Tour getTourCourant() {
		return listeTours.get(listeTours.size() - 1);
	}

}
