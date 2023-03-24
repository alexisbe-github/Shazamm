package main.java.model.jeu.carte;

import main.java.model.jeu.Joueur;
import main.java.model.jeu.partie.Partie;

public class Carte2 extends Carte {

	private final String NOM_CARTE = "Clone";
	private final String TEXTE_CARTE = "Je pose devant moi une des cartes jouées par l’adversaire au tour"
			+ " précédent. Cette carte est appliquée à ce tour, comme si je l’avais jouée normalement.";
	private final int NUMERO_CARTE = 2;

	public Carte2(Partie p, Joueur j) {
		super.partie = p;
		super.joueur = j;
		super.numeroCarte = NUMERO_CARTE;
		super.nom = NOM_CARTE;
		super.description = TEXTE_CARTE;
	}

	@Override
	public void lancerEffet() {
		//partie.lancerClone(joueur, );
	}

}
