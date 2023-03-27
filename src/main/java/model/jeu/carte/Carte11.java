package main.java.model.jeu.carte;

import main.java.model.jeu.ECouleurJoueur;
import main.java.model.jeu.Joueur;
import main.java.model.jeu.partie.Partie;
import main.java.model.jeu.partie.Tour;

public class Carte11 extends Carte {

	private final String NOM_CARTE = "Résistance";
	private final String TEXTE_CARTE = "Si le mur de feu devait avancer vers moi, il ne bouge pas.";
	private final int NUMERO_CARTE = 11;

	public Carte11(Partie p, Joueur j) {
		super.partie = p;
		super.joueur = j;
		super.numeroCarte = NUMERO_CARTE;
		super.nom = NOM_CARTE;
		super.description = TEXTE_CARTE;
	}

	@Override
	public void lancerEffet(Tour tour) {
		// Si le mur se déplace dans le sens du joueur qui a lancé l'effet (=détenteur
		// de la carte)
		if (tour.getDeplacementMur() > 0 && joueur.getCouleur().equals(ECouleurJoueur.VERT)
				|| tour.getDeplacementMur() < 0 && joueur.getCouleur().equals(ECouleurJoueur.ROUGE)) {
			tour.setDeplacementMur(0); // on met le déplacement à 0
		}
	}

}
