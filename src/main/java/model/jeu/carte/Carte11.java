package main.java.model.jeu.carte;

import main.java.model.jeu.Joueur;
import main.java.model.jeu.Partie;
import main.java.model.jeu.carte.effets.IMurStrategy;

public class Carte11 extends Carte implements IMurStrategy{

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
	public void jouer() {

	}

	@Override
	public void deplacerMur() {
		// TODO Auto-generated method stub

	}

}
