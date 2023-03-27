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
		boolean joueurRougeEtRougePerdLeTour = tour.getDeplacementMur() > 0 && joueur.getCouleur().equals(ECouleurJoueur.VERT);
		boolean joueurVertEtVertPerdLeTour = tour.getDeplacementMur() < 0 && joueur.getCouleur().equals(ECouleurJoueur.ROUGE);
		if (joueurRougeEtRougePerdLeTour || joueurVertEtVertPerdLeTour) {
			tour.setDeplacementMur(0); // on met le déplacement à 0
		}
	}

}
