package main.java.model.jeu.carte;

import main.java.model.jeu.Joueur;
import main.java.model.jeu.partie.Partie;

public class Carte5 extends Carte {

	private final String NOM_CARTE = "Milieu";
	private final String TEXTE_CARTE = "Je replace immédiatement le mur de feu à égale distance des deux "
			+ "sorciers. Le tour continue ensuite normalement.";
	private final int NUMERO_CARTE = 5;

	public Carte5(Partie p, Joueur j) {
		super.partie = p;
		super.joueur = j;
		super.numeroCarte = NUMERO_CARTE;
		super.nom = NOM_CARTE;
		super.description = TEXTE_CARTE;
	}

	@Override
	public void lancerEffet(Joueur caster) {
		partie.deplacerMur((partie.getPont().getPosJoueurRouge() + partie.getPont().getPosJoueurVert()) / 2);
	}

}
