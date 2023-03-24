package main.java.model.jeu.partie;

import java.util.ArrayList;
import java.util.List;

import main.java.model.jeu.ECouleurJoueur;
import main.java.model.jeu.Joueur;
import main.java.model.jeu.carte.Carte;

public class Manche {

	private List<Tour> listeTours;
	private boolean mutismeCourant;
	private int deplacementMur;

	public Manche() {
		mutismeCourant = false;
		deplacementMur = 0;
		listeTours = new ArrayList<>();
		listeTours.add(new Tour(mutismeCourant));
	}

	public Tour getTourCourant() {
		return listeTours.get(listeTours.size() - 1);
	}

	public void passerAuTourSuivant() {
		this.listeTours.add(new Tour(mutismeCourant));
	}

	public boolean getMutismeCourant() {
		return mutismeCourant;
	}

	public void enableMutisme(boolean enable) {
		this.mutismeCourant = enable;
		this.getTourCourant().activerMutisme(enable);
	}
	
	public void lancerLarcin(Joueur joueur) {
		this.getTourCourant().activerLarcin(joueur);
	}
	
	public void lancerClone(Joueur caster, Carte carteClonee) {
		this.getTourCourant().clonerCarte(caster, carteClonee);
	}
	
	public void lancerFinDeManche() {
		this.getTourCourant().activerFinDeManche();
	}
	
	public void changerMise(Joueur caster, int montant) {
		this.getTourCourant().changerMise(caster, montant);
	}

	public void addAttaqueJoueur(ECouleurJoueur joueur, int atq) {
		this.getTourCourant().addAttaqueJoueur(joueur, atq);
	}

	public int getAttaqueJoueur(ECouleurJoueur joueur) {
		return this.getTourCourant().getAttaqueJoueur(joueur);
	}

}
