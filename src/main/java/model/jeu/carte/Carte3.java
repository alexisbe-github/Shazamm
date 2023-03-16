package main.java.model.jeu.carte;

import main.java.model.jeu.Joueur;
import main.java.model.jeu.Partie;

public class Carte3 extends Carte {

	private final String NOM_CARTE = "Larcin";
	private final String TEXTE_CARTE = "Tous les sorts joués à ce tour sont sous mon contrôle. Chacun est, à "
			+ "mon choix, appliqué comme si je l’avais joué, ou annulé et défaussé.";
	private final int NUMERO_CARTE = 3;

	public Carte3(Partie p, Joueur j) {
		super.partie = p;
		super.joueur = j;
		super.numeroCarte = NUMERO_CARTE;
		super.nom = NOM_CARTE;
		super.description = TEXTE_CARTE;
	}

	@Override
	public void jouer() {
		// TODO
	}

}
