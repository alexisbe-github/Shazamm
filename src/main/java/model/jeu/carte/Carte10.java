package main.java.model.jeu.carte;

import main.java.model.jeu.Joueur;
import main.java.model.jeu.partie.Partie;
import main.java.model.jeu.partie.Tour;

public class Carte10 extends Carte {

	private final String NOM_CARTE = "Brasier";
	private final String TEXTE_CARTE = "Le mur de feu se déplace de deux cases au lieu d’une. Seulement"
			+ " s’il devait se déplacer, bien sûr.";
	private final int NUMERO_CARTE = 10;

	public Carte10(Partie p, Joueur j) {
		super.partie = p;
		super.joueur = j;
		super.numeroCarte = NUMERO_CARTE;
		super.nom = NOM_CARTE;
		super.description = TEXTE_CARTE;
	}

	@Override
	public void lancerEffet(Tour tour) {
		tour.doubleDeplacementMur();
	}

}
