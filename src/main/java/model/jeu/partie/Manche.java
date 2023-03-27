package main.java.model.jeu.partie;

import java.util.ArrayList;
import java.util.List;

public class Manche {

	private List<Tour> listeTours;
	private boolean mutismeCourant;

	public Manche() {
		mutismeCourant = false;
		listeTours = new ArrayList<>();
		listeTours.add(new Tour(mutismeCourant));
	}

	public Tour getTourCourant() {
		return listeTours.get(listeTours.size() - 1);
	}

	public void passerAuTourSuivant() {
		this.mutismeCourant = this.getTourCourant().getMutisme();
		this.listeTours.add(new Tour(mutismeCourant));
	}

	public boolean getMutismeCourant() {
		return mutismeCourant;
	}

}
