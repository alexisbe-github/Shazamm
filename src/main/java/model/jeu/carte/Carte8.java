package main.java.model.jeu.carte;

import main.java.model.jeu.Joueur;
import main.java.model.jeu.partie.Partie;
import main.java.model.jeu.partie.Tour;

public class Carte8 extends Carte {

	private final String NOM_CARTE = "Double dose";
	private final String TEXTE_CARTE = "La puissance de mon attaque est multipliée par deux.";
	private final int NUMERO_CARTE = 8;

	public Carte8(Partie p, Joueur j) {
		super.partie = p;
		super.joueur = j;
		super.numeroCarte = NUMERO_CARTE;
		super.nom = NOM_CARTE;
		super.description = TEXTE_CARTE;
	}

	@Override
	public void lancerEffet(Tour tour) {
		tour.addAttaqueJoueur(joueur.getCouleur(), tour.getAttaqueJoueur(joueur.getCouleur()) * 2);
	}

}
