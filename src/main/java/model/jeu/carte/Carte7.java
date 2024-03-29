package main.java.model.jeu.carte;

import main.java.model.jeu.Joueur;
import main.java.model.jeu.partie.Partie;
import main.java.model.jeu.partie.Tour;

public class Carte7 extends Carte {

	private final String NOM_CARTE = "Boost attaque";
	private final String TEXTE_CARTE = "La puissance de mon attaque est augmentée de 7.";
	private final int NUMERO_CARTE = 7;

	public Carte7(Partie p, Joueur j) {
		super.partie = p;
		super.joueur = j;
		super.numeroCarte = NUMERO_CARTE;
		super.nom = NOM_CARTE;
		super.description = TEXTE_CARTE;
	}

	@Override
	public void lancerEffet(Tour tour) {
		tour.addAttaqueJoueur(joueur.getCouleur(), 7);
	}

}
