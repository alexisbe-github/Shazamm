package main.java.model.jeu.carte;

import main.java.model.jeu.Joueur;
import main.java.model.jeu.partie.Partie;
import main.java.model.jeu.partie.Tour;

public class Carte9 extends Carte {

	private final String NOM_CARTE = "Qui perd gagne";
	private final String TEXTE_CARTE = "Le mur de feu avance en sens inverse : vers celui qui a gagné ce tour."
			+ " N’a pas d’effet si le mur de feu ne devait pas bouger.";
	private final int NUMERO_CARTE = 9;

	public Carte9(Partie p, Joueur j) {
		super.partie = p;
		super.joueur = j;
		super.numeroCarte = NUMERO_CARTE;
		super.nom = NOM_CARTE;
		super.description = TEXTE_CARTE;
	}

	@Override
	public void lancerEffet(Tour tour) {
		tour.inverserDeplacementMur();
	}

}
