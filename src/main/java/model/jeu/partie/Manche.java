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

	public int getNumeroTourCourant() {
		return listeTours.size();
	}

	/**
	 * 
	 * @return int deplacement du mur sur le tour courant
	 */
	public int passerAuTourSuivant() {
		Tour tourCourant = getTourCourant();
		this.mutismeCourant = tourCourant.getMutisme();
		this.listeTours.add(new Tour(mutismeCourant));
		System.out.println("R:" +tourCourant.getAttaqueJoueurRouge() + "       V:" + tourCourant.getAttaqueJoueurVert());//DEBUG PRINT ATTAQUES JOUEURS
		return tourCourant.getDeplacementMur();
	}

	public boolean getMutismeCourant() {
		return mutismeCourant;
	}

	public int getNombreTours() {
		return listeTours.size();
	}

	public List<Tour> getListeTours() {
		return listeTours;
	}

}
