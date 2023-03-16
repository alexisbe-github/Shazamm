package main.java.model.jeu.carte;

import main.java.model.jeu.Joueur;
import main.java.model.jeu.Partie;
import main.java.model.jeu.carte.effets.IMurStrategy;

public class Carte5 extends Carte implements IMurStrategy {

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
	public void jouer() {
		deplacerMur();
	}

	@Override
	public void deplacerMur() {
		int posJoueurRouge = partie.getPont().getPosJoueurRouge();
		int posJoueurVert = partie.getPont().getPosJoueurVert();
		partie.deplacerMur((posJoueurVert + posJoueurRouge) / 2);
	}



	
}
